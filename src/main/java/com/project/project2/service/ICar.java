package com.project.project2.service;

import com.project.project2.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICar  {
    List<Car> findAll();
}
