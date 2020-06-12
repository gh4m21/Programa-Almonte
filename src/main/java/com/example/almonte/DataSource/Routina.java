package com.example.almonte.DataSource;

public class Routina {
    String firstname;
    String lastname;
    String identification;
    String id;
    String amount;
    String phone;
    String adress;
    String status;
    String date;

    public Routina(String firstname, String lastname,
                  String identification, String id, String amount,String phone,String adress,
                String status, String date) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.identification = identification;
        this.id = id;
        this.amount = amount;
        this.phone = phone;
        this.adress = adress;
        this.status = status;
        this.date = date;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getIdentification() {
        return identification;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public String getPhone(){
        return phone;
    }

    public String getAdress() {
        return adress;
    }


    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
