package com.example.almonte.Fragments;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Adapter_lista_cliente;
import com.example.almonte.Adapter_lista_prestamo;
import com.example.almonte.DataSource.Clients;
import com.example.almonte.DataSource.Loans;
import com.example.almonte.DataSource.LoansByClient;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListaPrestamoFragment extends Fragment {

    List<Clients> clientList= new ArrayList<>();
    List<LoansByClient> loansList = new ArrayList<>();
    RecyclerView rvPrestamo;
    Adapter_lista_prestamo loanAdapter;

    TextView nombre_cliente, apellido_cliente, telefono_cliente, cedula_cliente;

    public ListaPrestamoFragment() {    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Lista Prestamo");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_prestamo, container, false);
        viewHolder(view);

        Bundle bundle =getArguments();
        String idLoan = bundle.getString("id_item");



        rvPrestamo = view.findViewById(R.id.recycler_view_lista_prestamo);
        rvPrestamo.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        getClientOnLocal(idLoan);
        getLoansByClientLocal(idLoan);
        if(loansList.size() > 0 ){
            loanAdapter = new Adapter_lista_prestamo(getActivity(), loansList);
            rvPrestamo.setAdapter(loanAdapter);
        }else{
            Toast.makeText(getActivity(), "Ese cliente no tiene historial", Toast.LENGTH_LONG).show();
        }
        return view;

    }

    private void getClientOnLocal(String idClient){
        clientList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select *  from Clients where id like  '"+ idClient+"'"  , null);

        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {

                    clientList.add(
                            new Clients(
                                    fila.getString(1),
                                    fila.getString(2),
                                    fila.getString(3),
                                    fila.getString(4),
                                    fila.getString(5),
                                    fila.getString(6),
                                    fila.getString(7),
                                    fila.getString(8),
                                    fila.getString(9),
                                    fila.getString(10)
                            )
                    );
                } while (fila.moveToNext());
            }
        }

        if(clientList.size() > 0){
            nombre_cliente.setText(clientList.get(0).getName());
            apellido_cliente.setText(clientList.get(0).getApellido());
            cedula_cliente.setText(clientList.get(0).getCedula());
            telefono_cliente.setText(clientList.get(0).getTelefono());
        }else{
            getClient(idClient);
        }

    }
    private void getClient(final String idClient){
        clientList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_CLIENTS)+"/"+idClient,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                                JSONObject client = new JSONObject(response);
                                clientList.add(
                                        new Clients(
                                                client.getString("_id"),
                                                client.getString("name"),
                                                client.getString("apellido"),
                                                client.getString("cedula"),
                                                client.getString("telefono"),
                                                client.getString("telefono"),
                                                client.getString("telefono"),
                                                client.getString("dirreccion"),
                                                client.getString("ciudad"),
                                                client.getString("dirreccion")
                                        )
                                );
                            nombre_cliente.setText(clientList.get(0).getName());
                            apellido_cliente.setText(clientList.get(0).getApellido());
                            cedula_cliente.setText(clientList.get(0).getCedula());
                            telefono_cliente.setText(clientList.get(0).getTelefono());

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

    private void getLoansByClient(final String idClient){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_LOANS_BY_CLIENT)+"/"+idClient,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonLoans = new JSONArray(response);
                            for (int i = 0; i < jsonLoans.length(); i++) {
                                JSONObject loan = jsonLoans.getJSONObject(i);
                                JSONObject plans = loan.getJSONObject("plan");
                                String plan = plans.getString("name");
                                String idLoan = loan.getString("_id");

                                boolean encontrado = false;

                                for (int j = 0; j < loansList.size(); j++){
                                    if(idLoan.equals(loansList.get(j).get_id())){
                                        encontrado =true;
                                    }
                                }

                                if (!encontrado){
                                    loansList.add(
                                            new LoansByClient(
                                                    loan.getString("_id"),
                                                    loan.getString("client"),
                                                    loan.getString("amount"),
                                                    plan,
                                                    loan.getString("status"),
                                                    loan.getString("amountPerQuota"),
                                                    loan.getString("interestPerQuota"),
                                                    loan.getString("date")
                                            )
                                    );
                                }
                            }
                            if(loansList.size() > 0){
                                loanAdapter = new Adapter_lista_prestamo(getActivity(), loansList);
                                rvPrestamo.setAdapter(loanAdapter);
                            }else{
                                Toast.makeText(getActivity(), "No tiene historial", Toast.LENGTH_SHORT).show();
                            }
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
    public void getLoansByClientLocal(String idClient){
        loansList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select l.id, l.client, l.amount, p.name , l.status, l.amountPerQuota," +
                "l.interestPerQuota, l.date " +
                "from Loans l inner join Plans p on l.plane like p.id " +
                " where l.client like  '"+ idClient+"'"  , null);
        StringBuffer buffer = new StringBuffer();
        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {
                    loansList.add(
                            new LoansByClient(
                                    fila.getString(0),
                                    fila.getString(1),
                                    fila.getString(2),
                                    fila.getString(3),
                                    fila.getString(4),
                                    fila.getString(5),
                                    fila.getString(6),
                                    fila.getString(7)
                            )
                    );
                } while (fila.moveToNext());
            }
        }
        getLoansByClient(idClient);
    }

    public void viewHolder(View itemView) {
        nombre_cliente = itemView.findViewById(R.id.nombre_cliente);
        apellido_cliente = itemView.findViewById(R.id.apellido_cliente);
        cedula_cliente = itemView.findViewById(R.id.cedula_cliente);
        telefono_cliente = itemView.findViewById(R.id.telefono_cliente);
    }

}