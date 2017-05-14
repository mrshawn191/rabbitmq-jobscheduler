package com.model;

import java.io.Serializable;
import java.util.Date;

public class SmsMessage implements Serializable
{

    private Integer id;

    private String from;

    private String to;

    private String message;

    private String placeId;

    private Integer numberOfPeople;

    private Date meetingDate;

    private Date createdAt;

    private Date sentAt;

    public Integer getId()
    {
        return id;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }
    public String getFrom()
    {
        return from;
    }
    public void setFrom(String from)
    {
        this.from = from;
    }
    public String getTo()
    {
        return to;
    }
    public void setTo(String to)
    {
        this.to = to;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
    public String getPlaceId()
    {
        return placeId;
    }
    public void setPlaceId(String placeId)
    {
        this.placeId = placeId;
    }
    public Integer getNumberOfPeople()
    {
        return numberOfPeople;
    }
    public void setNumberOfPeople(Integer numberOfPeople)
    {
        this.numberOfPeople = numberOfPeople;
    }
    public Date getMeetingDate()
    {
        return meetingDate;
    }
    public void setMeetingDate(Date meetingDate)
    {
        this.meetingDate = meetingDate;
    }
    public Date getCreatedAt()
    {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }
    public Date getSentAt()
    {
        return sentAt;
    }
    public void setSentAt(Date sentAt)
    {
        this.sentAt = sentAt;
    }



    @Override
    public String toString()
    {
        return "SmsMessage{" + "id=" + id + ", from='" + from + '\'' + ", to='" + to + '\'' + ", message='" + message + '\'' + ", placeId='" + placeId + '\'' + ", numberOfPeople=" + numberOfPeople + ", meetingDate=" + meetingDate + ", createdAt=" + createdAt + ", sentAt=" + sentAt + '}';
    }
}
