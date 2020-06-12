package com.example.almonte.Fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.clienteNoPagoActivity;
import com.example.almonte.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class pagoDetailFragment extends Fragment implements PrintingCallback {


    private TextView nombre, apellido, cedula, cuota, monto, fecha;
    EditText TextMonto;
    private Button validar, cancel;
    private String id;
    private double cuotas, amountPaid, interestPaid;
    AdminSQLiteOpenHelper myDb;
    ArrayList<Printable> printables;
    List<String> cityList = new ArrayList<>();
    private Printing printing;
    JSONObject jsonpayment;

    public pagoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle("Pagar Prestamo");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.pago_detail, container, false);

        viewHolder(rootview);

        Bundle bundle = getArguments();
        final String idLoan = bundle.getString("id_item");

        getInfoLoan(idLoan);
        myDb = new AdminSQLiteOpenHelper(getContext(), "dbSystem", null, 3);

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Double.valueOf(TextMonto.getText().toString()) >= cuotas) {
                    payLoan(idLoan);
                    try {
                        //imprimirFactura();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //String stack = e.getMessage();
                    }
                    backView();
                } else
                    Toast.makeText(getContext(), "Verifique el monto ingresado es mayor que la cuota", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return rootview;
    }

    private void backView() {
        getActivity().onBackPressed();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (printing != null) {
            printing.setPrintingCallback(this);
        }
    }

    private void imprimirFactura() {

        if (!Printooth.INSTANCE.hasPairedPrinter()) {
            startActivityForResult(new Intent(getActivity(), ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
        } else {
            factura();
            if (printing != null) {
                printing.setPrintingCallback(this);
            }
        }
    }

    private void factura() {
        printables = new ArrayList<>();

        printables.add(new TextPrintable.Builder()
                .setText("Prestamo Almonte")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("809-884-0841")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("*** Original ***")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("FACTURA DE PAGO")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("--------------------------------")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setNewLinesAfter(1)
                .build());

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Cliente : " + jsonpayment.getString("firstname") + " " + jsonpayment.getString("lastname"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Telefono : " + jsonpayment.getString("phone"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }
/*
        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Direccion : " + jsonpayment.getString("completedirection"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "2 "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
*/
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        printables.add(new TextPrintable.Builder()
                .setText("Fecha : " + dateFormat.format(date))
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setNewLinesAfter(1)
                .build());

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Tipo Cuota : " + jsonpayment.getString("plan"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Fecha Prestamo : " + jsonpayment.getString("date"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Cuota Inicial : " + jsonpayment.getString("amount"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Capital : " + jsonpayment.getDouble("amountPerQuota"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Interes : " + jsonpayment.getDouble("interestPerQuota"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        printables.add(new TextPrintable.Builder()
                .setText("Mora : " + "0.00")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("Otros cargos : " + "0.00")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setNewLinesAfter(1)
                .build());

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Total : " + jsonpayment.getDouble("amountPerQuota") + jsonpayment.getDouble("interestPerQuota"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("S. Final : " + (Double.parseDouble(jsonpayment.getString("amount")) - (jsonpayment.getDouble("amountPerQuota") + jsonpayment.getDouble("interestPerQuota"))))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        printables.add(new TextPrintable.Builder()
                .setText("Forma Pago Efectivo")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("M. Adeudado : " + "12,000.00")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("--------------------------------")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("***Original***")
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setNewLinesAfter(1)
                .build());

        printables.add(new TextPrintable.Builder()
                .setText("Factura hecho el " + dateFormat.format(date))
                .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                .setNewLinesAfter(1)
                .build());

        printing.print(printables);

    }

    public void getInfoLoan(String idLoan) {
        jsonpayment = new JSONObject();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select c.firstname,c.lastname, c.identification," +
                "l.amount," + "p.name, p.interest, l.amountPerQuota, l.interestPerQuota, l.date, l.id, c.phone," + "c.city, c.adress, c.description" +
                " from Clients c inner join Loans l on c.id  like l.client " +
                "inner join Plans p on  l.plane like p.id  where l.id like  '" + idLoan + "'", null);
        if (fila != null && fila.getCount() != 0) {
            fila.moveToFirst();

            if (fila.moveToFirst()) {
                do {

                    try {
                        jsonpayment.put("firstname", fila.getString(0));
                        jsonpayment.put("lastname", fila.getString(1));
                        jsonpayment.put("identification", fila.getString(2));
                        jsonpayment.put("amount", fila.getString(3));
                        jsonpayment.put("plan", fila.getString(4));
                        jsonpayment.put("interest", fila.getString(5));
                        jsonpayment.put("amountPerQuota", fila.getString(6));
                        jsonpayment.put("interestPerQuota", fila.getString(7));
                        jsonpayment.put("date", fila.getString(8));
                        jsonpayment.put("id", fila.getString(9));
                        jsonpayment.put("phone", fila.getString(10));
                        jsonpayment.put("completedirection", fila.getString(11) + ", " + fila.getString(12) + ", " + fila.getString(13));

                        cuotas = jsonpayment.getDouble("amountPerQuota") + jsonpayment.getDouble("interestPerQuota");
                        amountPaid = jsonpayment.getDouble("amountPerQuota");
                        interestPaid = jsonpayment.getDouble("interestPerQuota");

                        nombre.setText("" + jsonpayment.getString("firstname"));
                        apellido.setText("" + jsonpayment.getString("lastname"));
                        cedula.setText(jsonpayment.getString("identification"));
                        cuota.setText("" + cuotas + " $RD");
                        monto.setText(jsonpayment.getString("amount"));
                        fecha.setText(jsonpayment.getString("date"));
                        id = jsonpayment.getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (fila.moveToNext());
            }
        } else {
            getInfoLoanOnline(idLoan);
        }

    }

    public void getInfoLoanOnline(String idLoan) {
        jsonpayment = new JSONObject();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.GET, getResources().getString(R.string.URL_GET_LOAN_IDLOAN) + "/" + idLoan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonLoan = new JSONObject(response);

                            JSONObject client = jsonLoan.getJSONObject("client");
                            JSONObject plans = jsonLoan.getJSONObject("plan");

                            jsonpayment.put("firstname", client.getString("name"));
                            jsonpayment.put("lastname", client.getString("apellido"));
                            jsonpayment.put("idCliente", client.getString("_id"));
                            jsonpayment.put("phone", client.getString("telefono"));
                            jsonpayment.put("identification", client.getString("cedula"));
                            jsonpayment.put("amount", jsonLoan.getString("amount"));
                            jsonpayment.put("plan", plans.getString("name"));
                            jsonpayment.put("interest", plans.getString("interest"));
                            jsonpayment.put("amountPerQuota", jsonLoan.getString("amountPerQuota"));
                            jsonpayment.put("interestPerQuota", jsonLoan.getString("interestPerQuota"));
                            jsonpayment.put("date", jsonLoan.getString("date"));
                            jsonpayment.put("id", jsonLoan.getString("_id"));
                            jsonpayment.put("completedirection", jsonpayment.getString("ciudad") + ", " + client.getString("direccion") + ", " + jsonpayment.getString("RefDireccion"));


                            cuotas = jsonpayment.getDouble("amountPerQuota") + jsonpayment.getDouble("interestPerQuota");
                            amountPaid = jsonpayment.getDouble("amountPerQuota");
                            interestPaid = jsonpayment.getDouble("interestPerQuota");

                            nombre.setText("" + jsonpayment.getString("firstname"));
                            apellido.setText("" + jsonpayment.getString("lastname"));
                            cedula.setText(jsonpayment.getString("identification"));
                            cuota.setText("" + cuotas + " $RD");
                            monto.setText(jsonpayment.getString("amount"));
                            fecha.setText(jsonpayment.getString("date"));
                            id = jsonpayment.getString("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);
    }


    public void payLoan(String idLoan) {
        long isInserted;
        boolean isUpdate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        isInserted = myDb.insertNewDues(idLoan, dateFormat.format(date), dateFormat.format(date),
                String.valueOf(amountPaid), String.valueOf(interestPaid), "");
        if (isInserted != -1) {
            isUpdate = myDb.updateLoans(idLoan, "Pagado");
            Toast.makeText(getContext(), "Pago hecho...", Toast.LENGTH_SHORT).show();
            TextMonto.setText("");
        } else {
            Toast.makeText(getContext(), "No se realizo el pago...", Toast.LENGTH_SHORT).show();
        }


    }

    public void viewHolder(View view) {
        nombre = view.findViewById(R.id.nombre_cliente);
        apellido = view.findViewById(R.id.apellido_cliente);
        cedula = view.findViewById(R.id.cedula_cliente);
        cuota = view.findViewById(R.id.cuota);
        monto = view.findViewById(R.id.monto);
        fecha = view.findViewById(R.id.fecha);
        TextMonto = view.findViewById((R.id.TextMonto));
        validar = view.findViewById(R.id.pagarBtn);
        cancel = view.findViewById(R.id.cancelBtn);
    }

    @Override
    public void connectingWithPrinter() {
        Toast.makeText(getContext(), "Connectando a la impressora", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectionFailed(String s) {
        Toast.makeText(getContext(), "Fallo: " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String s) {
        Toast.makeText(getContext(), "Error: " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void printingOrderSentSuccessfully() {
        Toast.makeText(getContext(), "Impression de Factura...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK)
            //Printer is ready now
            imprimirFactura();
            Toast.makeText(getContext(), "before calling the initPrinting function...", Toast.LENGTH_SHORT).show();
            initPrinting();

    }

    private void initPrinting() {
        Toast.makeText(getContext(), "init printing...", Toast.LENGTH_SHORT).show();
        if (Printooth.INSTANCE.hasPairedPrinter())
            printing = Printooth.INSTANCE.printer();

        if (printing != null) {
            Toast.makeText(getContext(), "printing gonna call setback function...", Toast.LENGTH_SHORT).show();
            printing.setPrintingCallback(this);
        }
    }

}

