package com.services.publishers;

import com.model.SmsMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.services.queues.QueueNameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.services.FirebaseDatabaseImplementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * SmsMessagePublisher sends scheduled smsMessages to be sent today to the queue
 */
public class SmsMessagePublisher
{

    final static Logger logger = LoggerFactory.getLogger(SmsMessagePublisher.class);

    final static ConnectionFactory factory = new ConnectionFactory();

    private FirebaseDatabaseImplementation firebaseDatabaseImplementation = new FirebaseDatabaseImplementation();

    public void publish()
    {
        try
        {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            Map<String, Object> params = new HashMap<>();
            params.put("x-ha-policy", "all");
            channel.queueDeclare(QueueNameConstants.SMSMESSAGE_QUEUE_NAME, true, false, false, params);

            List<SmsMessage> scheduledSmsMessagesForToday = firebaseDatabaseImplementation.getScheduledSmsMessagesForToday();
            scheduledSmsMessagesForToday.forEach(x -> sendPayload(channel, x));
            logger.info("Sent total " + scheduledSmsMessagesForToday.size() + " to queue");

            // close everything
            channel.close();
            connection.close();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    private void sendPayload(Channel channel, SmsMessage smsMessage)
    {
        byte[] payload = serialize(smsMessage);
        try
        {
            channel.basicPublish("", QueueNameConstants.SMSMESSAGE_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, payload);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage(), e);
        }
    }

    private byte[] serialize(SmsMessage smsMessage)
    {
        byte[] serializedSmsMessage = new byte[0];

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos))
        {
            out.writeObject(smsMessage);
            serializedSmsMessage = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return serializedSmsMessage;
    }

}