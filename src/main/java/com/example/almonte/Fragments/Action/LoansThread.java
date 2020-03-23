package com.example.almonte.Fragments.Action;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoansThread implements  Runnable{

    Context context;
    AdminSQLiteOpenHelper myDb;

    public LoansThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getLoans();
        Log.i("debug:" , "second" );
    }

    private void getLoans(){
        JSONObject loan = new JSONObject();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select  amount, date, client ,plane  from Loans " +
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
                        setLoanToApi(loan);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (fila.moveToNext());
            }
        }
    }
    public void setLoanToApi(final JSONObject jsonObjectProducto) throws JSONException {
        myDb = new AdminSQLiteOpenHelper(context, "dbSystem", null, 3);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://178.128.144.72/api/loan";
        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, url,
                new JSONObject(jsonObjectProducto.toString()),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context, "" + response, Toast.LENGTH_LONG).show();
                            JSONObject loan = response.getJSONObject("loan");
                            String id = loan.getString("client");
                            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "dbSystem", null, 3);
                            SQLiteDatabase db = admin.getWritableDatabase();
                            Cursor fila = db.rawQuery("select id from Loans" +
                                    " where client like '"+id+"'" , null);
                            Toast.makeText(context, "" + response, Toast.LENGTH_LONG).show();

                            if (fila != null && fila.getCount() != 0) {
                                fila.moveToFirst();

                                if (fila.moveToFirst()) {
                                    do {
                                        myDb.deleteLoan(fila.getString(0));
                                    } while (fila.moveToNext());
                                }
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
                        Toast.makeText(context, "" + stackTrace, Toast.LENGTH_LONG).show();
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
