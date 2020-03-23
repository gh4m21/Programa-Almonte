package com.example.almonte.DataSource;

public class Loans {
    String _id;
    String client;
    String amount;
    String  amountPerQuota;
    String interestPerQuota;
    String plan;
    String status;
    String quota;
    String nextpaymentDate;
    String date;

    public Loans(String _id, String client, String amount, String amountPerQuota, String interestPerQuota, String plan,String status, String quota, String nextpaymentDate, String date) {
        this._id = _id;
        this.client = client;
        this.amount = amount;
        this.amountPerQuota = amountPerQuota;
        this.interestPerQuota = interestPerQuota;
        this.plan = plan;
        this.status = status;
        this.quota = quota;
        this.nextpaymentDate = nextpaymentDate;
        this.date = date;
    }

    public String get_id() {
        return _id;
    }

    public String getClient() {
        return client;
    }

    public String getAmount() {
        return amount;
    }

    public String getAmountPerQuota() {
        return amountPerQuota;
    }

    public String getInterestPerQuota() {
        return interestPerQuota;
    }

    public String getPlan() {
        return plan;
    }


    public String getStatus() {
        return status;
    }

    public String getQuota() {
        return quota;
    }

    public String getNextpaymentDate() {
        return nextpaymentDate;
    }

    public String getDate() {
        return date;
    }
}
