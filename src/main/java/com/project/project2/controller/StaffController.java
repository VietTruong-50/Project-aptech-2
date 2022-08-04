package com.project.project2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class StaffController {
    public AnchorPane root;

    public void showAddForm(ActionEvent actionEvent) {
    }

    public void showEditForm(ActionEvent actionEvent) {
    }

    public void delStaff(ActionEvent actionEvent) {
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void refreshTable(ActionEvent actionEvent) {
    }

    public void importFile(ActionEvent actionEvent) {
    }
}
