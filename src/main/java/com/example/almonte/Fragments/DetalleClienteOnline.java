package com.example.almonte.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.example.almonte.Adapter_lista_cliente;
import com.example.almonte.DataSource.Clients;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalleClienteOnline extends Fragment {

    private TextView nombre, apellido, cedula, telefono, telefono2, telefono3, ciudad, direccion, direccionReferencia, punto;
    private Button prestamoAcual, historicoPrestamo, crearPrestamo;
    private String id;
    JSONObject jsonClient = new JSONObject();

    public  DetalleClienteOnline() {
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
        View rootview = inflater.inflate(R.layout.detalle_cliente_online, container, false);

        viewHolder(rootview);

        Bundle bundle =getArguments();
        String idLoan = bundle.getString("id_item");

        getClientOnLocal(idLoan);


        return rootview;
    }

    private void getClientByid(String idCLient){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_CLIENTS)+"/"+idCLient,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject client = new JSONObject(response);
                            jsonClient.put("firstname", client.getString("name"));
                            jsonClient.put("lastname", client.getString("apellido"));
                            jsonClient.put("identification", client.getString("cedula"));
                            jsonClient.put("phone", client.getString("telefono"));
                            jsonClient.put("phone2", client.getString("telefono2"));
                            jsonClient.put("phone3", client.getString("telefono3"));
                            jsonClient.put("city", client.getString("ciudad"));
                            jsonClient.put("adress", client.getString("direccion"));
                            jsonClient.put("description", client.getString("RefDireccion"));
                            jsonClient.put("points", client.getString("puntos"));
                            jsonClient.put("id", client.getString("_id"));

                            nombre.setText(jsonClient.getString("firstname"));
                            apellido.setText(jsonClient.getString("lastname"));
                            cedula.setText(jsonClient.getString("identification"));
                            telefono.setText(jsonClient.getString("phone"));
                            telefono2.setText(jsonClient.getString("phone2"));
                            telefono3.setText(jsonClient.getString("phone3"));
                            ciudad.setText(jsonClient.getString("city"));
                            direccion.setText(jsonClient.getString("adress"));
                            id = jsonClient.getString("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                String stackTrace = Log.getStackTraceString(error);
            }
        });
        requestQueue.add(stringRequest);

    }




    private void getClientOnLocal(String idClient){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select *  from Clients where id like  '"+idClient+"'" , null);

        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {

                    try {
                        jsonClient.put("id", fila.getString(1));
                        jsonClient.put("firstname", fila.getString(2));
                        jsonClient.put("lastname", fila.getString(3));
                        jsonClient.put("identification", fila.getString(4));
                        jsonClient.put("phone", fila.getString(5));
                        jsonClient.put("phone2", fila.getString(6));
                        jsonClient.put("phone3", fila.getString(7));
                        jsonClient.put("city", fila.getString(8));
                        jsonClient.put("adress", fila.getString(9));
                        jsonClient.put("description", fila.getString(10));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (fila.moveToNext());
            }
        }
        if(jsonClient.length() > 0 ){
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
        }else{
            getClientByid(idClient);
        }
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
    }
}

