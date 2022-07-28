package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import com.project.project2.model.Car;
import com.project.project2.service.MyListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class CarDetailController{

    @FXML
    public JFXButton addBtn;

    @FXML
    public JFXButton cancelBtn;

    @FXML
    public ImageView img;

    @FXML
    public Label label;

    private Car car = new Car();
    private MyListener myListener;

    public void setData(Car car, MyListener myListener) {
        this.car = car;
        this.myListener = myListener;
        label.setText("License plate: " + car.getLicense_plates());
        addBtn.setVisible(true);
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/Car/" + car.getCimageSrc())));
        img.setImage(image);
        cancelBtn.setVisible(false);
    }

    public void addToContract(ActionEvent actionEvent) {
        addBtn.setVisible(false);
        cancelBtn.setVisible(true);
        myListener.onClickListener(this.car);
    }

    public void cancelAdd(ActionEvent actionEvent) {
        addBtn.setVisible(true);
        cancelBtn.setVisible(false);
        myListener.onRemoveListener(this.car);
    }
}
