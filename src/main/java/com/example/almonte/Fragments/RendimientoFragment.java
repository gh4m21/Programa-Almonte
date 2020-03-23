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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.almonte.Activities.Databases.AdminSQLiteOpenHelper;
import com.example.almonte.Activities.Databases.SqliteDatabase;
import com.example.almonte.Activities.Databases.exampleListaNoPago;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RendimientoFragment extends Fragment implements PrintingCallback {

    private TextView nombre, apellido, cedula, cuota, monto, fecha, mensaje;
    EditText TextMonto;
    private Button validar, cancel;
    private String id;
    private double cuotas, amountPaid,interestPaid;
    private double interest;
    AdminSQLiteOpenHelper myDb;
    private Printing printing;
    ArrayList<Printable> printables = new ArrayList<>();
    JSONObject jsonpayment = new JSONObject();

    public RendimientoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey("id_item")) {
            int id = (getArguments().getInt("id_item"));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Rendimiento");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rendimiento, container, false);
        viewHolder(rootView);


        Bundle bundle = getArguments();
        final String idLoan = bundle.getString("id_item");

        getInfoLoan(idLoan);
        myDb = new AdminSQLiteOpenHelper(getContext(), "dbSystem", null, 3);

        // Para Mostrar los datos que proviene de la lista en TextView.

        //Action evemt cuado presiona los botones
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Double.valueOf(TextMonto.getText().toString()) >= interest ){
                    payRendimiento(idLoan);
                    try {
                      //  imprimirFactura();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Verifique la impressora esta connectado para imprimir la Factura", Toast.LENGTH_SHORT).show();
                    }
                    backView();
                }else
                    Toast.makeText(getContext(), "Verifique el monto ingresado es mayor que el interes", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "cancel Button click", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public void getInfoLoan( String idLoan) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "dbSystem", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select c.firstname,c.lastname, c.identification," +
                "l.amount," +"p.name, p.interest, l.amountPerQuota, l.interestPerQuota, l.date, l.id," + "c.city || c.adress || c.description" +
                " from Clients c inner join Loans l on c.id  like l.client " +
                "inner join Plans p on  l.plane like p.id  where l.id like  '"+ idLoan+"'" , null);
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
                        jsonpayment.put("completedrirection", fila.getString(10));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } while (fila.moveToNext());
            }
        }

        try {

            cuotas = jsonpayment.getDouble("amountPerQuota")+ jsonpayment.getDouble("interestPerQuota");
            amountPaid =  jsonpayment.getDouble("amountPerQuota");
            interestPaid = jsonpayment.getDouble("interestPerQuota");

            nombre.setText(""+jsonpayment.getString("firstname"));
            apellido.setText(""+jsonpayment.getString("lastname"));
            cedula.setText(jsonpayment.getString("identification"));
            cuota.setText(""+interestPaid+" $RD");
            monto.setText(jsonpayment.getString("amount"));
            fecha.setText(jsonpayment.getString("date"));
            id = jsonpayment.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void payRendimiento(String idLoan){
        long isInserted;
        boolean isUpdate;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();

        isInserted = myDb.insertNewDues(idLoan,"",dateFormat.format(date),
                "0",String.valueOf(interestPaid),"");
        if(isInserted != -1){
            isUpdate = myDb.updateLoans(idLoan,"Pagado");
            Toast.makeText(getContext(), "Pago hecho...", Toast.LENGTH_SHORT).show();
            TextMonto.setText("");
        }else {
            Toast.makeText(getContext(), "No se realizo el pago...", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewHolder(View view) {
        nombre = view.findViewById(R.id.nombre_cliente);
        apellido = view.findViewById(R.id.apellido_cliente);
        cedula = view.findViewById(R.id.cedula_cliente);
        cuota = view.findViewById(R.id.cuota);
        monto = view.findViewById(R.id.monto);
        mensaje = view.findViewById(R.id.TextMensaje);
        fecha = view.findViewById(R.id.fecha);
        TextMonto = view.findViewById((R.id.TextMonto));
        validar= view.findViewById(R.id.pagarBtn);
        cancel = view.findViewById(R.id.cancelBtn);
    }

    private void imprimirFactura() {

        try {
            if (BluetoothAdapter.getDefaultAdapter().getState() == BluetoothAdapter.STATE_OFF) {
                return;
            }
            if (!Printooth.INSTANCE.hasPairedPrinter()) {
                startActivityForResult(new Intent(getActivity(), ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
            } else {
                factura();
                if (printing != null) {
                    printing.setPrintingCallback(this);
                }
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "Connectar una impressora para imprimir la factura", Toast.LENGTH_SHORT).show();
        }

    }

    private void factura() {
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

        try {
            printables.add(new TextPrintable.Builder()
                    .setText("Direccion : " + jsonpayment.getString("completedirection"))
                    .setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_30())
                    .setAlignment(DefaultPrinter.Companion.getALIGNMENT_LEFT())
                    .setNewLinesAfter(1)
                    .build());
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
            initPrinting();
    }

    private void initPrinting() {

        if (Printooth.INSTANCE.hasPairedPrinter())
            printing = Printooth.INSTANCE.printer();

        if (printing != null) {
            printing.setPrintingCallback(this);
        }
    }

    private void backView() {
        getActivity().onBackPressed();
    }

}
