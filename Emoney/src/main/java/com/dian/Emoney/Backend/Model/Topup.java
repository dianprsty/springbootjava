package com.dian.Emoney.Backend.Model;

public class Topup {
    private long id_transaksi, id_user;
    private int nominal;
    private String status, username;

    public Topup(long id_transaksi, long id_user, String username, int nominal, String status) {
        this.id_transaksi = id_transaksi;
        this.id_user = id_user;
        this.username = username;
        this.nominal = nominal;
        this.status = status;
    }

    public Topup() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(long id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Topup{" +
                "id_transaksi=" + id_transaksi +
                ", id_user=" + id_user +
                ", nominal=" + nominal +
                ", status='" + status + '\'' +
                '}';
    }
}
