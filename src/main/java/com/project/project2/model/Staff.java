package com.project.project2.model;

import java.time.LocalDate;

public class Staff extends Person{
    private int id_staff;
    private LocalDate birth;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Staff(){

    }

    public Staff(int id_staff, LocalDate birth, LocalDate createdAt, LocalDate updatedAt) {
        this.id_staff = id_staff;
        this.birth = birth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Staff(String full_name, String phone, int id_staff, LocalDate birth, LocalDate createdAt, LocalDate updatedAt) {
        super(full_name, phone);
        this.id_staff = id_staff;
        this.birth = birth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
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
}
