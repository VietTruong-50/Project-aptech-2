package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import com.project.project2.model.Car;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class CarDetailController{
    @FXML
    public JFXButton addBtn;

    @FXML
    public ImageView img;

    @FXML
    public Label label;

    public void setData(Car car) {
        label.setText("License plate: " + car.getLicense_plates());
        addBtn.setVisible(true);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/Car/" + car.getCimageSrc())));
        img.setImage(image);
    }
}
