package com.model;

import org.joda.time.DateTime;

public class SmsMessageResult
{

    private String status;

    private DateTime dateSent;

    private String errorMessage;

    private Integer errorCode;

    public SmsMessageResult(String status, DateTime dateSent, String errorMessage, Integer errorCode)
    {
        this.status = status;
        this.dateSent = dateSent;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
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
        return "SmsMessageResult{" + "status='" + status + '\'' + ", dateSent=" + dateSent + ", errorMessage='" + errorMessage + '\'' + ", errorCode=" + errorCode + '}';
    }
}
