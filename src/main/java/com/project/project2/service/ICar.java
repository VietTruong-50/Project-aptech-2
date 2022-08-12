package com.project.project2.service;

import com.project.project2.model.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public interface ICar  {
    ObservableList<Car> CAR_LIST = FXCollections.observableArrayList();
    List<Car> findAll() throws SQLException;
    void insertCar(Car car, File file);
    void deleteCar(Car car) throws SQLException;
    void updateCar(Car car, File file);
    List<Car> findCarByStatus(String status) throws SQLException;
    List<Car> findCarById(int id) throws SQLException;
    void importFileExcel(File file);
    void setCarStatus(String status, int id);
}
