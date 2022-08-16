package com.project.project2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.Objects;

public class StaffController {
    public AnchorPane root;
    public TextField nameStaffTf;
    public TextField phoneStaffTf;
    public TextField contractNbTf;
    public DatePicker birthStaffDp;
    public Circle pointLight;


    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void refreshTable(ActionEvent actionEvent) {
    }

    public void importFile(ActionEvent actionEvent) {
    }

    public void addStaff(ActionEvent actionEvent) {


    }

    public void updateStaff(ActionEvent actionEvent) {
    }

    public void delStaff(ActionEvent actionEvent) {
    }
}
