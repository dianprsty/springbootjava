package com.dian.Merchant.Backend.Model;

public class MerchantUser {
    private long id_user;
    private String username, password, role;
    private int logstatus;


    public MerchantUser(long id_user, String username, String password, String role, int logstatus) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.role = role;
        this.logstatus = logstatus;
    }

    public MerchantUser() {
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getLogstatus() {
        return logstatus;
    }

    public void setLogstatus(int logstatus) {
        this.logstatus = logstatus;
    }

    @Override
    public String toString() {
        return "MerchantUser{" +
                "id_user=" + id_user +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", logstatus=" + logstatus +
                '}';
    }
}
