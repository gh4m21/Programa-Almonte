package com.example.almonte.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;

import com.example.almonte.DataSource.Clients;
import com.example.almonte.DataSource.Interest;
import com.example.almonte.DataSource.Loans;
import com.example.almonte.DataSource.Payment;
import com.example.almonte.DataSource.Plans;
import com.example.almonte.DataSource.PreviewLoans;
import com.example.almonte.DataSource.Routina;
import com.example.almonte.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoadRoutinaInLocal extends AppCompatActivity {

    //All Lists for receive data's routina loans that We need to the rest API
    List<Clients> clientList = new ArrayList<>();
    List<Loans> loansList = new ArrayList<>();
    List<Payment> paymentList = new ArrayList<>();
    List<Interest> interestList = new ArrayList<>();
    List<PreviewLoans> previewLoansList = new ArrayList<>();
    AdminSQLiteOpenHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new AdminSQLiteOpenHelper(LoadRoutinaInLocal.this, "dbSystem", null, 3);

      //  getRoutina();

    }
/*
    public void getRoutina() {
        loansList.clear();
        clientList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(LoadRoutinaInLocal.this);
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.GET_ALL_ROUTINE_LOANS),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonCities = new JSONArray();
                            JSONObject jsonRoutina = new JSONObject(response);
                            JSONArray  jsonResponse = jsonRoutina.getJSONArray("response");
                            for (int i = 0; i < jsonResponse.length(); i++) {
                                JSONObject jsonObjectResponse = jsonResponse.getJSONObject(i);
                                jsonCities = jsonObjectResponse.getJSONArray("city");

                                for (int a = 0; a < jsonCities.length(); a++) {

                                    //Get the object at this index
                                    JSONObject loans = jsonCities.getJSONObject(a);

                                    //Dispatch the API Response
                                    JSONObject clients = loans.getJSONObject("client");
                                    JSONObject plans = loans.getJSONObject("plan");
                                    JSONArray payment = loans.getJSONArray("pagos");
                                    JSONArray interest = loans.getJSONArray("interes");
                                    JSONArray previews = loans.getJSONArray("antecedentes");

                                    // Get the sub-document id
                                    String plan = plans.getString("_id");
                                    String client = clients.getString("_id");
                                    //Fill the loans list
                                    loansList.add(
                                            new Loans(
                                                    loans.getString("_id"),
                                                    client,
                                                    loans.getString("monto"),
                                                    plan,
                                                    loans.getString("estadoPago"),
                                                    loans.getString("status"),
                                                    loans.getString("fecha")
                                            )
                                    );
                                    //Fill the clientList
                                    clientList.add(
                                            new Clients(
                                                    clients.getString("_id"),
                                                    clients.getString("name"),
                                                    clients.getString("apellido"),
                                                    clients.getString("cedula"),
                                                    clients.getString("telefono"),
                                                    clients.getString("dirreccion"),
                                                    clients.getString("ciudad"),
                                                    clients.getString("DirReferencia"),
                                                    clients.getString("puntos")
                                            )
                                    );

                                    for (int p = 0; p < payment.length(); p++){
                                        //Get the object payment at this index
                                        JSONObject object = payment.getJSONObject(p);
                                        //Fill the paymentList
                                        paymentList.add(
                                                new Payment(
                                                        loans.getString("_id"),
                                                        object.getString("fecha"),
                                                        object.getString("monto")
                                                )
                                        );
                                    }

                                    for (int p = 0; p < previews.length(); p++){
                                        //Get the object previews at this index
                                        JSONObject object = previews.getJSONObject(p);
                                        String idPreviewLoan = object.getString("id");
                                        //Fill the previewList
                                        previewLoansList.add(
                                                new PreviewLoans(
                                                        loans.getString("_id"),
                                                        idPreviewLoan
                                                )
                                        );
                                    }


                                    for (int p = 0; p < interest.length(); p++){
                                        //Get the object interest at this index
                                        JSONObject object = interest.getJSONObject(p);
                                        //Fill the interestList
                                        interestList.add(
                                                new Interest(
                                                        loans.getString("_id"),
                                                        object.getString("fecha"),
                                                        object.getString("monto")
                                                )
                                        );
                                    }


                                }
                            }
                            backupRoutinaInLocal();
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

    // Clone the data which I received from the API.....
    public void backupClient(){
        long isInserted;
        for (int i = 0; i < clientList.size(); i++) {
            isInserted = myDb.insertClients(clientList.get(i).get_id(),clientList.get(i).getName(),clientList.get(i).getApellido(),
                    clientList.get(i).getCedula(),clientList.get(i).getTelefono(),clientList.get(i).getDirreccion(),
                    clientList.get(i).getCiudad(),clientList.get(i).getDirReferencia());
        }
    }

    public void backupLoans(){
        long isInserted;
        for (int i = 0; i < loansList.size(); i++) {
            isInserted = myDb.insertLoans(loansList.get(i).get_id(),loansList.get(i).getMonto(),loansList.get(i).getEstadoPago(),
                    loansList.get(i).getStatus(),loansList.get(i).getFecha(),loansList.get(i).getClient(),loansList.get(i).getPlan());
        }
    }

    public void backupPayment(){
        long isInserted;

        for (int i = 0; i < paymentList.size(); i++) {
            isInserted = myDb.insertDues(paymentList.get(i).getIdLoan(),paymentList.get(i).getMonto(),paymentList.get(i).getFecha(),"true");
        }
    }

    public void backupInterest(){
        long isInserted;
        for (int i = 0; i < interestList.size(); i++) {

            isInserted = myDb.insertInterest(interestList.get(i).getIdLoan(),interestList.get(i).getMonto(),interestList.get(i).getFecha());
        }
    }

    public void backupPreview(){
        long isInserted;
        for (int i = 0; i < previewLoansList.size(); i++) {
            isInserted = myDb.insertPreviewLoans(previewLoansList.get(i).getIdLoan(),previewLoansList.get(i).getIdPreviewLoan());
        }
    }

    public void backupRoutinaInLocal(){
        backupClient();
        backupLoans();
        backupPayment();
        backupInterest();
        backupPreview();
    }

 */
}
