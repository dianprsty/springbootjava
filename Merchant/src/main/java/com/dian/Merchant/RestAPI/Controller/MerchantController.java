package com.dian.Merchant.RestAPI.Controller;

import com.dian.Merchant.Backend.Controller.ReceiverController;
import com.dian.Merchant.Backend.Model.MerchantUser;
import com.dian.Merchant.Backend.Model.PaymentConfirm;
import com.dian.Merchant.Backend.Model.Product;
import com.dian.Merchant.Backend.Model.TopupEmoneyByMerchant;
import com.dian.Merchant.broker.front.FrontConsumer;
import com.dian.Merchant.broker.front.FrontProducer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    FrontProducer send1;
    @Autowired
    FrontConsumer recieve2;
    @Autowired
    ReceiverController server;
//---------------login status----------------
    int logsession = 0;

    public int getLogsession() {
        return logsession;
    }

    public void setLogsession(int logsession) {
        this.logsession = logsession;
    }

    public void printLogSesion(){
        if(getLogsession() == 1){
            System.out.println("[Sesion Login Info] Anda sedang login");
        }else{
            System.out.println("[Sesion Login Info] Tidak ada sesion login");
        }

    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody MerchantUser user) {
        if(logsession == 0) {
            String message = new Gson().toJson(user);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.register();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are any login session, please logout first\"}",HttpStatus.CONFLICT);
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody MerchantUser user) {
        if(logsession == 0) {
            String message = new Gson().toJson(user);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.login();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are any login session, please logout first\"}",HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logoutUser(@RequestBody MerchantUser user) {
        if(logsession == 1) {
            String message = new Gson().toJson(user);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.logout();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        if(logsession == 1) {
            String message = new Gson().toJson(product);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.createProduct();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/readallproduct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readAllProduct(@RequestBody Product product){
        if(logsession == 1) {
            String message = new Gson().toJson(product);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.findAllProduct();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/readproductbyname", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readProductByName(@RequestBody Product product){
        String message = new Gson().toJson(product);
        String response = "";

        Thread thread = new Thread(){
            public void run() {
                server.findProductByName();
            }
        };
        thread.start();

        send1.sendToBack(message);
        recieve2.recieveFromBack();
        response = recieve2.getPesan();

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/readproductbyid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> readProductById(@RequestBody Product product){
        if(logsession == 1) {
            String message = new Gson().toJson(product);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.findProductById();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }



    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        if(logsession == 1) {
            String message = new Gson().toJson(product);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.updateProduct();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteProduct(@RequestBody Product product){
        if(logsession == 1) {
            String message = new Gson().toJson(product);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.deleteProduct();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.GONE);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Transaction(@RequestBody Product product) {
        if(logsession == 1) {
            String message = new Gson().toJson(product);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.transaction();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.PAYMENT_REQUIRED);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/transaction/payment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Payment(@RequestBody PaymentConfirm paymentConfirm) {
        if(logsession == 1) {
            String message = new Gson().toJson(paymentConfirm);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.payment();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/topupbymerchant", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> Topup(@RequestBody TopupEmoneyByMerchant topup) {
        if (logsession == 1){
            String message = new Gson().toJson(topup);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.topupEmoney();
                }
            };
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}
