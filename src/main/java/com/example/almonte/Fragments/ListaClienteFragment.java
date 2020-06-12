package com.example.almonte.Fragments;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Adapter_lista_cliente;
import com.example.almonte.DataSource.Clients;
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
public class ListaClienteFragment extends Fragment {

    List<Clients> clientList= new ArrayList<>();
    RecyclerView rvClientt;
    Adapter_lista_cliente clientAdapter;

    public ListaClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Lista Cliente");
            }
      //  }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_lista_cliente, container, false);

        rvClientt = rootview.findViewById(R.id.recycler_view_lista_cliente);
        rvClientt.setLayoutManager(new GridLayoutManager(getActivity(), 1));


        getClientOnLocal();

        if(clientList.size() > 0){
            clientAdapter = new Adapter_lista_cliente(getActivity(), clientList);
            rvClientt.setAdapter(clientAdapter);
        }else{
            Toast.makeText(getActivity(), "No hay clientes", Toast.LENGTH_SHORT).show();
        }
        return rootview;

    }

    private void getAllClients(){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_CLIENTS),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonClients = new JSONArray(response);
                            for (int i = 0; i < jsonClients.length(); i++) {
                                JSONObject client = jsonClients.getJSONObject(i);
                                String id = client.getString("_id");
                                boolean encontrado = false;

                                for (int j = 0; j < clientList.size(); j++){
                                    if(id.equals(clientList.get(j).get_id())){
                                        encontrado =true;
                                    }
                                }

                                if (!encontrado){
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
                                }

                            }

                            if(clientList.size() > 0){
                                clientAdapter = new Adapter_lista_cliente(getActivity(), clientList);
                                rvClientt.setAdapter(clientAdapter);
                            }else{
                                Toast.makeText(getActivity(), "No hay clientes", Toast.LENGTH_LONG).show();
                            }

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
    private void getClientOnLocal(){
        clientList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select *  from Clients " , null);

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
        getAllClients();
    }

}
