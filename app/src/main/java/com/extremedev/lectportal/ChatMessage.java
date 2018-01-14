package com.extremedev.lectportal;

/**
 * Created by sikinijs on 8/5/17.
 */
public class ChatMessage {

/*

               "sender":"c89-678\/3087",
               "receiver":"c89-678\/3087",
               "receiver_type":"",
               "time":"2017-11-21 06:42:52",
               "msg":"Good morning Mr Kaburu, Can we meet today?"
 */

    public ChatMessage(String sender, String receiver, String time, String receiver_type, String msg) {
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.receiver_type = receiver_type;
        this.msg = msg;
    }

public String time;
public String receiver_type;
public String msg;
public String sender;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReceiver_type() {
        return receiver_type;
    }

    public void setReceiver_type(String receiver_type) {
        this.receiver_type = receiver_type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String receiver;



}