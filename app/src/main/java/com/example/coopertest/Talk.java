package com.example.coopertest;

public class Talk {
    private String title;
    private String time;
    private String name;
    public Talk(String title, String time, String name){
        this.title=title;
        this.time=time;
        this.name=name;
    }
    public String getTitle(){ return title; }
    public String getTime(){
        return time;
    }
    public String getName(){ return name; }

}
