package com.project.project2.controller;

import com.project.project2.connection.DBHandle;
import com.project.project2.model.Car;
import com.project.project2.service.impl.ImplCar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.service.ICar.CAR_LIST;

public class CarController implements Initializable {
    private final ImplCar implCar = new ImplCar();
    private final ObservableList<Integer> SEAT_LIST = FXCollections.observableArrayList(4, 7, 11, 16);

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

    @FXML
    public RadioButton rBtn2;

    @FXML
    public ImageView carImage;

    @FXML
    public AnchorPane carPane;

    @FXML
    public void handleClickTableView(MouseEvent mouseEvent) throws SQLException, IOException {
        Car car = carTable.getSelectionModel().getSelectedItem();

        if (car != null) {
            license_platesTf.setText("" + car.getLicense_plates());
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
            setCarImg(car);
        }
    }

    @FXML
    public void addCar(ActionEvent actionEvent) throws SQLException, FileNotFoundException {
        radioButton = (RadioButton) status.getSelectedToggle();
        Car car = new Car();
        car.setLicense_plates(license_platesTf.getText());
        car.setCar_name(carNameTf.getText());
        car.setManufacture(carManufactureTf.getText());
        car.setRental_cost(Integer.parseInt(carPriceTf.getText()));
        car.setModel(carModelTa.getText());
        car.setCar_status(radioButton.getText());
        car.setSeats(seatNbCbb.getValue());
        car.setCimageSrc(file.toString().substring(file.toString().lastIndexOf('\\') + 1));

        implCar.insertCar(car, file);

        refresh();
    }

    @FXML
    public void updateCar(ActionEvent actionEvent) throws SQLException, FileNotFoundException {
        Car car = carTable.getSelectionModel().getSelectedItem();
        if (car == null) {
//            showError("Lỗi nhận dạng", "Chưa chọn xe cần sua trong bảng");
        } else {
            radioButton = (RadioButton) status.getSelectedToggle();
            car.setCar_name(carNameTf.getText());
            car.setManufacture(carManufactureTf.getText());
            car.setModel(carModelTa.getText());
            car.setSeats(seatNbCbb.getValue());
            car.setRental_cost(Integer.parseInt(carPriceTf.getText()));
            car.setCar_status(radioButton.getText());
            car.setLicense_plates(license_platesTf.getText());
            car.setCimageSrc(file.toString().substring(file.toString().lastIndexOf('/') + 1));

            implCar.updateCar(car, file);
            refresh();
        }
    }

    @FXML
    public void delCar(ActionEvent actionEvent) throws SQLException {
        Car car = carTable.getSelectionModel().getSelectedItem();
        implCar.deleteCar(car);
        refresh();
    }

    @FXML
    public void resetBtn(ActionEvent actionEvent) throws SQLException {
        refresh();
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
    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        carPane.getChildren().setAll(dashboard);
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

        carTable.setItems(CAR_LIST);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refresh();
            seatNbCbb.setItems(SEAT_LIST);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void refresh() throws SQLException {
        license_platesTf.setText("");
        carNameTf.setText("");
        carManufactureTf.setText("");
        carPriceTf.setText("");
        seatNbCbb.setValue(SEAT_LIST.get(0));
        carModelTa.setText("");
        rBtn1.setSelected(false);
        rBtn2.setSelected(false);
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/add.png")));
        carImage.setY(0);
        carImage.setImage(img);
        CAR_LIST.clear();
        showCar();
    }

    public void setCarImg(Car car) throws SQLException, IOException {
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
                carImage.setY(30);
            } else {
                img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/project/project2/Img/add.png")));
            }
            carImage.setImage(img);
        }
    }
}
