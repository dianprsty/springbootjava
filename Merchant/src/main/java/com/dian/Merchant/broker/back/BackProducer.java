package com.dian.Merchant.broker.back;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class BackProducer {
    final String EXCHANGE_NAME = "merchant_recieve";
   public void sendToFront(String message){
       ConnectionFactory factory = new ConnectionFactory();
       factory.setHost("localhost");
       try{Thread.sleep(500);}catch (Exception e){e.printStackTrace();}
       try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
           channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
           channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
           System.out.println(" [Back Producer ] Sent '" + message + "'");
       }catch (Exception e){e.printStackTrace();}
   }
}
