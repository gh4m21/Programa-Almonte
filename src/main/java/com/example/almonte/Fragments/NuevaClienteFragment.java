package com.example.almonte.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.pagoDetailActivity;
import com.example.almonte.DataSource.City;
import com.example.almonte.DataSource.Plans;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class NuevaClienteFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private TextView txtNombre, txtApellido, txtCedula, txtTelefono, txtTelefono2, txtTelefono3,
            txtDireccion, txtDirecReferencia;
    private Spinner spinnerPlan;
    private Button btnValidar;
    private ImageButton iconAdd, iconRemove2, iconRemove3;
    private LinearLayout containerTelefono1, containerTelefono2;
    private List<String> CiudadList = new ArrayList<>();
    String spinnerCiudadSelected;
    List<City> cityList = new ArrayList<>();
    AdminSQLiteOpenHelper myDb;

    public NuevaClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Crear Nueva Cliente");
            appBarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
            appBarLayout.setExpandedTitleTextAppearance(R.style.TextAppearance_Design_CollapsingToolbar_Expanded);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_nueva_cliente, container, false);
        viewHolder(rootView);
        myDb = new AdminSQLiteOpenHelper(getContext(), "dbSystem", null, 3);
        spinnerPlan = rootView.findViewById(R.id.spinner_ciudad);
        List<String> spinnerArray =  new ArrayList<String>();

        getCity();

        for(int i = 0; i < cityList.size(); i++){
            spinnerArray.add(cityList.get(i).getCity());
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlan.setAdapter(adapter);

        spinnerPlan.setOnItemSelectedListener(this);

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long isInserted;
                String idClient = txtCedula.getText().toString();
                isInserted = myDb.insertNewClients(txtCedula.getText().toString(), txtNombre.getText().toString(),
                        txtApellido.getText().toString(),txtCedula.getText().toString(), txtTelefono.getText().toString(),
                        txtTelefono2.getText().toString(), txtTelefono3.getText().toString(),
                        txtDireccion.getText().toString(), spinnerCiudadSelected,txtDirecReferencia.getText().toString());


                if (isInserted != -1) {
                    Toast.makeText(getContext(), "Cliente Guardado", Toast.LENGTH_SHORT).show();
                    txtCedula.setText("");
                    txtNombre.setText("");
                    txtApellido.setText("");
                    txtCedula.setText("");
                    txtTelefono.setText("");
                    txtTelefono2.setText("");
                    txtTelefono3.setText("");
                    txtDireccion.setText("");
                    txtDirecReferencia.setText("");
                    Toast.makeText(getContext(), "Abriendo Prestamo...", Toast.LENGTH_SHORT).show();

                    Context context = v.getContext();
                    Intent intent = new Intent(context, pagoDetailActivity.class);
                    intent.putExtra("botonClick", "crearPrestamo");
                    intent.putExtra("ID_iTEM", idClient); //pasar los datos del activity de lista en otro activity de detail
                    context.startActivity(intent);
                }

            }

        });

        iconAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNuevaCampoTelefono();
            }
        });

        iconRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCampoTelefono1();
            }
        });

        iconRemove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCampoTelefono2();
            }
        });

        return rootView;
    }

    private void removeCampoTelefono1() {
        containerTelefono1.setVisibility(View.GONE);
    }

    private void removeCampoTelefono2() {
        containerTelefono2.setVisibility(View.GONE);
    }

    private void addNuevaCampoTelefono() {

        if (containerTelefono1.getVisibility() == View.GONE) {
            containerTelefono1.setVisibility(View.VISIBLE);
        } else {
            containerTelefono2.setVisibility(View.VISIBLE);
        }
    }

    public void getCity(){
        cityList.clear();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(),"dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select  city from City", null);
        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if(fila.moveToFirst()){
                do{
                    cityList.add(
                            new City(
                                    fila.getString(0)
                            )
                    );
                }while(fila.moveToNext());
            }
        } else {
            Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
        }

    }


    public void viewHolder(View view) {
        txtNombre = view.findViewById(R.id.nombre_cliente);
        txtApellido = view.findViewById(R.id.apellido_cliente);
        txtCedula = view.findViewById(R.id.cedula_cliente);
        txtTelefono = view.findViewById(R.id.telefono_cliente1);
        txtTelefono2 = view.findViewById(R.id.telefono_cliente2);
        txtTelefono3 = view.findViewById(R.id.telefono_cliente3);
        //txtCiudad = view.findViewById(R.id.ciudad_cliente);
        txtDireccion = view.findViewById(R.id.direccion_cliente);
        txtDirecReferencia = view.findViewById(R.id.referencia_cliente);
        btnValidar = view.findViewById(R.id.validar_cliente);
        iconAdd = view.findViewById(R.id.add_button);
        iconRemove2 = view.findViewById(R.id.remove_button2);
        iconRemove3 = view.findViewById(R.id.remove_button3);
        containerTelefono1 = view.findViewById(R.id.container_telefono2);
        containerTelefono2 = view.findViewById(R.id.container_telefono3);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerCiudadSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
