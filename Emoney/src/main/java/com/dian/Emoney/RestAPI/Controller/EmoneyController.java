package com.dian.Emoney.RestAPI.Controller;

import com.dian.Emoney.Backend.Controller.ReceiverController;
import com.dian.Emoney.Backend.Model.Topup;
import com.dian.Emoney.Backend.Model.User;
import com.dian.Emoney.broker.front.FrontConsumer;
import com.dian.Emoney.broker.front.FrontProducer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/emoney")
public class EmoneyController {




    @Autowired
    FrontProducer send1;
    @Autowired
    FrontConsumer recieve2;
    @Autowired
    ReceiverController server;

    //---------------Login Sesion---------------
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



    //------------------Register new User--------------
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody User user) {
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
        }else {
            return new ResponseEntity<>("{\"message\":\"there are any login session, please logout first\"}",HttpStatus.CONFLICT);
        }

    }

    //---------------Login User-------------
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        if(logsession == 0) {
            String message = new Gson().toJson(user);
            String response = "";

            Thread thread = new Thread() {
                public void run() {
                    server.login();
                }
            };
            thread.start();

            Thread standBy = new Thread() {
                public void run() {
                    server.updateBalancePayment();
                }
            };
            standBy.start();
            Thread standBy2 = new Thread() {
                public void run() {
                    server.topupMerchant();
                }
            };                          // thread yang standby menunggu request dari merchant,
            standBy2.start();            // ditaruh di login karena setiap menjalankan aplikasi pasti pilih login dulu

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are any login session, please logout first\"}",HttpStatus.CONFLICT);
        }
    }

    //-------------------Logout-----------------
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logoutUser(@RequestBody User user) {
        if(logsession == 1) {
            String message = new Gson().toJson(user);
            String response = "";

            Thread thread = new Thread(() -> server.logout());
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }


    @RequestMapping(value = "/topup", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> topup(@RequestBody Topup topup) {
        if(logsession == 1) {
            String message = new Gson().toJson(topup);
            String response = "";

            Thread thread = new Thread(() -> server.topup());
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> confirm(@RequestBody Topup topup) {
        if(logsession == 1) {
            String message = new Gson().toJson(topup);
            String response = "";

            Thread thread = new Thread(() -> server.confirm());
            thread.start();

            send1.sendToBack(message);
            recieve2.recieveFromBack();
            response = recieve2.getPesan();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("{\"message\":\"there are no login session, please login first\"}",HttpStatus.METHOD_NOT_ALLOWED);
        }
    }














   /* @RequestMapping(value = "/user/update", method = RequestMethod.PUT)
    public String updateUser(@RequestBody String user) {

        return loginSession.update(user);
    }
    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {

        return loginSession.delete(id);
    }
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String loginUser(@RequestBody String user) {

        return loginSession.login(user);
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser() {
        return loginSession.findAll();
    }

*/
}
