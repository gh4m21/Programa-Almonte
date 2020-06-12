package com.example.almonte.Activities.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static final String SQL_CREATE_ClIENTS =
            "CREATE TABLE Clients ("+"idClient INTEGER  PRIMARY KEY AUTOINCREMENT,"+"id TEXT UNIQUE,"+ "firstname TEXT NOT NULL,"+
                    "lastname TEXT NOT NULL,"+"identification TEXT NOT NULL UNIQUE,"+"phone TEXT NOT NULL,"+"phone2 TEXT,"+
                    "phone3 TEXT ,"+
                    "adress TEXT NOT NULL,"+"city TEXT NOT NULL,"+"description TEXT NOT NULL,"+
                    "points INTEGER DEFAULT 500,"+"upTOdate TEXT DEFAULT 'false',"+"sincronized TEXT DEFAULT 'false'"+")";

    private static final String SQL_CREATE_PLAN =
            "CREATE TABLE Plans("+ "idPlan INTEGER  PRIMARY KEY AUTOINCREMENT,"+
                    "id TEXT UNIQUE,"+"name TEXT UNIQUE,"+"steps TEXT NOT NULL,"+"interval TEXT ,"+
                    "interest TEXT ,"+"date TEXT NOT NULL,"+"upTOdate TEXT DEFAULT 'false'"+")";

    private static final String SQL_CREATE_LOANS =
            "CREATE TABLE Loans ("+"idLoan INTEGER  PRIMARY KEY AUTOINCREMENT,"+"id TEXT UNIQUE,"+
                    "amount TEXT NOT NULL,"+"amountPerQuota TEXT DEFAULT '0',"+
                    "interestPerQuota TEXT DEFAULT '0',"+"status TEXT DEFAULT 'false',"
                    +"quota TEXT DEFAULT '0',"+"nextpaymentDate TEXT,"+
                    "date TEXT,"+"upTOdate TEXT DEFAULT 'false',"+"client TEXT,"+"plane TEXT,"+
                   "FOREIGN KEY(client) REFERENCES Clients(id),"+"FOREIGN KEY(plane) REFERENCES Plans(id)"+")";

    private static final String SQL_CREATE_DUES =
            "CREATE TABLE Dues ("+"idDue INTEGER  PRIMARY KEY AUTOINCREMENT,"+"id TEXT UNIQUE ,"+"loan TEXT NOT NULL,"+"dateToPay TEXT,"+
                    "dateAmountPaid TEXT,"+"dateInterestPaid TEXT ,"+"quota TEXT ,"+"status TEXT ,"+"amountPaid TEXT ,"+
                    "interestPaid TEXT ,"+"comment TEXT ,"+"upTOdate TEXT DEFAULT 'false',"+
                    "FOREIGN KEY(loan) REFERENCES Loans(id)"+ ")";

    private static final String SQL_CREATE_CITY =
            "CREATE TABLE City ("+ "id INTEGER  PRIMARY KEY AUTOINCREMENT,"+
                    "city TEXT UNIQUE"+")";

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ClIENTS);
        db.execSQL(SQL_CREATE_CITY);
        db.execSQL(SQL_CREATE_LOANS);
        db.execSQL(SQL_CREATE_DUES);
        db.execSQL(SQL_CREATE_PLAN);
    }

    public long insertClients(String id, String firstname, String lastname, String identification, String phone, String phone2, String phone3, String adress, String city, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("identification", identification);
        contentValues.put("phone", phone);
        contentValues.put("phone2", phone2);
        contentValues.put("phone3", phone3);
        contentValues.put("adress", adress);
        contentValues.put("city", city);
        contentValues.put("description", description);
        long result = db.insertWithOnConflict("Clients",null ,contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return result;
    }
    public Integer deleteClient (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete("Clients", "id = ?",new String[] {id});
    }
    public long insertNewClients(String id, String firstname, String lastname, String identification, String phone,String phone2, String phone3, String adress, String city, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("identification", identification);
        contentValues.put("phone", phone);
        contentValues.put("phone2", phone2);
        contentValues.put("phone3", phone3);
        contentValues.put("adress", adress);
        contentValues.put("city", city);
        contentValues.put("description", description);
        contentValues.put("upTOdate", "true");
        long result = db.insertWithOnConflict("Clients",null ,contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return result;
    }
    public boolean updateClients(String id, String cedula) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("upTOdate", "false");

        db.update("Clients", contentValues, "id = ?",new String[] { cedula });
        return true;
    }

    public long insertPlan(String id, String name, String steps, String interval ,String interest, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("steps", steps);
        contentValues.put("interval", interval);
        contentValues.put("interest", interest);
        contentValues.put("date", date);
        long result = db.insert("Plans",null ,contentValues);
        return result;
    }
    public long insertNewPlan(String id, String name, String steps, String interval ,String interest, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("steps", steps);
        contentValues.put("interval", interval);
        contentValues.put("interest", interest);
        contentValues.put("date", date);
        contentValues.put("upTOdate", "true");
        long result = db.insert("Plans",null ,contentValues);
        return result;
    }

    public long insertLoans(String id,String amount, String amountPerQuota, String interestPerQuota,
                            String status, String quota, String nextPaymentDate, String date,String idClient, String plan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("amount", amount);
        contentValues.put("amountPerQuota", amountPerQuota);
        contentValues.put("interestPerQuota", interestPerQuota);
        contentValues.put("status", status);
        contentValues.put("quota", quota);
        contentValues.put("nextpaymentDate", nextPaymentDate);
        contentValues.put("date", date);
        contentValues.put("client", idClient);
        contentValues.put("plane", plan);
        long result = db.insert("Loans",null ,contentValues);
        return result;
    }
    public Integer deleteLoan (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Loans", "id = ?",new String[] {id});

    }
    public long insertNewLoans(String id,String amount, String date,String idClient, String plan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("amount", amount);
        contentValues.put("date", date);
        contentValues.put("upTOdate", "true");
        contentValues.put("client", idClient);
        contentValues.put("plane", plan);
        long result = db.insert("Loans",null ,contentValues);
        return result;
    }
    public boolean updateLoans(String id,String upToDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("upTOdate", upToDate);
        db.update("Loans", contentValues, "id = ?",new String[] { id });
        return true;
    }
    public boolean updateLoan(String id,String client) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("client", client);
        db.update("Loans", contentValues, "id = ?",new String[] { id });
        return true;
    }

    public long insertDues(String idLoan,String dateToPay,String dateamountPaid, String dateInterestPaid, String quota, String status,String amountPaid
                           , String interestPaid, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("loan",idLoan);
        contentValues.put("dateToPay",dateToPay);
        contentValues.put("dateAmountPaid",dateamountPaid);
        contentValues.put("dateInterestPaid",dateInterestPaid);
        contentValues.put("quota",quota);
        contentValues.put("status",status);
        contentValues.put("amountPaid",amountPaid);
        contentValues.put("interestPaid",interestPaid);
        contentValues.put("comment",comment);
        long result = db.insert("Dues",null ,contentValues);
        return result;
    }
    public long insertNewDues(String idLoan,String dateamountPaid, String dateInterestPaid,String amountPaid
            ,String interestPaid, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("loan",idLoan);
        contentValues.put("dateAmountPaid",dateamountPaid);
        contentValues.put("dateInterestPaid",dateInterestPaid);
        contentValues.put("amountPaid",amountPaid);
        contentValues.put("interestPaid",interestPaid);
        contentValues.put("comment",comment);
        contentValues.put("upTOdate","true");
        long result = db.insert("Dues",null ,contentValues);
        return result;
    }
    public Integer deleteDue (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Dues", "loan = ?",new String[] {id});

    }

    public long insertCity(String city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", city);
        long result = db.insertWithOnConflict("City",null ,contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
