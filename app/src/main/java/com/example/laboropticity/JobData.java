package com.example.laboropticity;

public class JobData {

    String title;
    String desc;
    String skill;
    String amount;
    String city;
//    String id;
    String status;

    public JobData(){

    }

    public JobData(String title, String desc, String skill, String amount, String city, String status) {
        this.title = title;
        this.desc = desc;
        this.skill = skill;
        this.amount = amount;
        this.city = city;
//        this.id = id;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCity() {
        return city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCity(String city) {
        this.city = city;
    }

//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }
}
