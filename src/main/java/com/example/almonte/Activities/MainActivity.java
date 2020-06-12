package com.example.almonte.Activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Adapter_lista_cliente;
import com.example.almonte.DataSource.Clients;
import com.example.almonte.DataSource.Plans;
import com.example.almonte.DataSource.Users;
import com.example.almonte.Fragments.CrearPrestamoFragment;
import com.example.almonte.Fragments.InicioFragment;
import com.example.almonte.Fragments.ListaClienteFragment;
import com.example.almonte.Fragments.ListaPrestamoFragment;
import com.example.almonte.Fragments.NuevaClienteFragment;
import com.example.almonte.Fragments.OpcionesFragment;
import com.example.almonte.Fragments.PlanFragment;
import com.example.almonte.Fragments.RoutinasFragment;
import com.example.almonte.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("login", MODE_PRIVATE);
        editor = sp.edit();

        setToolbar();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setFragmentByDefault();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean FragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.inicio:
                        fragment = new InicioFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Nav_VerRoutinas:
                        fragment = new RoutinasFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Nav_CrearCliente:
                        fragment = new NuevaClienteFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Nav_VerCliente:
                        fragment = new ListaClienteFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Nav_Planes:
                        fragment = new PlanFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Opciones:
                        fragment = new OpcionesFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Disconnect:
                       // abrirDialog();
                        editor.clear();
                        editor.commit();
                        Intent intent;
                        intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                        finish();
                        break;
                }

                if (FragmentTransaction) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, fragment)
                            .commit();
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });
    }

    private void abrirDialog() {
        DialogDesconectar dialogDesconectar = new DialogDesconectar();
        dialogDesconectar.show(getSupportFragmentManager(), "AVISO");
    }
/*
    private void getAllClients(){
        clientList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_CLIENTS),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonClients = new JSONArray(response);
                            for (int i = 0; i < jsonClients.length(); i++) {
                                JSONObject client = jsonClients.getJSONObject(i);

                                    clientList.add(
                                            new Clients(
                                                    client.getString("_id"),
                                                    client.getString("name"),
                                                    client.getString("apellido"),
                                                    client.getString("cedula"),
                                                    client.getString("telefono"),
                                                    client.getString("dirreccion"),
                                                    client.getString("ciudad"),
                                                    client.getString("DirReferencia"),
                                                    client.getString("puntos")
                                            )
                                    );
                            }
                            backupClient();
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

    public void backupClient() {
        long isInserted;
        for (int i = 0; i < clientList.size(); i++) {
            isInserted = myDb.insertClients(clientList.get(i).get_id(), clientList.get(i).getName(), clientList.get(i).getApellido(),
                    clientList.get(i).getCedula(), clientList.get(i).getTelefono(), clientList.get(i).getDirreccion(),
                    clientList.get(i).getCiudad(), clientList.get(i).getDirReferencia());
        }
    }*/

    public void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setFragmentByDefault() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new InicioFragment())
                .commit();

        MenuItem item = navigationView.getMenu().getItem(0);
        item.setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
