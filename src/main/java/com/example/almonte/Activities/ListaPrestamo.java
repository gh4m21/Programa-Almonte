package com.example.almonte.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.almonte.R;

public class ListaPrestamo extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_lista_prestamo);

        Bundle extra = getIntent().getExtras();
        String id = extra.getString("id");

    }
}
