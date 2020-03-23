package com.example.almonte.DataSource;

public class PreviewLoans {
    String _id;
    String idPreviewLoan;

    public PreviewLoans(String idLoan, String idPreviewLoan) {
        this._id = idLoan;
        this.idPreviewLoan = idPreviewLoan;
    }

    public String getIdLoan() {
        return _id;
    }

    public String getIdPreviewLoan() {
        return idPreviewLoan;
    }
}
