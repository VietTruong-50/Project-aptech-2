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
    public void loginButton(ActionEvent actionEvent) {
        String user_name = txtUsername.getText();
        String password = txtPassword.getText();

        //query
        try {
            String query = "select * from Users where user_name = '"+ user_name +"' and password = '"+ password +"'";

            if(!DBHandle.executeQuery(query).next()){
                lblError.setTextFill(Color.RED);
                lblError.setText("Thông tin tên đăng nhập hoặc mật khẩu không đúng");
            }
            else{
                lblError.setTextFill(Color.GREEN);
                System.out.println("Login successfull! Redirecting....");
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/CarController.fxml")));
                Stage TrangChuStage = new Stage();
                TrangChuStage.setScene(new Scene(root));
                TrangChuStage.setTitle("Trương Quốc Việt & viet ");
                TrangChuStage.show();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
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
