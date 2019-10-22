package com.example.coopertest;

public class Coop {

    private String time;
    private String organizationName;
    private String contact;
    private String place;
    private String title;

    public Coop(String time,String organizationName,String contact,String place,String title){
        this.time = time;
        this.organizationName = organizationName;
        this.contact = contact;
        this.place = place;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public String getTime(){
        return time;
    }

    public String getOrganizationName(){
        return organizationName;
    }

    public String getContact(){
        return contact;
    }

    public String getPlace(){
        return place;
    }

}
