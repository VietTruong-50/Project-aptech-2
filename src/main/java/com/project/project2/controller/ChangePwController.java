package com.project.project2.controller;

import com.project.project2.connection.DBHandle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.SQLException;

public class ChangePwController {


    @FXML
    private TextField user_nameTf;

    @FXML
    private PasswordField oldPwTf, newPwTf;

    @FXML
    private PasswordField retypeNewPwTf;

    @FXML
    private Button confirmButton;

    @FXML
    void confirmButton(javafx.event.ActionEvent actionEvent) throws SQLException {
        String user_name = user_nameTf.getText();
        String oldPw = oldPwTf.getText();
        String newPw = newPwTf.getText();
        String retypeNewPw = retypeNewPwTf.getText();

        String query;
        query = "SELECT * FROM users WHERE user_name = '" + user_name + "' AND password = '" + oldPw + "'";

        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();

        if (!DBHandle.executeQuery(query).next()) {
            JOptionPane.showMessageDialog(null, "Kiểm tra lại tên đăng nhập hoặc mật khẩu");
        } else if (newPw.equals(retypeNewPw)) {
            query = "UPDATE Users SET password= '" + newPw + "' WHERE user_name = '" + user_name + "'";

            if (DBHandle.executeUpdate(query)) {
                System.out.println("An existing user was updated successfully!");
                JOptionPane.showMessageDialog(null, "Thay đổi mật khẩu thành công");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Không trùng khớp");
        }

    }
}
