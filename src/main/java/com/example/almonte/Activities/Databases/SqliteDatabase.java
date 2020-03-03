package com.example.almonte.Activities.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Prestamo";
    private static final String TABLE_PRESTAMO = "Prestamos";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_APELLIDO = "apellido";
    private static final String COLUMN_CEDULA = "cedula";
    private static final String COLUMN_TELEFONO = "telefono";
    private static final String COLUMN_UBICACION = "ubicacion";
    private static final String COLUMN_MONTO = "monto";

    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRESTAMO_TABLE = "CREATE	TABLE " + TABLE_PRESTAMO + " (" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NOMBRE + " TEXT," + COLUMN_APELLIDO + " TEXT," + COLUMN_CEDULA + " TEXT," + COLUMN_TELEFONO + " TEXT," + COLUMN_UBICACION + " TEXT," + COLUMN_MONTO + " REAL" + " );";
        db.execSQL(CREATE_PRESTAMO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESTAMO);
        onCreate(db);
    }

    public ArrayList<exampleListaNoPago> ListPrestamo() {
        String sql = "select * from " + TABLE_PRESTAMO;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<exampleListaNoPago> storePrestamo = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);
                String cedula = cursor.getString(3);
                String telefono = cursor.getString(4);
                String ubicacion = cursor.getString(5);
                float monto = cursor.getFloat(6);
                storePrestamo.add(new exampleListaNoPago(id, nombre, apellido, cedula, telefono, ubicacion, monto));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storePrestamo;
    }

    public void addPrestamo(exampleListaNoPago exampleListaNoPago) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, exampleListaNoPago.getNombre());
        values.put(COLUMN_APELLIDO, exampleListaNoPago.getApellido());
        values.put(COLUMN_CEDULA, exampleListaNoPago.getCedula());
        values.put(COLUMN_TELEFONO, exampleListaNoPago.getTelefono());
        values.put(COLUMN_UBICACION, exampleListaNoPago.getUbicacion());
        values.put(COLUMN_MONTO, exampleListaNoPago.getMonto());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRESTAMO, null, values);
    }

    public void updatePrestamo(exampleListaNoPago exampleListaNoPago) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, exampleListaNoPago.getNombre());
        values.put(COLUMN_APELLIDO, exampleListaNoPago.getApellido());
        values.put(COLUMN_CEDULA, exampleListaNoPago.getCedula());
        values.put(COLUMN_TELEFONO, exampleListaNoPago.getTelefono());
        values.put(COLUMN_UBICACION, exampleListaNoPago.getUbicacion());
        values.put(COLUMN_MONTO, exampleListaNoPago.getMonto());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_PRESTAMO, values, COLUMN_ID + "	= ?", new String[]{String.valueOf(exampleListaNoPago.getId())});
    }

    public exampleListaNoPago findPrestamo(String name) {
        String query = "Select * FROM " + TABLE_PRESTAMO + " WHERE " + COLUMN_NOMBRE + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        exampleListaNoPago exampleListaNoPago = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            String cedula = cursor.getString(3);
            String telefono = cursor.getString(4);
            String ubicacion = cursor.getString(5);
            float monto = cursor.getFloat(6);
            exampleListaNoPago = new exampleListaNoPago(id, nombre, apellido, cedula, telefono, ubicacion, monto);
        }
        cursor.close();
        return exampleListaNoPago;
    }

    public ArrayList<exampleListaNoPago> findPrestamoById(int idItem) {
        String query = "Select * FROM " + TABLE_PRESTAMO + " WHERE " + COLUMN_ID + " = " + idItem;
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<exampleListaNoPago> storeData = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            String cedula = cursor.getString(3);
            String telefono = cursor.getString(4);
            String ubicacion = cursor.getString(5);
            float monto = cursor.getFloat(6);
            storeData.add(new exampleListaNoPago(id, nombre, apellido, cedula, telefono, ubicacion, monto));
        }
        cursor.close();
        return storeData;
    }

    public void deletePrestamo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRESTAMO, COLUMN_ID + "	= ?", new String[]{String.valueOf(id)});
    }

    public exampleListaNoPago getPrestamo(int idItem) {

        String query = "Select * FROM " + TABLE_PRESTAMO + " WHERE " + COLUMN_ID + " = " + "idItem";
        SQLiteDatabase db = this.getWritableDatabase();
        exampleListaNoPago exampleListaNoPago = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            String nombre = cursor.getString(1);
            String apellido = cursor.getString(2);
            String cedula = cursor.getString(3);
            String telefono = cursor.getString(4);
            String ubicacion = cursor.getString(5);
            float monto = cursor.getFloat(6);
            exampleListaNoPago = new exampleListaNoPago(id, nombre, apellido, cedula, telefono, ubicacion, monto);
        }
        cursor.close();

        return exampleListaNoPago;
    }
}