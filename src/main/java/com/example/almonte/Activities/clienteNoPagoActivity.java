package com.example.almonte.Activities;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.almonte.AdapterClienteNoPagoActivity;
import com.example.almonte.DataSource.Clients;
import com.example.almonte.DataSource.Interest;
import com.example.almonte.DataSource.Loans;
import com.example.almonte.DataSource.Payment;
import com.example.almonte.DataSource.Plans;
import com.example.almonte.DataSource.PreviewLoans;
import com.example.almonte.DataSource.Routina;
import com.example.almonte.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link pagoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class clienteNoPagoActivity extends AppCompatActivity {


    List<Routina> rutinaList = new ArrayList<>();
    RecyclerView rvItemList;
    AdapterClienteNoPagoActivity itemListAdapter;
    AdminSQLiteOpenHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cliente_no_pago);
        myDb = new AdminSQLiteOpenHelper(clienteNoPagoActivity.this, "dbSystem", null, 3);

        rvItemList = findViewById(R.id.item_list);
        rvItemList.setLayoutManager(new GridLayoutManager(this, 1));

        Bundle extra = getIntent().getExtras();
        String ciudad = extra.getString("ciudad");
        printDatafromDatabase(ciudad);


        Activity activity = this;
        Toolbar appBarLayout = activity.findViewById(R.id.toolbar);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Lista Cliente "+ciudad);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle extra = getIntent().getExtras();
        String ciudad = extra.getString("ciudad");
        printDatafromDatabase(ciudad);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extra = getIntent().getExtras();
        String ciudad = extra.getString("ciudad");
        printDatafromDatabase(ciudad);
    }

    public void printDatafromDatabase(String city){
        rutinaList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(clienteNoPagoActivity.this, "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select c.firstname,c.lastname, c.identification," +
                "l.id, l.amount,"+"c.phone,c.city || ' '|| c.adress|| ' '|| c.description, l.status, l.date" +
                " from Clients c inner join Loans l on c.id like l.client  where l.upTOdate like 'false' and c.city like '"+city+"'", null);
        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if(fila.moveToFirst()){
                do{
                    rutinaList.add(

                            new Routina (
                                    fila.getString(0),
                                    fila.getString(1),
                                    fila.getString(2),
                                    fila.getString(3),
                                    fila.getString(4),
                                    fila.getString(5),
                                    fila.getString(6),
                                    fila.getString(7),
                                    fila.getString(8)
                            )
                    );
                }while(fila.moveToNext());
            }
            itemListAdapter = new AdapterClienteNoPagoActivity (clienteNoPagoActivity.this, rutinaList);
            rvItemList.setAdapter(itemListAdapter);
        } else {
            Toast.makeText(clienteNoPagoActivity.this, "No hay registros", Toast.LENGTH_LONG).show();
        }
    }

}
