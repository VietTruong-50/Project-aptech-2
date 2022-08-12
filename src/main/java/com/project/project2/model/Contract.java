package com.project.project2.model;

import java.time.LocalDate;

public class Contract {
    private int id_contract;
    private int id_customer;
    private int id_staff;
    private LocalDate startDate;
    private LocalDate endDate;
    private double total_cost;
    private int VAT;
    private double deposit;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String staff_name;
    private String customer_name;

    public Contract(){

    }

    public Contract(int id_contract, int id_customer, int id_staff, LocalDate startDate, LocalDate endDate, double total_cost, int VAT, double deposit, LocalDate createdAt, LocalDate updatedAt) {
        this.id_contract = id_contract;
        this.id_customer = id_customer;
        this.id_staff = id_staff;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total_cost = total_cost;
        this.VAT = VAT;
        this.deposit = deposit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Contract(int id_contract, int id_customer, int id_staff, String staff_name, String customer_name, LocalDate startDate, LocalDate endDate, double total_cost, int VAT, double deposit, LocalDate createdAt, LocalDate updatedAt) {
        this.id_contract = id_contract;
        this.id_customer = id_customer;
        this.id_staff = id_staff;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total_cost = total_cost;
        this.VAT = VAT;
        this.deposit = deposit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.staff_name = staff_name;
        this.customer_name = customer_name;
    }

    public int getId_contract() {
        return id_contract;
    }

    public void setId_contract(int id_contract) {
        this.id_contract = id_contract;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_staff() {
        return id_staff;
    }

    public void setId_staff(int id_staff) {
        this.id_staff = id_staff;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(double total_cost) {
        this.total_cost = total_cost;
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

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id_contract=" + id_contract +
                ", id_customer=" + id_customer +
                ", id_staff=" + id_staff +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", total_cost=" + total_cost +
                ", VAT=" + VAT +
                ", deposit=" + deposit +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", staff_name='" + staff_name + '\'' +
                ", customer_name='" + customer_name + '\'' +
                '}';
    }
}
