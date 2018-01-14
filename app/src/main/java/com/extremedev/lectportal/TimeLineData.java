package com.extremedev.lectportal;

/**
 * Created by jjsikini on 12/3/17.
 */

public class TimeLineData {




    public TimeLineData(String date, String event_name, String brief_description, String student_id, String sender, String lec_id) {
        this.date = date;
        this.event_name = event_name;
        this.brief_description = brief_description;
        this.student_id = student_id;
        this.sender = sender;
        this.lec_id = lec_id;
    }
    public String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getBrief_description() {
        return brief_description;
    }

    public void setBrief_description(String brief_description) {
        this.brief_description = brief_description;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getLec_id() {
        return lec_id;
    }

    public void setLec_id(String lec_id) {
        this.lec_id = lec_id;
    }

    public String event_name;
    public String brief_description;
    public String student_id;
    public String sender;
    public String lec_id;

}
