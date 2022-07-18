package com.project.project2.model;

import javafx.scene.image.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car {
    private int id_car;
    private String car_name;
    private String manufacture;
    private int seats;
    private int rental_cost;
    private String model;
    private String car_status;
}
