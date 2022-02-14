package com.khaziev.rabbitmq.sender;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class PHPSenderApp {
    private final static String QUEUE_NAME = "php";
    private final static String EXCHANGER_NAME = "php_exchanger";

    public static void main(String[] args) throws Exception {


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGER_NAME, "php");



            String message = "Welcome to PHP channel!";

            while(true) {
                channel.basicPublish(EXCHANGER_NAME, "php", null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
                BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
                message = bf.readLine();
            }
        }
    }
}
























