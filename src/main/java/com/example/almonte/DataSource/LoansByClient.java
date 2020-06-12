package com.example.almonte.DataSource;

public class LoansByClient {

    String _id;
    String client;
    String amount;
    String plan;
    String status;
    String amountPerQuota;
    String interestPerQuota;
    String date;

    public LoansByClient(String _id, String client, String amount, String plan, String status, String amountPerQuota, String interestPerQuota, String date) {
        this._id = _id;
        this.client = client;
        this.amount = amount;
        this.plan = plan;
        this.status = status;
        this.amountPerQuota = amountPerQuota;
        this.interestPerQuota = interestPerQuota;
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

    public String getPlan() {
        return plan;
    }

    public String getStatus() {
        return status;
    }

    public String getAmountPerQuota() {
        return amountPerQuota;
    }

    public String getInterestPerQuota() {
        return interestPerQuota;
    }

    public String getDate() {
        return date;
    }
}
