package com.example.almonte.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.almonte.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {

    private BarChart bar;
    ArrayList<BarEntry> entries;
    ArrayList<String> Meses;
    BarDataSet barDatatSet;
    BarData barData;

    public InicioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_inicio, container, false);

        barChat(rootview);

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        barChat(this.getView());
    }

    private void barChat(View view) {
        bar = view.findViewById(R.id.barchart);

        entries = new ArrayList<>();
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));
        entries.add(new BarEntry(8f, 6));
        entries.add(new BarEntry(2f, 7));
        entries.add(new BarEntry(5f, 8));
        entries.add(new BarEntry(20f, 9));
        entries.add(new BarEntry(15f, 10));
        entries.add(new BarEntry(20f, 11));

        barDatatSet = new BarDataSet(entries,"Prestamos");

        Meses = new ArrayList<String>();

        Meses.add("Ene");
        Meses.add("Feb");
        Meses.add("Mar");
        Meses.add("Abr");
        Meses.add("May");
        Meses.add("Jun");
        Meses.add("Jul");
        Meses.add("Ago");
        Meses.add("Sep");
        Meses.add("Oct");
        Meses.add("Nov");
        Meses.add("Dic");

        barData = new BarData(Meses, barDatatSet);
        bar.setData(barData);

        bar.setDescription("Graphica de los Prestamos");  // set the description

        barDatatSet.setColors(ColorTemplate.COLORFUL_COLORS);

        bar.animateY(5000);

    }

}
