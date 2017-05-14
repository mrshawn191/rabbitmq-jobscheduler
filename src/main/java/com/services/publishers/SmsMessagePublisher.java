package com.services.publishers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.services.queues.QueueNameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SmsMessagePublisher
{

    final static Logger logger = LoggerFactory.getLogger(SmsMessagePublisher.class);

    final static ConnectionFactory factory = new ConnectionFactory();

    public void publish()
    {
        try
        {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            Map<String, Object> params = new HashMap<>();
            params.put("x-ha-policy", "all");
            channel.queueDeclare(QueueNameConstants.SMSMESSAGE_QUEUE_NAME, true, false, false, params);

            String msg = "Sent at:" + System.currentTimeMillis();
            byte[] payload = msg.getBytes("UTF-8");
            channel.basicPublish("", QueueNameConstants.SMSMESSAGE_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, payload);
            logger.info("Message sent: " + msg);

            // close everything
            channel.close();
            connection.close();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }

}
