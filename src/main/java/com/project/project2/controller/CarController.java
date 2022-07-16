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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.service.ICar.CARLIST;

public class CarController implements Initializable {
    private final ImplCar implCar = new ImplCar();
    private final ObservableList<String> SEATLIST = FXCollections.observableArrayList("4", "7", "11", "16");
    private File file;
    private RadioButton radioButton;

    @FXML
    public TableView<Car> carTable;

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
    public RadioButton rBtn1;

    @FXML
    public RadioButton rBtn2;

    @FXML
    public ImageView carImage;

    @FXML
    public void handleClickTableView(MouseEvent mouseEvent) {
        Car car = carTable.getSelectionModel().getSelectedItem();
        if (car != null) {
            carIDTf.setEditable(false);
            carIDTf.setText("" + car.getId_car());
            carNameTf.setText(car.getCar_name());
            carManufactureTf.setText(car.getManufacture());
            carPriceTf.setText("" + car.getRental_cost());
            seatNbCbb.setValue(car.getSeats());
            carModelTa.setText(car.getModel());
            carModelTa.setWrapText(true);
            if (car.getCar_status().equals("ON")) {
                rBtn1.setSelected(true);
            } else {
                rBtn2.setSelected(true);
            }
//            setCarImg(car.getId_car());
        }
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

            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void updateCar(ActionEvent actionEvent) throws SQLException {
        Car car = carTable.getSelectionModel().getSelectedItem();
        if (car == null) {
//            showError("Lỗi nhận dạng", "Chưa chọn xe cần sua trong bảng");
        } else {
            car.setCar_name(carNameTf.getText());
            car.setManufacture(carManufactureTf.getText());
            car.setModel(carModelTa.getText());
            car.setSeats((Integer) seatNbCbb.getValue());
            car.setRental_cost(Integer.parseInt(carPriceTf.getText()));
            car.setCar_status(car.getCar_status());

            implCar.updateCar(car, file);
            refresh();
        }
    }

    @FXML
    public void delCar(ActionEvent actionEvent) {
    }

    @FXML
    public void resetBtn(ActionEvent actionEvent) throws SQLException {
        carIDTf.setEditable(true);
        refresh();
    }

    @FXML
    public void getStatus(ActionEvent actionEvent) {
    }

    @FXML
    public void importFile(ActionEvent actionEvent) {
    }

    @FXML
    public void getCarImage(MouseEvent mouseEvent) {
        FileChooser fc = new FileChooser();
        Stage stage = new Stage();
        file = fc.showOpenDialog(stage);
        if (file != null) {
            Image img = new Image(file.toURI().toString(), 188, 141, true, true);
            carImage.setFitWidth(188);
            carImage.setFitHeight(141);
            carImage.setSmooth(true);
            carImage.setY(30);
            carImage.setPreserveRatio(true);
            carImage.setImage(img);
        }
    }
    @FXML
    public void setGoBackBtn(ActionEvent actionEvent) {
    }

    public void showCar() throws SQLException {
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

    public void refresh() throws SQLException {
        carIDTf.setText("");
        carNameTf.setText("");
        carManufactureTf.setText("");
        carPriceTf.setText("");
        seatNbCbb.setValue(4);
        carModelTa.setText("");
        rBtn1.setSelected(false);
        rBtn2.setSelected(false);
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/add.png")));
        carImage.setY(0);
        carImage.setImage(img);
        CARLIST.clear();
        showCar();
    }
}
