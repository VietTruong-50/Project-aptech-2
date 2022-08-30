package com.project.project2.model;

import java.time.LocalDate;

public class Customer extends Person{
    private int id_customer;

    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Customer(){
        super();

    }

    public Customer(int id_customer, String full_name, String idCard, String phone, String address, LocalDate createdAt, LocalDate updatedAt) {
        super(full_name, phone, idCard);
        this.id_customer = id_customer;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return super.toString() +
                "id_customer=" + id_customer +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
