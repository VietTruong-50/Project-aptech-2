package com.project.project2.service.impl;

import com.project.project2.model.Car;
import com.project.project2.repository.CarRepository;
import com.project.project2.service.ICar;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImplCar implements ICar {

    @Autowired
    CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
