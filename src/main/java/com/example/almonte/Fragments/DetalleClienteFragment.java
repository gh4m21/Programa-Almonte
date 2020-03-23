package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleClienteFragment extends Fragment {

    private TextView nombre, apellido, cedula, telefono, telefono2, telefono3, ciudad, direccion, direccionReferencia, punto;
    private Button prestamoAcual, historicoPrestamo, crearPrestamo;
    private String id;
    JSONObject jsonClient = new JSONObject();

    public DetalleClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Informaciones Clientes");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_detalle_cliente, container, false);

        viewHolder(rootview);

        Bundle bundle =getArguments();
        String idLoan = bundle.getString("id_item");

        getClient(idLoan);

        //Action evemt cuado presiona los botones
        prestamoAcual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo prestamo Actual...", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "detallePrestamo");
                intent.putExtra("ID_iTEM", id); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });

        historicoPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Historico de Prestamo...", Toast.LENGTH_SHORT).show();
            }
        });

        crearPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Abriendo Prestamo...", Toast.LENGTH_SHORT).show();

                Context context = v.getContext();
                Intent intent = new Intent(context, pagoDetailActivity.class);
                intent.putExtra("botonClick", "crearPrestamo");
                intent.putExtra("ID_iTEM", id); //pasar los datos del activity de lista en otro activity de detail
                context.startActivity(intent);
            }
        });

        return rootview;
    }

    public void getClient(String idLoan){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select c.firstname, c.lastname, c.identification," +
                "c.phone, c.city , c.adress , c.description, l.id,c.phone2,c.phone3" +
                " from Clients c inner join Loans l on c.id like l.client where l.id like '"+ idLoan+"'" , null);

        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {

                    try {
                        jsonClient.put("firstname", fila.getString(0));
                        jsonClient.put("lastname", fila.getString(1));
                        jsonClient.put("identification", fila.getString(2));
                        jsonClient.put("phone", fila.getString(3));
                        jsonClient.put("city", fila.getString(4));
                        jsonClient.put("adress", fila.getString(5));
                        jsonClient.put("description", fila.getString(6));
                        jsonClient.put("id", fila.getString(7));
                        jsonClient.put("phone2", fila.getString(8));
                        jsonClient.put("phone3", fila.getString(9));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (fila.moveToNext());
            }
        }else{
            getClientLoanOnline(idLoan);
        }

        try {
            nombre.setText(jsonClient.getString("firstname"));
            apellido.setText(jsonClient.getString("lastname"));
            cedula.setText(jsonClient.getString("identification"));
            telefono.setText(jsonClient.getString("phone"));
            telefono2.setText(jsonClient.getString("phone2"));
            telefono3.setText(jsonClient.getString("phone3"));
            ciudad.setText(jsonClient.getString("city"));
            direccion.setText(jsonClient.getString("adress"));
            direccionReferencia.setText(jsonClient.getString("adress"));
            id = jsonClient.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void getClientLoanOnline(String idLoan){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_LOAN_IDLOAN)+"/"+idLoan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonLoan = new JSONObject(response);
                            String telefon2 = "", telefon3 = "";

                            JSONObject client= jsonLoan.getJSONObject("client");


                            jsonClient.put("firstname", client.getString("name"));
                            jsonClient.put("lastname", client.getString("apellido"));
                            jsonClient.put("identification", client.getString("cedula"));
                            jsonClient.put("phone", client.getString("telefono"));
                            jsonClient.put("city", client.getString("ciudad"));
                            jsonClient.put("adress", client.getString("dirreccion"));
                            jsonClient.put("description", client.getString("dirreccion"));
                            jsonClient.put("id", client.getString("_id"));

                            if(client.length() > 8){
                                telefon2 = client.getString("telefono2");
                            }else if(client.length() > 9){
                                telefon3 = client.getString("telefono3");
                            }

                            nombre.setText(jsonClient.getString("firstname"));
                            apellido.setText(jsonClient.getString("lastname"));
                            cedula.setText(jsonClient.getString("identification"));
                            telefono.setText(jsonClient.getString("phone"));
                            telefono2.setText(telefon2);
                            telefono3.setText(telefon3);
                            ciudad.setText(jsonClient.getString("city"));
                            direccion.setText(jsonClient.getString("adress"));
                            direccionReferencia.setText(jsonClient.getString("adress"));
                            id = jsonClient.getString("id");
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
        nombre = view.findViewById(R.id.nombre);
        apellido = view.findViewById(R.id.apellido);
        cedula = view.findViewById(R.id.cedula);
        telefono = view.findViewById(R.id.telefono);
        telefono2 = view.findViewById(R.id.telefono2);
        telefono3 = view.findViewById(R.id.telefono3);
        ciudad = view.findViewById(R.id.ciudad);
        direccion = view.findViewById(R.id.direccion);
        direccionReferencia = view.findViewById(R.id.direc_referencia);
        historicoPrestamo = view.findViewById(R.id.historico_prestamo);
        prestamoAcual = view.findViewById(R.id.prestamo_actual);
        crearPrestamo = view.findViewById(R.id.crearPrestamo);
    }
}

