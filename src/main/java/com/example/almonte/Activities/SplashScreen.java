package com.example.almonte.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.DataSource.City;
import com.example.almonte.DataSource.Plans;
import com.example.almonte.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends AppCompatActivity {
    Handler splashScreen = new Handler();
    List<Plans> planList = new ArrayList<>();
    List<City> cityList = new ArrayList<>();
    AdminSQLiteOpenHelper myDb;
    SQLiteDatabase db;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            goToLogin();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(SplashScreen.this, "dbSystem", null, 3);
        db = admin.getWritableDatabase();
        myDb = new AdminSQLiteOpenHelper(SplashScreen.this, "dbSystem", null, 3);
        backupPlanTable();
        getCity();
        splashScreen.postDelayed(runnable, 3000);

    }

    private void goToLogin() {
        Intent intent;
        intent = new Intent(getBaseContext(), Login.class);
        startActivity(intent);
    }

    public void backupPlanTable(){
        planList.clear();
            RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);
            StringRequest stringRequest;
            stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_PLANS),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonResponse = new JSONArray(response);
                                for (int i = 0; i < jsonResponse.length(); i++) {
                                    JSONObject jsonObjectResponse = jsonResponse.getJSONObject(i);
                                    planList.add(
                                            new Plans(
                                                    jsonObjectResponse.getString("_id"),
                                                    jsonObjectResponse.getString("name"),
                                                    jsonObjectResponse.getString("steps"),
                                                    jsonObjectResponse.getString("interval"),
                                                    jsonObjectResponse.getString("interest"),
                                                    jsonObjectResponse.getString("date")
                                            )
                                    );
                                }
                                //  Toast.makeText(MainActivity.this, "l"+planList.size(), Toast.LENGTH_SHORT).show();
                                setLocal();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                  String stackTrace = Log.getStackTraceString(e);
                                  Toast.makeText(SplashScreen.this,""+stackTrace,Toast.LENGTH_SHORT).show();
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
    public void setLocal() {
        if(planList.size() > 0 ){
            for (int i = 0; i < planList.size(); i++) {
                myDb.insertPlan(planList.get(i).get_id(), planList.get(i).getName(),planList.get(i).getSteps(),
                        planList.get(i).getInterval(),planList.get(i).getInterest(),planList.get(i).getDate());
            }

            db.close();
        }else {
            Toast.makeText(SplashScreen.this, "No hay planes predefinidas", Toast.LENGTH_SHORT).show();
            Toast.makeText(SplashScreen.this, "Por favor crea por lo menos una", Toast.LENGTH_SHORT).show();
            Toast.makeText(SplashScreen.this, "para poder crear prestamos", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCity(){
        cityList.clear();
        String url = "http://178.128.144.72/api/city";
        RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        try {
                            JSONArray jsonCity = new JSONArray(response);
                            for(int i = 0; i < jsonCity.length(); i++){
                                JSONObject object = jsonCity.getJSONObject(i);
                                cityList.add(
                                        new City(
                                                object.getString("name")
                                        )
                                );
                            }
                            setCityLocal();
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
    public void setCityLocal(){
        for (int i = 0; i < cityList.size(); i++){
            long isInserted = myDb.insertCity(cityList.get(i).getCity());
        }

        Toast.makeText(SplashScreen.this, ""+cityList.size(), Toast.LENGTH_SHORT).show();
    }

}
