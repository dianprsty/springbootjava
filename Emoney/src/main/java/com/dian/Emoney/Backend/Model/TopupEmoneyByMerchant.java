package com.dian.Emoney.Backend.Model;

public class TopupEmoneyByMerchant {
    long id_user;
    int balance;

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
