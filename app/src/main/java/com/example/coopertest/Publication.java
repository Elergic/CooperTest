package com.example.coopertest;

import android.support.annotation.StringDef;

import org.litepal.crud.DataSupport;

public class Publication extends DataSupport {
    private String time;
    private String name;
    private String title;
    private String detail;
    private String maintype;
    private int money;
    private String thing;
    private String range;
    private String deadline;
    private int number;
    private String actype;
    private String organization;

    public Publication() {
        this.time = time;
        this.name = name;
        this.title = title;
        this.detail = detail;
        this.maintype = maintype;
        this.money = money;
        this.thing = thing;
        this.range = range;
        this.deadline = deadline;
        this.number = number;
        this.actype = actype;
        this.organization = organization;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMaintype() {
        return maintype;
    }

    public void setMaintype(String maintype) {
        this.maintype = maintype;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getActype() {
        return actype;
    }

    public void setActype(String actype) {
        this.actype = actype;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
