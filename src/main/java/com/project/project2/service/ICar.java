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
    boolean deleteCar(Car car) throws SQLException;
    void updateCar(Car car, File file);
    void findCarsByStatus(String status) throws SQLException;
    void findCarsBySeat(int seat) throws SQLException;
    void findCarsByStatusAndSeat(String status, int seat) throws SQLException;
    void findCarsById(int id) throws SQLException;
    boolean findCarByLicensePlates(String lcPlate) throws SQLException;
    void importFileExcel(File file);
    void setCarStatus(String status, int id);
}
