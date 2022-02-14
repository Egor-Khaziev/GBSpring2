package com.khaziev.rabbitmq.recipient;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

public class SingleRecipientApp {
    private static String QUEUE_NAME = "php";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        System.out.println("Please change you theme.");
        System.out.println("You need input: set_topic #topic name#");

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String tempMessage = bf.readLine().toLowerCase(Locale.ROOT).substring(10);
        if(tempMessage.startsWith("set_topic")){
            QUEUE_NAME = tempMessage.toLowerCase(Locale.ROOT).substring(10);
        }



        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println(" [*] Waiting...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Found message: '" + message + "'");
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });

    }

}
