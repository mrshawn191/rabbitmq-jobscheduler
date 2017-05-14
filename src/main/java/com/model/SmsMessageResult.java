package com.model;

import org.joda.time.DateTime;

public class SmsMessageResult
{

    private int id;

    private String message;

    private String from;

    private String to;

    private String status;

    private DateTime dateSent;

    private String errorMessage;

    private Integer errorCode;

    public SmsMessageResult(int id, String message, String from, String to, String status, DateTime dateSent, String errorMessage, Integer errorCode)
    {
        this.id = id;
        this.message = message;
        this.from = from;
        this.to = to;
        this.status = status;
        this.dateSent = dateSent;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
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
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public DateTime getDateSent()
    {
        return dateSent;
    }
    public void setDateSent(DateTime dateSent)
    {
        this.dateSent = dateSent;
    }
    public String getErrorMessage()
    {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    public Integer getErrorCode()
    {
        return errorCode;
    }
    public void setErrorCode(Integer errorCode)
    {
        this.errorCode = errorCode;
    }

    @Override
    public String toString()
    {
        return "SmsMessageResult{" + "id=" + id + ", message='" + message + '\'' + ", from='" + from + '\'' + ", to='" + to + '\'' + ", status='" + status + '\'' + ", dateSent=" + dateSent + ", errorMessage='" + errorMessage + '\'' + ", errorCode=" + errorCode + '}';
    }
}
