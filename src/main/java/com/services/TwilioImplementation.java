package com.services;

import com.model.SmsMessage;
import com.model.SmsMessageResult;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioImplementation
{

    public static final String ACCOUNT_SID = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    public static final String AUTH_TOKEN = "your_auth_token";

    public SmsMessageResult sendSms(SmsMessage smsMessage)
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(smsMessage.getFrom()), new PhoneNumber(smsMessage.getTo()), smsMessage.getMessage())
                .create();

        SmsMessageResult smsMessageResult = new SmsMessageResult(message
                .getStatus()
                .toString(), message.getDateSent(), message.getErrorMessage(), message.getErrorCode());

        return smsMessageResult;
    }

}
