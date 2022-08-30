package com.project.project2.model;


public class Person {
    private String full_name;
    private String phone;
    private String idCard;

    public Person() {

    }

    public Person(String full_name, String phone, String idCard) {
        this.full_name = full_name;
        this.phone = phone;
        this.idCard = idCard;
    }

    public Person(String fullName) {
        this.full_name = fullName;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Override
    public String toString() {
        return full_name ;
    }
}
