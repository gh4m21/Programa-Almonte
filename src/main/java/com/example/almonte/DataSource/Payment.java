package com.example.almonte.DataSource;

public class Payment {
    String _id;
    String loan;
    String dateToPay;
    String dateAmountPaid;
    String dateInterestPaid;
    String quota;
    String status;
    String amountPaid;
    String comment;


    public Payment(String _id, String loan, String dateToPay, String dateAmountPaid, String dateInterestPaid, String quota, String status, String amountPaid, String comment) {
        this._id = _id;
        this.loan = loan;
        this.dateToPay = dateToPay;
        this.dateAmountPaid = dateAmountPaid;
        this.dateInterestPaid = dateInterestPaid;
        this.quota = quota;
        this.status = status;
        this.amountPaid = amountPaid;
        this.comment = comment;
    }

    public String get_id() {
        return _id;
    }

    public String getLoan() {
        return loan;
    }

    public String getDateToPay() {
        return dateToPay;
    }

    public String getDateAmountPaid() {
        return dateAmountPaid;
    }

    public String getDateInterestPaid() {
        return dateInterestPaid;
    }

    public String getQuota() {
        return quota;
    }

    public String getStatus() {
        return status;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public String getComment() {
        return comment;
    }
}
