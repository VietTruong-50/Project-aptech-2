package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import com.project.project2.model.Car;
import com.project.project2.service.impl.ImplCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CreateContractController implements Initializable {


    private final ImplCar implCar = new ImplCar();

    public TabPane seatTabPane;
    public Pagination fourPagination;
    public AnchorPane sevenPane;
    public Label license_pl;
    public GridPane grid;
    public ScrollPane scrollPane;

    public void setGoBackBtn(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void generateCar() throws SQLException, IOException {
        List<Car> carList = implCar.findAllCar();
        int column = 0;
        int row = 0;
        for(Car c: carList){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/project/project2/CarDetail.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            CarDetailController carDetailController = fxmlLoader.getController();
            carDetailController.setData(c);

            if(column == 3){
                column = 0;
                row++;
            }

            grid.add(anchorPane, column++, row);
            GridPane.setMargin(anchorPane, new Insets(10));
        }
    }

}
