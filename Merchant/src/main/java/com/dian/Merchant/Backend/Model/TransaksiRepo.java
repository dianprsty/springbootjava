package com.dian.Merchant.Backend.Model;

public class TransaksiRepo {
   long id_user;
    int tagihan = 0;

    public int getTagihan() {
        return tagihan;
    }

    public void setTagihan(int tagihan) {
        this.tagihan = tagihan;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }
}
