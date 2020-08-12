package com.dian.Merchant.Backend.Model;

public class PaymentConfirm {
    String metode;
    long status;

    public String getMetode() {
        return metode;
    }

    public void setMetode(String metode) {
        this.metode = metode;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }
}
