package com.example.almonte.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.almonte.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RenovarPrestamoFragment extends Fragment {


    public RenovarPrestamoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_renovar_prestamo, container, false);
    }

}
