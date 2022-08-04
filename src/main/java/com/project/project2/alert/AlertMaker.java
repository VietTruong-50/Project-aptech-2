package com.project.project2.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMaker {

    public static void showError(String title, String detail){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(detail);
        alert.showAndWait();
    }

    public static void showSuccess(String title,  String detail){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(detail);
        alert.showAndWait();
    }

    public static void showWarning(String title,  String detail){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(detail);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmation(String value){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText("Are you sure to delete this " + value +" ?");
        alert.setContentText("");
        return alert.showAndWait();
    }
}
