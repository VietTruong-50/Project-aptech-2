package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import com.project.project2.connection.DBHandle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class LoginController {
    @FXML
    public JFXButton loginButton;

    @FXML
    public TextField txtUsername;

    @FXML
    public PasswordField txtPassword;

    @FXML
    public Label lblError;

    @FXML
    public void loginButton(ActionEvent actionEvent) throws SQLException, IOException {
        String user_name = txtUsername.getText();
        String password = txtPassword.getText();

        String query = "select * from Users where user_name = '" + user_name + "' and password = '" + password + "'";

        if (!DBHandle.executeQuery(query).next()) {
            lblError.setTextFill(Color.RED);
            lblError.setText("Thông tin tên đăng nhập hoặc mật khẩu không đúng");
        } else {
            lblError.setTextFill(Color.GREEN);
            System.out.println("Login successfull! Redirecting....");
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
            loader.load();
            MainDashboardController mainDashboardController = loader.getController();
            mainDashboardController.setUserName(user_name);
            Parent root = loader.getRoot();
            Stage DashboardStage = new Stage();
            DashboardStage.setScene(new Scene(root));
            DashboardStage.setTitle("Trương Quốc Việt & viet ");
            DashboardStage.show();
        }
    }

    @FXML
    public void changePwButton(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ChangePw.fxml")));
        Stage changePwStage = new Stage();
        changePwStage.setScene(new Scene(root));
        changePwStage.show();
    }
}
