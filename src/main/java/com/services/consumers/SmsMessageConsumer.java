package com.services.consumers;

import com.model.SmsMessage;
import com.model.SmsMessageResult;
import com.rabbitmq.client.*;
import com.services.FirebaseDatabaseImplementation;
import com.services.TwilioImplementation;
import com.services.queues.QueueNameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * SmsMessageConsumer receives scheduled smsMessages from the queue and send them
 * through the Twilio API. It will also save all sent sms to firebase.
 */
public class SmsMessageConsumer
{

    final static Logger logger = LoggerFactory.getLogger(SmsMessageConsumer.class);

    final static private TwilioImplementation twilioImplementation = new TwilioImplementation();

    private static FirebaseDatabaseImplementation firebaseDatabaseImplementation = new FirebaseDatabaseImplementation();

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
                SmsMessage smsMessage = deserialize(body);

                SmsMessageResult smsMessageResult = twilioImplementation.sendSms(smsMessage);
                firebaseDatabaseImplementation.saveSentSmsMessages(smsMessageResult);
                logger.info("Saved smsMessageResult " + smsMessageResult.toString());
            }
        };

        channel.basicConsume(QueueNameConstants.SMSMESSAGE_QUEUE_NAME, true, consumer);
    }

    private static SmsMessage deserialize(byte[] payload)
    {
        SmsMessage smsMessage = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(payload); ObjectInput in = new ObjectInputStream(bis))
        {
            smsMessage = (SmsMessage) in.readObject();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }

        return smsMessage;
    }

}
