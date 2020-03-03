package com.example.almonte.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.almonte.Fragments.CrearPrestamoFragment;
import com.example.almonte.Fragments.DetallePrestamoFragment;
import com.example.almonte.Fragments.RendimientoFragment;
import com.example.almonte.Fragments.pagoDetailFragment;
import com.example.almonte.R;


public class pagoDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Aqui se esta creando el fragment y le pasa el datos del id para recuperarla en el fragment
        Fragment fragment = new Fragment();
        String botonClick = (String) getIntent().getSerializableExtra("botonClick");
        int id = (int) getIntent().getSerializableExtra("ID_iTEM"); //recuperar en el activity el dato del activity lista que esta pasando

        Bundle arguments = new Bundle();
        arguments.putInt("id_item",
                id);

        if (savedInstanceState == null) {
            switch (botonClick) {
                case "pagar":
                    fragment = new pagoDetailFragment();
                    fragment.setArguments(arguments);
                    break;

                case "rendimiento":
                    fragment = new RendimientoFragment();
                    fragment.setArguments(arguments);
                    break;

                case "detalle":
                    fragment = new DetallePrestamoFragment();
                    fragment.setArguments(arguments);
                    break;

                case "crearPrestamo":
                    fragment = new CrearPrestamoFragment();
                    fragment.setArguments(arguments);
                    break;

                case "crearPlanes":

                    break;

                case "detalleCliente":

                    break;
            }


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
