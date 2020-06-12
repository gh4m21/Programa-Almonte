package com.example.almonte.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Login extends AppCompatActivity {

    RelativeLayout containerLogin;
    TextView creador;
    EditText _txtUsuario, _txtContrasena;
    Button btnConnectar, btnOlvidarContrasena;
    private String Token;
    final String CHANNEL_ID = "personal_notifications";
    final int NOTIFICATION_ID = 001;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewHolder();

        sp = getSharedPreferences("login", MODE_PRIVATE);

        if (sp.getBoolean("logged", false)) {
            goToMainActivity();

        } else {

            btnConnectar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String user = _txtUsuario.getText().toString();
                    String pass = _txtContrasena.getText().toString();

                    JSONObject request = new JSONObject();

                    try {
                        request.put("name", user);
                        request.put("password", pass);
                        connectUser(request);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        View rootView = super.onCreateView(name, context, attrs);

        conneccionInternet(rootView);

        return rootView;
    }

    private void viewHolder() {
        _txtUsuario = findViewById(R.id.txt_usuario);
        _txtContrasena = findViewById(R.id.txt_contrasena);
        //   containerLogin = findViewById(R.id.container_login);
        //   creador = findViewById(R.id.creadorLabel);
        btnConnectar = findViewById(R.id.btn_connectar);
    }

    private void goToMainActivity() {
        Intent intent;
        intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    public void connectUser(final JSONObject jsonObjectProducto) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, getResources().getString(R.string.URL_CHECK_AUTH),
                new JSONObject(jsonObjectProducto.toString()),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            Token = response.getString("token");

                            if (Token != null) {
                                sp.edit().putBoolean("logged", true).apply();
                                goToMainActivity();
                            } else {
                                Toast.makeText(Login.this, "Usuario o Contrasena incorrecto", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Login.this, "Usuario o Contrasena incorrecto", Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Login.this, "Tu dispositivo no esta connectado a INTERNET, verifique tu connection", Toast.LENGTH_SHORT).show();

                        //String stackTrace = Log.getStackTraceString(error);
                        //Toast.makeText(Login.this, "" + stackTrace, Toast.LENGTH_LONG).show();
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

    public void noHayInternetNotification(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_a_icon)
                .setContentTitle("No hay Internet")
                .setContentText("No olvide connectar tu dispositivo...")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("No olvide connectar tu dispositivo a Internet para sincronizar tus datos"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }

    public void conneccionInternet(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        connectivityManager.registerNetworkCallback(
                builder.build(),
                new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(@NonNull Network network) {
                        super.onAvailable(network);

                    }

                    @Override
                    public void onLost(@NonNull Network network) {
                        super.onLost(network);
                        //noHayInternetNotification(view);
                        //Toast.makeText(getBaseContext(), "Usted piede el Internet", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

}
