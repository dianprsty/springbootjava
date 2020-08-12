package com.dian.Emoney.Backend.Model;

public class User {
    private String username, password, email;
    private long id_user;
    private int logstatus, balance;

    public User(String username, String password, String email, long id_user, int logstatus, int balance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id_user = id_user;
        this.logstatus = logstatus;
        this.balance = balance;
    }

    public User() {
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getLogstatus() {
        return logstatus;
    }

    public void setLogstatus(int logstatus) {
        this.logstatus = logstatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", id_user=" + id_user +
                '}';
    }
}
