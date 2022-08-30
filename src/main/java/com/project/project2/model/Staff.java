package com.project.project2.model;

import java.time.LocalDate;

public class Staff extends Person{
    private int id_staff;
    private LocalDate birth;
    private String role;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Staff(){

    }

    public Staff(String full_name, int id_staff){
        super(full_name);
        this.id_staff = id_staff;
    }

    public Staff(int id_staff, String full_name, LocalDate birth, String phone, String role, LocalDate createdAt, LocalDate updatedAt) {
        super(full_name, phone);
        this.id_staff = id_staff;
        this.birth = birth;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Staff(String full_name, LocalDate birth, String phone, LocalDate createdAt, LocalDate updatedAt) {
        super(full_name, phone);
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
