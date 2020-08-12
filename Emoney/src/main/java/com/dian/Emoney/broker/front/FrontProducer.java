package com.dian.Emoney.broker.front;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class FrontProducer {
    String EXCHANGE_NAME = "emoney_send";
   public void sendToBack(String message){
       ConnectionFactory factory = new ConnectionFactory();
       factory.setHost("localhost");
       try{Thread.sleep(1000);}catch (Exception e){e.printStackTrace();}
       try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
           channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
           channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
           System.out.println(" [FrontProducer ] Sent '" + message + "'");
       }catch (Exception e){e.printStackTrace();}
   }
}
