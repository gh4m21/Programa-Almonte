package com.example.almonte.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.almonte.Activities.Databases.exampleListaNoPago;
import com.example.almonte.Fragments.pagoDetailFragment;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.Toast;

import com.example.almonte.R;


public class pagoDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        int id = (int) getIntent().getSerializableExtra("ID_iTEM"); //recuperar en el activity el dato del activity lista que esta pasando

        if (savedInstanceState == null) {
            // Aqui se esta creando el fragment y le pasa el datos del id para recuperarla en el fragment
            Bundle arguments = new Bundle();
            arguments.putInt("id_item",
                    id);
            pagoDetailFragment fragment = new pagoDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, clienteNoPagoActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
