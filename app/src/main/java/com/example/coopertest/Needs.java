package com.example.coopertest;

public class Needs {
    private String title;
    private String time;
    private String place;
    private String or_name;
    private String res_name;
    public Needs(String title,String time,String place,String or_name,String res_name){
        this.title=title;
        this.time=time;
        this.place=place;
        this.or_name=or_name;
        this.res_name=res_name;
    }
    public String getTitle(){
        return title;
    }
    public String getTime(){
        return time;
    }
    public String getPlace(){
        return place;
    }
    public String getOr_name(){
        return or_name;
    }
    public String getRes_name(){ return res_name; }
}