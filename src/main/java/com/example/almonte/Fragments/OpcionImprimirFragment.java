package com.example.almonte.Fragments;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.ui.ScanningActivity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class OpcionImprimirFragment extends Fragment {

    private static final int REQUEST_ENABLE_BT = 1;
    Button btnConnectPrinter;
    TextView mensaje;
    BluetoothAdapter bluetoothAdapter = null;



    public OpcionImprimirFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Connectar Impressora");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_opcion_imprimir, container, false);

        btnConnectPrinter = rootview.findViewById(R.id.connectar_printer);
        mensaje = rootview.findViewById(R.id.mensaje_bluetooth);

        btnConnectPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                connectarBluetooth();
                connectarDispositivo();
            }
        });

        cambiarMensaje();

        return rootview;
    }

    private void cambiarMensaje() {
        if (Printooth.INSTANCE.hasPairedPrinter()) {
            mensaje.setText(new StringBuilder(Printooth.INSTANCE.getPairedPrinter().getName())
                    .append(" Vinculado"));
        }

    }

    private void connectarDispositivo() {
        if (!Printooth.INSTANCE.hasPairedPrinter())
            startActivityForResult(new Intent(getActivity(), ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
    }

    private void connectarBluetooth() {
         bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getContext(), "Tu telefono no soporta Bluetooth", Toast.LENGTH_SHORT).show();
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent activarBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(activarBtIntent, REQUEST_ENABLE_BT);
        }

    }

}
