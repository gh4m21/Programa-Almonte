package com.example.almonte.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.almonte.Fragments.CrearPrestamoFragment;
import com.example.almonte.Fragments.InicioFragment;
import com.example.almonte.Fragments.ListaClienteFragment;
import com.example.almonte.Fragments.ListaPrestamoFragment;
import com.example.almonte.Fragments.NuevaClienteFragment;
import com.example.almonte.Fragments.RenovarPrestamoFragment;
import com.example.almonte.Fragments.RoutinasFragment;
import com.example.almonte.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    //private Button linkListaRutinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

      //  linkListaRutinas = (Button) findViewById(R.id.santiago);
       // linkListaRutinas.setOnClickListener(santiagoClick);

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
                    case R.id.Nav_CrearPrestamo:
                        fragment = new CrearPrestamoFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Nav_RenovarPrestamo:
                        fragment = new RenovarPrestamoFragment();
                        FragmentTransaction = true;
                        break;
                    case R.id.Nav_ListasPrestamos:
                        fragment = new ListaPrestamoFragment();
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
                    case R.id.Nav_VerRoutinas:
                        fragment = new RoutinasFragment();
                        FragmentTransaction = true;
                        break;
                }

                if(FragmentTransaction) {
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

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
/*
    private final View.OnClickListener santiagoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent listaRutinas = new Intent(MainActivity.this, clienteNoPagoActivity.class);
            startActivity(listaRutinas);
        }
    };
*/
}
