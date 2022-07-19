package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainDashboardController implements Initializable {

    public AnchorPane root, pane;
    public Label welcomeLabel;
    public JFXButton homePageBtn;
    public JFXButton managementBtn;
    public JFXButton logoutBtn;
    public ImageView staffImg;
    public ImageView customerImg;
    public ImageView carImg;
    public JFXButton carManagementBtn;
    public JFXButton staffManagementBtn;
    public JFXButton customerManagementBtn;
    public ImageView statisticImg;
    public JFXButton chartStageBtn;
    public ImageView contractImg;
    public JFXButton contractManagementBtn;

    @FXML
    public void showManagement(ActionEvent actionEvent) {
        pane.setVisible(false);
        carManagementBtn.setVisible(true);
        contractManagementBtn.setVisible(true);
        customerManagementBtn.setVisible(true);
        staffManagementBtn.setVisible(true);
        chartStageBtn.setVisible(true);
        staffImg.setVisible(true);
        customerImg.setVisible(true);
        carImg.setVisible(true);
        contractImg.setVisible(true);
        statisticImg.setVisible(true);
    }

    @FXML
    public void logOut(ActionEvent actionEvent) {
        Parent root;
        Stage stage = (Stage) logoutBtn.getScene().getWindow();
        stage.close();
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/Login.fxml")));
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showCarManagement(ActionEvent actionEvent) {
        changeStage("CarController.fxml");
    }

    @FXML
    public void showStaffManagement(ActionEvent actionEvent) {
    }

    @FXML
    public void showChart(ActionEvent actionEvent) {
        changeStage("CreateContract.fxml");
    }

    @FXML
    public void showCustomerManagement(ActionEvent actionEvent) {
    }

    @FXML
    public void showContractManagement(ActionEvent actionEvent) {

    }

    @FXML
    public void showHomePage(ActionEvent actionEvent) {
    }

    public void setUserName(String userName){
        welcomeLabel.setText("Hello " + userName);
    }

    public void changeStage(String path){
        try {
            AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/" + path)));
            root.getChildren().setAll(dashboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeMainPane(String path){
        Parent dashboard;
        try {
            dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(path)));
            pane.getChildren().setAll(dashboard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showHomePage(){
        pane.setVisible(true);
        carManagementBtn.setVisible(false);
        contractManagementBtn.setVisible(false);
        customerManagementBtn.setVisible(false);
        staffManagementBtn.setVisible(false);
        chartStageBtn.setVisible(false);
        staffImg.setVisible(false);
        customerImg.setVisible(false);
        carImg.setVisible(false);
        contractImg.setVisible(false);
        statisticImg.setVisible(false);
        changeMainPane("/com/project/project2/SlideShow.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showHomePage();
    }
}
