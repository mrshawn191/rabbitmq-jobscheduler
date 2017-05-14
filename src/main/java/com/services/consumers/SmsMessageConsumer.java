package com.services.consumers;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SmsMessageConsumer
{
    final static Logger logger = LoggerFactory.getLogger(SmsMessageConsumer.class);

    final private String QUEUE_NAME = "work-queue-1";

    public static void main(String[] args) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(System.getenv("CLOUDAMQP_URL"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String queueName = "work-queue-1";
        Map<String, Object> params = new HashMap<>();
        params.put("x-ha-policy", "all");
        channel.queueDeclare(queueName, true, false, false, params);

        Consumer consumer = new DefaultConsumer(channel)
        {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, "UTF-8");
                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };

    }
}
