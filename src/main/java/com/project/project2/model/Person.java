package com.project.project2.model;


public class Person {
    private String full_name;
    private String phone;

    public Person() {

    }

    public Person(String full_name, String phone) {
        this.full_name = full_name;
        this.phone = phone;
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

    @Override
    public String toString() {
        return "Person{" +
                "full_name='" + full_name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
