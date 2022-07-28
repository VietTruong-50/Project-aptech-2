package com.project.project2.controller;

import com.project.project2.model.Car;
import com.project.project2.service.MyListener;
import com.project.project2.service.impl.ImplCar;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateContractController implements Initializable {

    private static List<Car> carList = new ArrayList<>();
    private final ImplCar implCar = new ImplCar();
    private final List<String> strings = new ArrayList<>();

    public TabPane seatTabPane;
    public Tab sevenPane;
    public Tab fourPane;
    public GridPane grid11;
    public ScrollPane scrollPane;
    public Tab elevenPane;
    public GridPane grid4;
    public GridPane grid7;
    public AnchorPane pane;

    private MyListener myListener;

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractController.fxml")));
        pane.getChildren().setAll(dashboard);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carList.clear();
    }

    public void generateCar(int seats, GridPane grid) throws SQLException, IOException {
        carList.clear();
        carList = implCar.findCarBySeats(seats);
        if(carList.size() > 0){
            myListener = new MyListener() {
                @Override
                public void onClickListener(Car car) {
                    strings.add(car.getLicense_plates());
                }

                @Override
                public void onRemoveListener(Car car) {
                    strings.remove(car.getLicense_plates());
                }
            };
        }
        int column = 0;
        int row = 1;
        for(Car c: carList){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/project/project2/CarDetail.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            CarDetailController carDetailController = fxmlLoader.getController();
            carDetailController.setData(c, myListener);

            if(column == 3){
                column = 0;
                row++;
            }

            grid.add(anchorPane, column++, row);
            GridPane.setMargin(anchorPane, new Insets(20, 20, 40, 34));
        }
    }

    public void getCarByElevenSeats(Event event) throws SQLException, IOException {
        generateCar(11, grid11);
    }

    public void getCarBySevenSeats(Event event) throws SQLException, IOException {
        generateCar(7, grid7);
    }

    public void getCarByFourSeats(Event event) throws SQLException, IOException {
        generateCar(4, grid4);

    }

    public void createContract(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/com/project/project2/ContractDetail.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        pane.getChildren().setAll(anchorPane);
        ContractDetailController contractDetailController = fxmlLoader.getController();
        contractDetailController.setLabel(strings);
    }
}
