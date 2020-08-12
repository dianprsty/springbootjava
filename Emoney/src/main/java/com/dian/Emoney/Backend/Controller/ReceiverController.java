package com.dian.Emoney.Backend.Controller;

import com.dian.Emoney.Backend.Model.*;
import com.dian.Emoney.Backend.Service.UserService;
import com.dian.Emoney.BankServer;
import com.dian.Emoney.RestAPI.Controller.EmoneyController;
import com.dian.Emoney.broker.EmoneyConsumer;
import com.dian.Emoney.broker.EmoneyProducer;
import com.dian.Emoney.broker.back.BackConsumer;
import com.dian.Emoney.broker.back.BackProducer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReceiverController {
    @Autowired
    BackConsumer recieve1;
    @Autowired
    UserService userService;
    @Autowired
    EmoneyController emoneyController;
    @Autowired
    BackProducer send2;
    @Autowired
    EmoneyConsumer recieve3;
    @Autowired
    EmoneyProducer send4;

    BankServer bankServer = new BankServer();

    String response;


    NominalTemp temp = new NominalTemp();

    //menyimpan nominal topup sebelumnya
    public void saveNominalTopUp(int nom) {
        temp.setNominal(nom);
    }


    public void register(){
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        User user = new Gson().fromJson(response,User.class);
        userService.register(user);
        String message = "{\"message\":\"Registrasi User"+ user.getUsername()+" Berhasil \"}";
        send2.sendToFront(message);
        System.out.println(message);
    }


    public void login(){
        System.out.println("Start Login");
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        User user = new Gson().fromJson(response,User.class);
        List<User> userData = userService.login(user);
        if (userData.size() > 0) {
            for (User log : userData) {
                log.setLogstatus(1);
                userService.updateStatus(log);
            }
            emoneyController.setLogsession(1);
            emoneyController.printLogSesion();
            String message = "{\"message\":\"Login User = "+ user.getUsername()+" Berhasil \"}";
            send2.sendToFront(message);
        } else {
            String message = "{\"message\":\"Login User = "+ user.getUsername()+" GAGAL !!! \"}";
            send2.sendToFront(message);
            System.out.println(message);
        }
    }


    public void logout() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        User user = new Gson().fromJson(response,User.class);
        List<User> userData = userService.logout(user);
        if (userData.size() > 0) {
            for (User log : userData) {
                log.setLogstatus(0);
                userService.updateStatus(log);
            }
            emoneyController.setLogsession(0);
            emoneyController.printLogSesion();
            String message = "{\"message\":\"Logout User = "+ user.getUsername()+" Berhasil \"}";
            send2.sendToFront(message);
            System.out.println(message);
        } else {
            String message = "{\"message\":\"Perintah Logout tidak bisa dijalankan \"}";
            send2.sendToFront(message);
        }
    }

    public void topup(){
        System.out.println("Start");
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Topup topup = new Gson().fromJson(response,Topup.class);
        int nominal = topup.getNominal();
        saveNominalTopUp(nominal);

        String username = topup.getUsername();
        long id_user = topup.getId_user();

        User user = new User();
        user.setId_user(id_user);
        user.setUsername(username);


        List<User> userData = userService.topup(user);
        if (userData.size() > 0) {
            String message1 = "{"+
                    "\"message\":\"Silakan Melakukan Pembayaran\"" +","+
                    "\"nominal\":\""+(nominal + 1000)+"\""+","+             //1000 adalah biaya admin
                    "}";
            send2.sendToFront(message1);
        } else {
            String message = "{\"message\":\"Top Up Gagal\"}";
            send2.sendToFront(message);
        }

        System.out.println("[v]Nominal Temp Berhasil Menyimpan nominal topup = "+ temp.getNominal()); //

    }

    public void confirm() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Topup confirm = new Gson().fromJson(response,Topup.class);

        String username = confirm.getUsername();
        long id_user = confirm.getId_user();

        User user = new User();
        user.setId_user(id_user);
        user.setUsername(username);

        int nominal = temp.getNominal();

        String stat = confirm.getStatus().toLowerCase();
        String confirmBank = bankServer.getStatus().toLowerCase();

        if(stat.equals(confirmBank)) {
            List<User> userData = userService.confirm(user);
            if (userData.size() > 0) {
                for (User conf : userData) {
                    String us = conf.getUsername();
                    int bal = conf.getBalance();
                    long id = conf.getId_user();
                    int nom = (bal + nominal);

                    User obj = new User();
                    obj.setBalance(nom);
                    obj.setUsername(us);
                    obj.setId_user(id);
                    userService.updateBalance(obj);
                }

                String message = "{\"message\":\"Topup sebesar " + nominal + " Berhasil\"}";
                send2.sendToFront(message);
            } else {
                String message = "{\"message\":\"Top Up Gagal, User tidak ditemukan\"}";
                send2.sendToFront(message);
            }
        }else{
            String message = "{\"message\":\"Pembayaran tidak dapat dikonfirmasi Transaksi gagal\"}";
            send2.sendToFront(message);
        }
    }

    public void updateBalancePayment(){
        recieve3.recieveFromMerchant();
        response=recieve3.getPesan();
        TransaksiRepo transaksiRepo = new Gson().fromJson(response,TransaksiRepo.class);
        long id_user = transaksiRepo.getId_user();
        int tagihan = transaksiRepo.getTagihan();

        User user = userService.findById(id_user);
        int newBalance = user.getBalance() - tagihan;
        user.setBalance(newBalance);

        userService.updateBalance(user);

        String message = "ok";
        send4.sendToMerchant(message);

    }

    public void topupMerchant(){
        recieve3.recieveFromMerchant();
        response=recieve3.getPesan();
        TopupEmoneyByMerchant topmer = new Gson().fromJson(response,TopupEmoneyByMerchant.class);
        long id_user = topmer.getId_user();
        int ammount = topmer.getBalance();

        User user = userService.findById(id_user);
        int newBalance = user.getBalance() + ammount;
        user.setBalance(newBalance);

        userService.updateBalance(user);

        String message = "berhasil";
        send4.sendToMerchant(message);

    }
}


