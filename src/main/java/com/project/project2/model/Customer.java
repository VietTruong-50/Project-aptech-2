package com.project.project2.model;

public class Customer extends Person{
    private int id_customer;
    private String idCard;
    private String address;

    public Customer(int id_customer, String full_name, String idCard, String phone, String address) {
        super(full_name, phone);
        this.id_customer = id_customer;
        this.address = address;
        this.idCard = idCard;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
