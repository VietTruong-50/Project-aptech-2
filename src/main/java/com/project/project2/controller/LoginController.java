package com.project.project2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    @FXML
    public void loginButton(ActionEvent actionEvent) {
    }

    @FXML
    public void changePwButton(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/View/ChangePw.fxml")));
        Stage ChangePwStage = new Stage();
        ChangePwStage.setScene(new Scene(root));
        ChangePwStage.show();
    }
}
