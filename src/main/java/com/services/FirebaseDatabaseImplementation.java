package com.services;

import com.google.firebase.database.*;
import com.model.SmsMessage;
import org.slf4j.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FirebaseDatabaseImplementation handles all the persistance logic
 */
public class FirebaseDatabaseImplementation
{

    final static org.slf4j.Logger logger = LoggerFactory.getLogger(FirebaseDatabaseImplementation.class);

    public List<SmsMessage> getScheduledSmsMessagesForToday()
    {
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

    public void saveSentSmsMessages()
    {

    }
}
