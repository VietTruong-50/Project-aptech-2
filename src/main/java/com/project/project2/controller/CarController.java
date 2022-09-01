package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import com.project.project2.connection.DBHandle;
import com.project.project2.model.Car;
import com.project.project2.model.Contract;
import com.project.project2.service.impl.ImplCar;
import com.project.project2.service.impl.ImplContract;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.alert.AlertMaker.*;
import static com.project.project2.service.ICar.CAR_LIST;

public class CarController implements Initializable {
    private final ImplCar implCar = new ImplCar();
    private final ImplContract implContract = new ImplContract();
    private final ObservableList<Integer> SEAT_LIST = FXCollections.observableArrayList(4, 7, 11, 16);
    private final ObservableList<String> Status = FXCollections.observableArrayList("Available", "Unavailable", "Was rented");

    private File file;
    private RadioButton radioButton;

    @FXML
    public TableView<Car> carTable;

    @FXML
    public ToggleGroup status;
    @FXML
    public TableColumn<Object, Object> idColumn;

    @FXML
    public TableColumn<Object, Object> nameColumn;

    @FXML
    public TableColumn<Object, Object> manufacturerColumn;

    @FXML
    public TableColumn<Object, Object> seatNbColumn;

    @FXML
    public TableColumn<Object, Object> priceColumn;

    @FXML
    public TableColumn<Object, Object> modelColumn;

    @FXML
    public TableColumn<Object, Object> statusColumn;

    @FXML
    public TextField searchCarTf;

    @FXML
    public TextField carNameTf;

    @FXML
    public TextField carManufactureTf;

    @FXML
    public TextField carPriceTf;

    @FXML
    public TextField license_platesTf;

    @FXML
    public ComboBox<Integer> seatNbCbb;

    @FXML
    public TextArea carModelTa;

    @FXML
    public RadioButton rBtn1;
    public RadioButton rBtn2;
    public RadioButton rBtn3;

    @FXML
    public ImageView carImage;

    @FXML
    public AnchorPane carPane;

    public ComboBox<String> filterCarBySttCb;

    public ComboBox<Integer> filterCarBySeatCb;

    public JFXButton addBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refresh();
            seatNbCbb.setItems(SEAT_LIST);
            filterCarBySttCb.setItems(Status);
            filterCarBySeatCb.setItems(SEAT_LIST);
            rBtn3.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleClickTableView(MouseEvent mouseEvent) throws SQLException {
        Car car = carTable.getSelectionModel().getSelectedItem();

        if (car != null) {
            addBtn.setDisable(true);
            carNameTf.setText(car.getCar_name());
            carManufactureTf.setText(car.getManufacture());
            carPriceTf.setText(String.valueOf((int) car.getRental_cost()));
            seatNbCbb.setValue(car.getSeats());
            license_platesTf.setText(car.getLicense_plates());
            carModelTa.setText(car.getModel());
            carModelTa.setWrapText(true);
            if(car.getCar_status().equals("Was rented")){
                rBtn3.setSelected(true);
                rBtn1.setDisable(true);
                rBtn2.setDisable(true);
                rBtn3.setDisable(true);
            } else if (car.getCar_status().equals("Available")) {
                rBtn1.setSelected(true);
                rBtn1.setDisable(false);
                rBtn2.setDisable(false);
            } else {
                rBtn1.setDisable(false);
                rBtn2.setDisable(false);
                rBtn2.setSelected(true);
            }
            setCarImg(car);
        }
    }

    @FXML
    public void addCar(ActionEvent actionEvent) throws SQLException {
        radioButton = (RadioButton) status.getSelectedToggle();

        if (carNameTf.getText().isBlank() || carManufactureTf.getText().isBlank() ||
                carPriceTf.getText().isBlank() || carModelTa.getText().isBlank() ||
                radioButton.getText().isBlank() || !file.exists()) {
            showWarning(null, "Please enter full information!");
        } else if (implCar.findCarByLicensePlates(license_platesTf.getText())) {
            showWarning(null, "This license plates is already exist!");
        } else {
            Car car = new Car();
            car.setLicense_plates(license_platesTf.getText().trim());
            car.setCar_name(carNameTf.getText().trim());
            car.setManufacture(carManufactureTf.getText().trim());
            car.setRental_cost(Integer.parseInt(carPriceTf.getText()));
            car.setModel(carModelTa.getText().trim());
            car.setCar_status(radioButton.getText());
            car.setSeats(seatNbCbb.getValue());
            car.setCreatedAt(LocalDate.now());
            car.setUpdatedAt(LocalDate.now());
            car.setCimageSrc(file.toString().substring(file.toString().lastIndexOf('\\') + 1));

            implCar.insertCar(car, file);
        }
        refresh();
    }

