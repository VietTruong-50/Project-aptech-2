package com.project.project2.controller;

import com.project.project2.model.Car;
import com.project.project2.service.impl.ImplCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.project.project2.service.ICar.CARLIST;

public class CarController implements Initializable {
    private final ImplCar implCar = new ImplCar();
    private final ObservableList<String> SEATLIST = FXCollections.observableArrayList("4", "7", "11", "16");
    File file;
    private RadioButton radioButton;

    @FXML
    public TableView carTable;

    @FXML
    public ToggleGroup status;

    @FXML
    public TableColumn idColumn;

    @FXML
    public TableColumn nameColumn;

    @FXML
    public TableColumn manufacturerColumn;

    @FXML
    public TableColumn seatNbColumn;

    @FXML
    public TableColumn priceColumn;

    @FXML
    public TableColumn modelColumn;

    @FXML
    public TableColumn statusColumn;

    @FXML
    public TextField searchCarTf;

    @FXML
    public TextField carIDTf;

    @FXML
    public TextField carNameTf;

    @FXML
    public TextField carManufactureTf;

    @FXML
    public TextField carPriceTf;

    @FXML
    public ComboBox seatNbCbb;

    @FXML
    public TextArea carModelTa;

    @FXML
    public void handleClickTableView(MouseEvent mouseEvent) {
    }
    @FXML
    public void addCar(ActionEvent actionEvent) {
        try {
            radioButton = (RadioButton) status.getSelectedToggle();
            Car car = new Car();
            car.setId_car(Integer.parseInt(carIDTf.getText()));
            car.setCar_name(carNameTf.getText());
            car.setManufacture(carManufactureTf.getText());
            car.setRental_cost(Integer.parseInt(carPriceTf.getText()));
            car.setModel(carModelTa.getText());
            car.setCar_status(radioButton.getText());
            car.setSeats(Integer.parseInt(seatNbCbb.getValue().toString()));

            implCar.insertCar(car, file);

            showCar();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    public void updateCar(ActionEvent actionEvent) {
    }
    @FXML
    public void delCar(ActionEvent actionEvent) {
    }
    @FXML
    public void resetBtn(ActionEvent actionEvent) {
    }
    @FXML
    public void getStatus(ActionEvent actionEvent) {
    }
    @FXML
    public void importFile(ActionEvent actionEvent) {
    }
    @FXML
    public void getCarImage(MouseEvent mouseEvent) {
    }
    @FXML
    public void setGoBackBtn(ActionEvent actionEvent) {
    }

    public void showCar() throws SQLException {
        CARLIST.clear();
        implCar.findAllCar();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_car"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("car_name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacture"));
        seatNbColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rental_cost"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("car_status"));

        carTable.setItems(CARLIST);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showCar();
            seatNbCbb.setItems(SEATLIST);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
