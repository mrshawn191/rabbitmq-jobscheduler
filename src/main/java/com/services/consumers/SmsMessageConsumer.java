package com.services.consumers;

import com.google.firebase.database.*;
import com.model.SmsMessage;
import com.rabbitmq.client.*;
import com.services.queues.QueueNameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.Map;

import java.util.Date;

public class SmsMessageConsumer
{

    final static Logger logger = LoggerFactory.getLogger(SmsMessageConsumer.class);

    public static void main(String[] args) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(System.getenv("CLOUDAMQP_URL"));
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> params = new HashMap<>();
        params.put("x-ha-policy", "all");
        channel.queueDeclare(QueueNameConstants.SMSMESSAGE_QUEUE_NAME, true, false, false, params);

        Consumer consumer = new DefaultConsumer(channel)
        {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, "UTF-8");
                logger.info("Received " + message);
            }
        };

        channel.basicConsume(QueueNameConstants.SMSMESSAGE_QUEUE_NAME, true, consumer);
    }

}