    @FXML
    public void updateCar(ActionEvent actionEvent) throws SQLException, FileNotFoundException {
        Car car = carTable.getSelectionModel().getSelectedItem();
        if (car == null) {
            showError("Error", "Haven't selected the car to be edited");
        } else if (carNameTf.getText().isBlank() || carManufactureTf.getText().isBlank() ||
                carPriceTf.getText().isBlank() || carModelTa.getText().isBlank() || file.exists()) {
            showWarning(null, "Please enter full information!");
        } else {
            double dcl = (Double.parseDouble(carPriceTf.getText()) - car.getRental_cost());
            radioButton = (RadioButton) status.getSelectedToggle();
            car.setCar_name(carNameTf.getText().trim());
            car.setManufacture(carManufactureTf.getText().trim());
            car.setModel(carModelTa.getText().trim());
            car.setSeats(seatNbCbb.getValue());
            car.setRental_cost(Double.parseDouble(carPriceTf.getText()));
            car.setCar_status(radioButton.getText());
            car.setLicense_plates(license_platesTf.getText().trim());
            car.setCreatedAt(car.getCreatedAt());
            car.setUpdatedAt(LocalDate.now());
            car.setCimageSrc(file.toString().substring(file.toString().lastIndexOf('\\') + 1));

            Contract contract = implContract.findContractByIdCar(car.getId_car());
            contract.setTotal_cost(contract.getTotal_cost() + dcl );
            implContract.updateContract(contract);

            implCar.updateCar(car, file);
            refresh();
        }
    }

    @FXML
    public void delCar(ActionEvent actionEvent) throws SQLException {
        Car car = carTable.getSelectionModel().getSelectedItem();
        if (car != null) {
            if (showConfirmation("car").get() == ButtonType.OK) {
                if (implCar.deleteCar(car)) {
                    showSuccess("Success", "Delete car success!");
                    refresh();
                } else {
                    showError("Error", "This car was rented!");
                }
            }
        }
    }

    @FXML
    public void resetBtn(ActionEvent actionEvent) throws SQLException {
        refresh();
    }

    @FXML
    public void importFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        Stage stage = new Stage();
        file = fc.showOpenDialog(stage);
        implCar.importFileExcel(file);
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
    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        carPane.getChildren().setAll(dashboard);
    }

    public void showCar() throws SQLException {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_car"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("car_name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacture"));
        seatNbColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rental_cost"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("car_status"));

        carTable.setItems(CAR_LIST);
        searchCar(searchCarTf.textProperty(), carTable);
    }


    public void refresh() throws SQLException {
        filterCarBySttCb.getSelectionModel().clearSelection();
        filterCarBySeatCb.getSelectionModel().clearSelection();
        license_platesTf.clear();
        carNameTf.clear();
        carManufactureTf.clear();
        carPriceTf.clear();
        seatNbCbb.setValue(SEAT_LIST.get(0));
        carModelTa.clear();
        rBtn1.setSelected(false);
        rBtn2.setSelected(false);
        addBtn.setDisable(false);
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/add.png")));
        carImage.setY(0);
        carImage.setImage(img);
        CAR_LIST.clear();
        implCar.findAll();
        showCar();
    }

    public void setCarImg(Car car) throws SQLException {
        Image img;

        String query = "SELECT cimage FROM Car WHERE id_car = " + car.getId_car();
        ResultSet rs = DBHandle.executeQuery(query);
        while (rs.next()) {
//            InputStream is = rs.getBinaryStream("cimage");
//            OutputStream os = new FileOutputStream("photo.jpg");
//            byte[] contents = new byte[1024];
//            int size;
            if (car.getCimageSrc() != null) {
//                while ((size = is.read(contents)) != -1) {
//                    os.write(contents, 0, size);
//                }
                img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/Car/" + car.getCimageSrc())),
                        carImage.getFitWidth(), carImage.getFitHeight(), true, true);
                file = new File("/com/project/project2/Img/Car/" + car.getCimageSrc());
                carImage.setY(30);
            } else {
                img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/add.png")));
            }
            carImage.setImage(img);
        }
    }

    private void searchCar(StringProperty txtFind, TableView<Car> carTable) {
        FilteredList<Car> filteredData = new FilteredList<>(CAR_LIST, p -> true);
        txtFind.addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(car -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (car.getCar_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (car.getManufacture().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return car.getModel().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Car> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(carTable.comparatorProperty());
        carTable.setItems(sortedData);
    }

    public void filterCarByStatus(ActionEvent actionEvent) throws SQLException {
        CAR_LIST.clear();
        if (filterCarBySeatCb.getSelectionModel().isEmpty()) {
            implCar.findCarsByStatus(filterCarBySttCb.getValue());
        } else {
            implCar.findCarsByStatusAndSeat(filterCarBySttCb.getValue(), filterCarBySeatCb.getValue());
        }
        showCar();
    }

    public void filterCarBySeat(ActionEvent actionEvent) throws SQLException {
        CAR_LIST.clear();
        if (filterCarBySttCb.getSelectionModel().isEmpty()) {
            implCar.findCarsBySeat(filterCarBySeatCb.getValue());
        } else {
            implCar.findCarsByStatusAndSeat(filterCarBySttCb.getValue(), filterCarBySeatCb.getValue());
        }
        showCar();
    }
}
