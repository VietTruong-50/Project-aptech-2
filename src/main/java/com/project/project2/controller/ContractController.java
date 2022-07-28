package com.project.project2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class ContractController {
    public AnchorPane root;

    public void showAddForm(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/CreateContract.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void showEditForm(ActionEvent actionEvent) {
    }

    public void delContract(ActionEvent actionEvent) {
    }

    public void expPDF(ActionEvent actionEvent) {
    }

    public void refreshTable(ActionEvent actionEvent) {
    }

    public void updateNtx(ActionEvent actionEvent) {
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }
}
