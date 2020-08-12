package com.dian.Merchant.Backend.Model;

public class Product {
    long pid;
    int price,stock;
    String name, role;       //variabel role di declare suspaya saat input di postman bisa menyertakan role

    public Product(long pid, int price, int stock, String name) {
        this.pid = pid;
        this.price = price;
        this.stock = stock;
        this.name = name;
    }

    public Product() {
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
