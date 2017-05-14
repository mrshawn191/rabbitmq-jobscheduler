package com.services;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.model.SmsMessage;
import com.model.SmsMessageResult;
import org.slf4j.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * FirebaseDatabaseImplementation handles all the persistance logic
 */
public class FirebaseDatabaseImplementation
{

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FirebaseDatabaseImplementation.class);

    public void initialize() throws FileNotFoundException
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream(FirebaseDatabaseImplementation.class
                        .getClassLoader()
                        .getResource("firebase.json")
                        .getPath()))
                .setDatabaseUrl("https://travlitt-94448.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
    }

    /**
     * Retrieves a list of smsMessages that are scheduled to be sent.
     * It will also delete those records, since they will be saved under different json child anyway.
     */
    public List<SmsMessage> getScheduledSmsMessagesForToday() throws FileNotFoundException
    {
        initialize();

        final List<SmsMessage> scheduledSmsMessagesForToday = new ArrayList<>();

        final Query query = com.google.firebase.database.FirebaseDatabase
                .getInstance()
                .getReference()
                .child("scheduledSmsMessage");

        query.addChildEventListener(new ChildEventListener()
        {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                SmsMessage smsMessage = dataSnapshot.getValue(SmsMessage.class);
                Date meetingDate = smsMessage.getMeetingDate();
                Date currentDate = new Date();

                if (meetingDate.compareTo(currentDate) > 0)
                {
                    scheduledSmsMessagesForToday.add(smsMessage);
                    // Delete from scheduledat
                    Query todayMeetingQuery = FirebaseDatabase
                            .getInstance()
                            .getReference()
                            .child("scheduledSmsMessage")
                            .child(smsMessage
                                    .getId()
                                    .toString());
                    todayMeetingQuery.addListenerForSingleValueEvent(new ValueEventListener()
                    {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            dataSnapshot
                                    .getRef()
                                    .removeValue();

                            logger.info("Removed meeting " + smsMessage.toString());

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError)
                        {

                        }
                    });
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        return scheduledSmsMessagesForToday;
    }

    /**
     * Saves smsMessage that has been sent through the twilio api
     */
    public void saveSentSmsMessages(SmsMessageResult smsMessageResult) throws FileNotFoundException
    {
        initialize();

        /**
         * The Firebase Java client uses daemon threads, meaning it will not prevent a process from exiting.
         * So we'll wait(countDownLatch.await()) until firebase saves record. Then decrement `countDownLatch` value
         * using `countDownLatch.countDown()` and application will continues its execution.
         */
        CountDownLatch countDownLatch = new CountDownLatch(1);

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("sentSmsMessage")
                .push()
                .setValue(smsMessageResult, (de, dr) ->
                {
                    // decrement countDownLatch value and application will be continues its execution.
                    countDownLatch.countDown();
                });
        try
        {
            // wait for firebase to saves record.
            countDownLatch.await();
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }

    }
}
