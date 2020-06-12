package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.Adapter_lista_prestamo;
import com.example.almonte.DataSource.LoansByClient;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetallePrestamoFragment extends Fragment {

    private TextView nombreCompleto, montoPrestamo, montoDeuda, plan, totalPago, interes, fechaPrestamo;
    private Button historicoPago, historicoPrestamo, verCliente, verPlan, crearPrestamo;
    private String id;
    private String idClient;
    JSONObject jsonpayment = new JSONObject();

    public DetallePrestamoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Informaciones Prestamo");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_detalle_prestamo, container, false);

        viewHolder(rootview);

        Bundle bundle =getArguments();
        String idLoan = bundle.getString("id_item");

        getInfoLoan(idLoan);

        //Action evemt cuado presiona los botones
        historicoPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Historico de Pago...", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "historialPagos");
                intent.putExtra("ID_iTEM", id); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });



        verCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Cliente...", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "detalleCliente");
                intent.putExtra("ID_iTEM", id); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });

        crearPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Prestamo...", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "crearPrestamo");
                intent.putExtra("ID_iTEM", idClient); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });


        return rootview;
    }

    public void getInfoLoan( String idLoan) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select c.firstname,c.lastname, c.id," +
                "l.amount," +"p.name, p.interest, l.amountPerQuota, l.interestPerQuota, l.date, l.id" +
                " from Clients c inner join Loans l on c.id  like l.client " +
                "inner join Plans p on  l.plane like p.id  where l.id like  '"+ idLoan+"'" , null);
        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {

                    try {
                        jsonpayment.put("firstname", fila.getString(0));
                        jsonpayment.put("lastname", fila.getString(1));
                        jsonpayment.put("idCliente", fila.getString(2));
                        jsonpayment.put("amount", fila.getString(3));
                        jsonpayment.put("plan", fila.getString(4));
                        jsonpayment.put("interest", fila.getString(5));
                        jsonpayment.put("amountPerQuota", fila.getString(6));
                        jsonpayment.put("interestPerQuota", fila.getString(7));
                        jsonpayment.put("date", fila.getString(8));
                        jsonpayment.put("id", fila.getString(9));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (fila.moveToNext());
            }
        }else{
            getInfoLoanOnline(idLoan);
        }

        try {
            nombreCompleto.setText(""+jsonpayment.getString("firstname")+" "+jsonpayment.getString("lastname"));
            montoPrestamo.setText(jsonpayment.getString("amount"));
            plan.setText(jsonpayment.getString("plan"));
            montoDeuda.setText(""+(jsonpayment.getDouble("amountPerQuota")+ jsonpayment.getDouble("interestPerQuota"))+" $RD");
            interes.setText(jsonpayment.getString("interest")+" %");
            fechaPrestamo.setText(jsonpayment.getString("date"));
            id = jsonpayment.getString("id");
            idClient = jsonpayment.getString("idCliente");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getInfoLoanOnline(String idLoan){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_LOAN_IDLOAN)+"/"+idLoan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonLoan = new JSONObject(response);

                            JSONObject client = jsonLoan.getJSONObject("client");
                            JSONObject plans = jsonLoan.getJSONObject("plan");

                            jsonpayment.put("firstname", client.getString("name"));
                            jsonpayment.put("lastname", client.getString("apellido"));
                            jsonpayment.put("idCliente", client.getString("_id"));
                            jsonpayment.put("amount", jsonLoan.getString("amount"));
                            jsonpayment.put("plan", plans.getString("name"));
                            jsonpayment.put("interest", plans.getString("interest"));
                            jsonpayment.put("amountPerQuota", jsonLoan.getString("amountPerQuota") );
                            jsonpayment.put("interestPerQuota", jsonLoan.getString("interestPerQuota"));
                            jsonpayment.put("date", jsonLoan.getString("date"));
                            jsonpayment.put("id", jsonLoan.getString("_id"));


                            nombreCompleto.setText(""+jsonpayment.getString("firstname")+" "+jsonpayment.getString("lastname"));
                            montoPrestamo.setText(jsonpayment.getString("amount"));
                            plan.setText(jsonpayment.getString("plan"));
                            montoDeuda.setText(""+(jsonpayment.getDouble("amountPerQuota")+ jsonpayment.getDouble("interestPerQuota"))+" $RD");
                           interes.setText(jsonpayment.getString("interest")+" %");
                            fechaPrestamo.setText(jsonpayment.getString("date"));
                            id = jsonpayment.getString("id");
                            idClient = jsonpayment.getString("idCliente");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }

    public void viewHolder(View view) {
        nombreCompleto = view.findViewById(R.id.nombre_completo);
        montoPrestamo = view.findViewById(R.id.monto_prestamo);
        montoDeuda = view.findViewById(R.id.monto_deuda);
        plan = view.findViewById(R.id.plan_prestamo);
        interes = view.findViewById(R.id.interes);
        fechaPrestamo = view.findViewById(R.id.fecha_prestamo);
        historicoPago = view.findViewById(R.id.historico_pago);
        verCliente = view.findViewById(R.id.ver_cliente);
        crearPrestamo = view.findViewById(R.id.crearPrestamo);
    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

}

