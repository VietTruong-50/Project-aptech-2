package com.project.project2.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Car {
    private int id_car;
    private String car_name;
    private String manufacture;
    private int seats;
    private int rental_cost;
    private String model;
    private String car_status;
    private String cimageSrc;
    private String license_plates;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public Car() {

    }

    public Car(int id_car, String car_name, String manufacture, int seats, int rental_cost, String model, String car_status, String cimageSrc, String license_plates, LocalDate createdAt, LocalDate updatedAt) {
        this.id_car = id_car;
        this.car_name = car_name;
        this.manufacture = manufacture;
        this.seats = seats;
        this.rental_cost = rental_cost;
        this.model = model;
        this.car_status = car_status;
        this.cimageSrc = cimageSrc;
        this.license_plates = license_plates;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId_car() {
        return id_car;
    }

    public void setId_car(int id_car) {
        this.id_car = id_car;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getRental_cost() {
        return rental_cost;
    }

    public void setRental_cost(int rental_cost) {
        this.rental_cost = rental_cost;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCar_status() {
        return car_status;
    }

    public void setCar_status(String car_status) {
        this.car_status = car_status;
    }

    public String getCimageSrc() {
        return cimageSrc;
    }

    public void setCimageSrc(String cimageSrc) {
        this.cimageSrc = cimageSrc;
    }

    public String getLicense_plates() {
        return license_plates;
    }

    public void setLicense_plates(String license_plates) {
        this.license_plates = license_plates;
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
