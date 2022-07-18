package com.project.project2.service;

import com.project.project2.model.Car;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public interface ICar  {
    ObservableList<Car> CARLIST = FXCollections.observableArrayList();
    List<Car> findAllCar() throws SQLException;
    void insertCar(Car car, File file);
    void deleteCar(Car car) throws SQLException;
    void updateCar(Car car, File file);
}
