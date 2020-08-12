package com.dian.Merchant.Backend.Controller;

import com.dian.Merchant.Backend.Model.MerchantUser;
import com.dian.Merchant.Backend.Model.PaymentConfirm;
import com.dian.Merchant.Backend.Model.Product;
import com.dian.Merchant.Backend.Service.ProductService;
import com.dian.Merchant.Backend.Model.TransaksiRepo;
import com.dian.Merchant.Backend.Service.UserService;
import com.dian.Merchant.RestAPI.Controller.MerchantController;
import com.dian.Merchant.broker.connectEmoney.MerchantConsumer;
import com.dian.Merchant.broker.connectEmoney.MerchantProducer;
import com.dian.Merchant.broker.back.BackConsumer;
import com.dian.Merchant.broker.back.BackProducer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReceiverController {
    @Autowired
    BackConsumer recieve1;
    @Autowired
    BackProducer send2;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    MerchantProducer send3;
    @Autowired
    MerchantConsumer recieve4;
    @Autowired
    MerchantController merchantController;


    String response;



    TransaksiRepo transaksiRepo = new TransaksiRepo();
    Product prodTemp = new Product();



    //-------------------------------------START POINY OF METHOD---------------------------------------------

    public void register(){
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        MerchantUser user = new Gson().fromJson(response, MerchantUser.class);
        userService.register(user);
        String message = "{\"message\":\"Registrasi User"+ user.getUsername()+" Berhasil \"}";
        send2.sendToFront(message);
        System.out.println(message);
    }


    public void login(){
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        MerchantUser user = new Gson().fromJson(response, MerchantUser.class);
        List<MerchantUser> userData = userService.login(user);
        if (userData.size() > 0) {
            for (MerchantUser log : userData) {
                log.setLogstatus(1);
                userService.updateStatus(log);
            }
            merchantController.setLogsession(1);
            merchantController.printLogSesion();
            String message = "{\"message\":\"Login User = "+ user.getUsername()+" Berhasil \"}";
            send2.sendToFront(message);
            System.out.println(message);
        } else {
            String message = "{\"message\":\"Login User = "+ user.getUsername()+" GAGAL !!! \"}";
            send2.sendToFront(message);
            System.out.println(message);
        }
    }


    public void logout() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        MerchantUser user = new Gson().fromJson(response, MerchantUser.class);
        List<MerchantUser> userData = userService.logout(user);
        if (userData.size() > 0) {
            for (MerchantUser log : userData) {
                log.setLogstatus(0);
                userService.updateStatus(log);
            }
            merchantController.setLogsession(0);
            merchantController.printLogSesion();
            String message = "{\"message\":\"Logout User"+ user.getUsername()+" Berhasil \"}";
            send2.sendToFront(message);
            System.out.println(message);
        } else {
            String message = "{\"message\":\"Perintah Logout tidak bisa dijalankan \"}";
            send2.sendToFront(message);
            System.out.println(message);
        }
    }

    public void createProduct(){
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Product product = new Gson().fromJson(response,Product.class);

        productService.create(product);

        List<Product> produkData = productService.findByName(product);
        if (produkData.size() > 0) {
            for (Product pro : produkData) {
                String message = new Gson().toJson(pro);
                send2.sendToFront(message);
            }
        } else {
            String message = "{\"message\":\"Gagal membuat Produk = "+product.getName()+"\"}";
            send2.sendToFront(message);
            System.out.println(message);
        }

    }








    public void updateProduct(){
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Product product = new Gson().fromJson(response,Product.class);

        productService.update(product);

        List<Product> produkData = productService.findByName(product);
        if (produkData.size() > 0) {
            for (Product pro : produkData) {
                String message = new Gson().toJson(pro);
                send2.sendToFront(message);
            }
        } else {
            String message = "{\"message\":\"Gagal memperbarui Produk = "+product.getName()+"\"}";
            send2.sendToFront(message);
            System.out.println(message);
        }

    }

    public void deleteProduct() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Product product = new Gson().fromJson(response,Product.class);

        Product productData = productService.findById(product);
        String message = new Gson().toJson(productData);
        send2.sendToFront(message);

        productService.delete(product);

    }

    public void findAllProduct() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Product product = new Gson().fromJson(response,Product.class);

        List<Product> allData = productService.findAllProduct();
        GsonBuilder gsonBuilder = new GsonBuilder();

        Gson gson = gsonBuilder.create();

        String JSONObject = gson.toJson(allData);

        send2.sendToFront(JSONObject);

    }

    public void findProductByName() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Product product = new Gson().fromJson(response,Product.class);

        List<Product> dataByName = productService.findByName(product);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String JSONObject = gson.toJson(dataByName);


        send2.sendToFront(JSONObject);

    }

    public void findProductById() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Product product = new Gson().fromJson(response,Product.class);

        List<Product> dataByName = productService.findProductById(product);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        String JSONObject = gson.toJson(dataByName);


        send2.sendToFront(JSONObject);

    }


    public void transaction() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        Product product = new Gson().fromJson(response,Product.class);

        int a = product.getStock();
        int b = product.getPrice();
        String c = product.getName();
        long d = product.getPid();
        int pembayaran = a*b;

        prodTemp.setStock(a);
        prodTemp.setPrice(b);
        prodTemp.setName(c);
        prodTemp.setPid(d);

        transaksiRepo.setTagihan(pembayaran);
        System.out.println("[Status penyimpanan tagihan] jika Lebih dari 0 maka berhasil "+ transaksiRepo.getTagihan());

        String message = "{\"message\":\"Total Belanja anda adalah "+transaksiRepo.getTagihan()+
                "Silakan Pilih Metode Pembayaran\"}";
        send2.sendToFront(message);

    }

    public void payment() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();
        PaymentConfirm paymentConfirm = new Gson().fromJson(response,PaymentConfirm.class);
        String a = paymentConfirm.getMetode();
        long b = paymentConfirm.getStatus();

        if(a.equals("cash") && b == 0){
            transaksiRepo.setTagihan(0);
            String message = "{\"message\":\"Pembayaran terkonfirmasi, Transaksi Sukses\"}";
            send2.sendToFront(message);
            List<Product> prodData = productService.findByName(prodTemp);
            if (prodData.size() > 0) {
                for (Product pro : prodData) {
                    long w = pro.getPid();
                    int x = pro.getPrice();
                    int y = (pro.getStock() - prodTemp.getStock());
                    String z = pro.getName();

                    Product product = new Product(w,x,y,z);

                    productService.updateAfterTransaction(product);
                    System.out.println("[Update]Stock Has been updated Stock =" + y);
                }
            }
        }else if(a.equals("emoney")){
            transaksiRepo.setId_user(b);
            String msg = new Gson().toJson(transaksiRepo,TransaksiRepo.class);
            send3.sendToEmoney(msg);
            recieve4.recieveFromEmoney();
            response = recieve4.getPesan();
            if(response.equals("ok")){
                transaksiRepo.setTagihan(0);
                String message = "{\"message\":\"Pembayaran Emoney terkonfirmasi, Transaksi Sukses\"}";
                send2.sendToFront(message);
                List<Product> prodData = productService.findByName(prodTemp);
                if (prodData.size() > 0) {
                    for (Product pro : prodData) {
                        long w = pro.getPid();
                        int x = pro.getPrice();
                        int y = (pro.getStock() - prodTemp.getStock());
                        String z = pro.getName();

                        Product product = new Product(w,x,y,z);

                        productService.updateAfterTransaction(product);
                        System.out.println("[Update]Stock Has been updated Stock =" + y);
                    }
                }
            }
        }
    }

    public void topupEmoney() {
        recieve1.recieveFromFront();
        response = recieve1.getPesan();

        send3.sendToEmoney(response);

        recieve4.recieveFromEmoney();
        String response2 = recieve4.getPesan();
        if(response2.equals("berhasil")){
            String message = "{\"message\":\"Top Up E Money Berhasil\"}";
            send2.sendToFront(message);
        }

    }
}
