package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.clienteNoPagoActivity;
import com.example.almonte.Adapter_lista_prestamo;
import com.example.almonte.Adapter_recyclerview_rutinas;

import com.example.almonte.DataSource.City;
import com.example.almonte.DataSource.Clients;
import com.example.almonte.DataSource.Loans;
import com.example.almonte.DataSource.Payment;
import com.example.almonte.Fragments.Action.ClientLoanUpdate;
import com.example.almonte.Fragments.Action.ClientLoansTrhead;
import com.example.almonte.Fragments.Action.LoansThread;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutinasFragment extends Fragment {

    private List<String> ciudades;

    private RecyclerView rvRutina;
    Adapter_recyclerview_rutinas rutiAdapter;
    private Button btnSincronizar, request;
    private ProgressBar progressSincronizar;

    List<Clients> clientList = new ArrayList<>();
    List<Loans> loansList = new ArrayList<>();
    List<City> cityList = new ArrayList<>();
    AdminSQLiteOpenHelper myDb;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public RoutinasFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();

        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Lista Rutina");
        }
        //    }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_routinas, container, false);

        btnSincronizar = rootview.findViewById(R.id.sincronizar);
        request = rootview.findViewById(R.id.request);
        progressSincronizar = rootview.findViewById(R.id.progress_bar_sincronizar);
        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRoutina();
            }
        });
        rvRutina = rootview.findViewById(R.id.reciclerViewRutina);
        rvRutina.setLayoutManager(new GridLayoutManager(getActivity(), 1));


        getCity();

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread firstTask = new Thread( new ClientLoansTrhead(getActivity()));
                  firstTask.setPriority(Thread.MAX_PRIORITY);
                  firstTask.start();
                  getLoans();
                try {
                    getPayment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        myDb = new AdminSQLiteOpenHelper(getContext(), "dbSystem", null, 3);


        rutiAdapter = new Adapter_recyclerview_rutinas(cityList,getContext());
        rvRutina.setAdapter(rutiAdapter);

        return rootview;
    }


    public List<String> getAllCiudades() {
        return new ArrayList<String>() {{
            add("santiago");
            add("Puerto Plata");
            add("Moca");
            add("Mao");
        }};
    }


    public void getCity(){
        cityList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(),"dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select  city from City", null);
        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if(fila.moveToFirst()){
                do{
                    cityList.add(
                            new City(
                                    fila.getString(0)
                            )
                    );
                }while(fila.moveToNext());
            }
            Toast.makeText(getActivity(), ""+clientList.size(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
        }

    }

    public void getRoutina() {
        progressSincronizar.setVisibility(View.VISIBLE);
        loansList.clear();
        clientList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.GET_ALL_ROUTINE_LOANS),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonCities = new JSONArray();
                            JSONObject jsonRoutina = new JSONObject(response);
                            JSONArray jsonRoute = jsonRoutina.getJSONArray("cities");
                            String [] array = new String [jsonRoute.length()];

                            JSONArray jsonResponse = jsonRoutina.getJSONArray("response");
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                JSONObject jsonObjectResponse = jsonResponse.getJSONObject(i);
                                jsonCities = jsonObjectResponse.getJSONArray("city");

                                for (int a = 0; a < jsonCities.length(); a++) {

                                    //Get the object at this index
                                    JSONObject loans = jsonCities.getJSONObject(a);

                                    //Dispatch the API Response
                                    JSONObject clients = loans.getJSONObject("client");
                                    JSONObject plans = loans.getJSONObject("plan");

                                    // Get the sub-document id
                                    String plan = plans.getString("_id");
                                    String client = clients.getString("_id");
                                  //  Toast.makeText(getContext(), "" + clients, Toast.LENGTH_LONG).show();
                                    //Fill the clientList
                                    clientList.add(
                                            new Clients(
                                                    clients.getString("_id"),
                                                    clients.getString("name"),
                                                    clients.getString("apellido"),
                                                    clients.getString("cedula"),
                                                    clients.getString("telefono"),
                                                    clients.getString("telefono"),
                                                    clients.getString("telefono"),
                                                    clients.getString("dirreccion"),
                                                    clients.getString("ciudad"),
                                                    clients.getString("DirReferencia")
                                            )
                                    );
                                    //Fill the loans list
                                    loansList.add(
                                            new Loans(
                                                    loans.getString("_id"),
                                                    client,
                                                    loans.getString("amount"),
                                                    loans.getString("amountPerQuota"),
                                                    loans.getString("interestPerQuota"),
                                                    plan,
                                                    loans.getString("status"),
                                                    loans.getString("quota"),
                                                    loans.getString("nextpaymentDate"),
                                                    loans.getString("date")
                                            )
                                    );
                                }
                            }
                            backupRoutinaInLocal();
                            progressSincronizar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            progressSincronizar.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                            String stackTrace = Log.getStackTraceString(e);
                           // Toast.makeText(getContext(), "" + stackTrace, Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressSincronizar.setVisibility(View.VISIBLE);
                error.printStackTrace();
                String stackTrace = Log.getStackTraceString(error);
             // Toast.makeText(getContext(), "" + stackTrace, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }
    public void backupLoans() {
        long isInserted;
        for (int i = 0; i < loansList.size(); i++) {
            isInserted = myDb.insertLoans(loansList.get(i).get_id(), loansList.get(i).getAmount(),
                    loansList.get(i).getAmountPerQuota(),loansList.get(i).getInterestPerQuota(),
                    loansList.get(i).getStatus(),loansList.get(i).getQuota(),
                    loansList.get(i).getNextpaymentDate(),loansList.get(i).getDate(),
                    loansList.get(i).getClient(),loansList.get(i).getPlan());
         //   Toast.makeText(getContext(), "" + isInserted, Toast.LENGTH_LONG).show();
        }
    }
    public void backupClient() {
        long isInserted;

        for (int i = 0; i < clientList.size(); i++) {
            isInserted = myDb.insertClients(clientList.get(i).get_id(), clientList.get(i).getName(),
                    clientList.get(i).getApellido(), clientList.get(i).getCedula(),
                    clientList.get(i).getTelefono(),clientList.get(i).getTelefono2(),
                    clientList.get(i).getTelefono3(),clientList.get(i).getDirreccion(),
                    clientList.get(i).getCiudad(), clientList.get(i).getDirReferencia());
          //  Toast.makeText(getContext(), "" + isInserted, Toast.LENGTH_LONG).show();
        }
    }
    public void backupRoutinaInLocal() {
        backupClient();
        backupLoans();
    }

    private void getLoans(){
        JSONObject loan = new JSONObject();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select  amount, date, client ,plane  from Loans  " +
                "where upTOdate like  'true'" , null);
        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {
                    try {
                        loan.put("amount", fila.getString(0));
                        loan.put("date", fila.getString(1));
                        loan.put("client", fila.getString(2));
                        loan.put("plan", fila.getString(3));
                        Toast.makeText(getContext(), "" +loan, Toast.LENGTH_LONG).show();
                        setLoanToApi(loan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (fila.moveToNext());
            }
        }
    }
    public void setLoanToApi(final JSONObject jsonObjectProducto) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, getResources().getString(R.string.URL_GET_LOAN_IDLOAN),
                new JSONObject(jsonObjectProducto.toString()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                            JSONObject loan = response.getJSONObject("loan");
                            String id = loan.getString("client");

                            myDb.deleteLoan(id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String stackTrace = Log.getStackTraceString(error);
                        Toast.makeText(getContext(), "" + stackTrace, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        requestQueue.add(postRequest);
    }

    private void getPayment() throws JSONException {
        JSONObject request = new JSONObject();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery(" select loan, amountPaid, interestPaid " +
                " from Dues  WHERE upTOdate LIKE  'true'" , null);

        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {
                    request.put("amount",fila.getString(1));
                    request.put("interest",fila.getString(2));
                    String id = fila.getString(0);
                    String url = getResources().getString(R.string.URL_SET_DUES)+"/"+""+id;
                    Toast.makeText(getContext(), "" + request, Toast.LENGTH_LONG).show();
                    setLoanApi(request,url);
                } while (fila.moveToNext());
            }
        }
    }
    public void setLoanApi(final JSONObject jsonObjectProducto,String url) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,
                new JSONObject(jsonObjectProducto.toString()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(), "" + response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject loan = response.getJSONObject("loan_");
                            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
                            SQLiteDatabase db = admin.getWritableDatabase();
                            Cursor fila = db.rawQuery(" select loan" +
                                    " from Dues where loan like '"
                                    +loan.getString("_id")+"'", null);

                            if(fila.getCount() > 0){
                                myDb.deleteDue(loan.getString("_id"));
                                myDb.deleteLoan(loan.getString("_id"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String stackTrace = Log.getStackTraceString(error);
                        Toast.makeText(getContext(), "" + stackTrace, Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        requestQueue.add(postRequest);
    }

}
