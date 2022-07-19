package com.project.project2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateContractController implements Initializable {


    public TabPane seatTabPane;

    public void setGoBackBtn(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ObservableList<Tab> tabs = FXCollections.observableArrayList();
//        ObservableList<Integer> SEAT_LIST = FXCollections.observableArrayList(4, 7, 11, 16);
//        for(int seat: SEAT_LIST){
//            tabs.add(new Tab(seat + " chỗ"));
//        }
//        seatTabPane.getTabs().addAll(tabs);
    }
}
