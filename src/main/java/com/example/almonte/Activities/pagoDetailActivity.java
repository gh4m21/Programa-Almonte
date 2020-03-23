package com.example.almonte.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.almonte.Fragments.CrearPlanFragment;
import com.example.almonte.Fragments.CrearPrestamoFragment;
import com.example.almonte.Fragments.DetalleClienteFragment;
import com.example.almonte.Fragments.DetalleClienteOnline;
import com.example.almonte.Fragments.DetallePlanFragment;
import com.example.almonte.Fragments.DetallePrestamoFragment;
import com.example.almonte.Fragments.HistoriaPagoFragment;
import com.example.almonte.Fragments.ListaPrestamoFragment;
import com.example.almonte.Fragments.OpcionImprimirFragment;
import com.example.almonte.Fragments.RendimientoFragment;
import com.example.almonte.Fragments.pagoDetailFragment;
import com.example.almonte.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class pagoDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Fragment fragment = new Fragment();
        String botonClick = (String) getIntent().getSerializableExtra("botonClick");
        String id = (String) getIntent().getSerializableExtra("ID_iTEM"); //recuperar en el activity el dato del activity lista que esta pasando

        Bundle arguments = new Bundle();
        arguments.putString("id_item",
                id);

        if (savedInstanceState == null) {
            switch (botonClick) {

                case "prestamo":
                    fragment = new ListaPrestamoFragment();
                    fragment.setArguments(arguments);
                    break;

                case "pagar":
                    fragment = new pagoDetailFragment();
                    fragment.setArguments(arguments);
                    break;

                case "rendimiento":
                    fragment = new RendimientoFragment();
                    fragment.setArguments(arguments);
                    break;

                case "detallePrestamo":
                    fragment = new DetallePrestamoFragment();
                    fragment.setArguments(arguments);
                    break;

                case "crearPrestamo":
                    fragment = new CrearPrestamoFragment();
                    fragment.setArguments(arguments);
                    break;

                 case "detalleCliente":
                    fragment = new DetalleClienteFragment();
                    fragment.setArguments(arguments);
                    break;
                case "detallePlan":
                    fragment = new DetallePlanFragment();
                    fragment.setArguments(arguments);
                    break;

                case "crearPlan":
                    fragment = new CrearPlanFragment();
                    fragment.setArguments(arguments);
                    break;
                case "historialPagos":
                    fragment = new HistoriaPagoFragment();
                    fragment.setArguments(arguments);
                    break;
                case "detalleClienteOnline":
                    fragment = new DetalleClienteOnline();
                    fragment.setArguments(arguments);
                    break;
                case "crearCliente":
                    fragment = new CrearPrestamoFragment();
                    break;
                case "opcionImpressora":
                    fragment = new OpcionImprimirFragment();
                    fragment.setArguments(arguments);
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
