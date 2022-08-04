package com.project.project2.controller;

import com.project.project2.model.Contract;
import com.project.project2.service.impl.ImplCar;
import com.project.project2.service.impl.ImplContract;
import com.project.project2.service.impl.ImplContractDetail;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.service.IContract.CONTRACTS;

public class ContractController implements Initializable {
    public AnchorPane root;
    public TableView<com.project.project2.model.Contract> contractTable;
    public TableColumn<Object, Object> idColumn;
    public TableColumn<Object, Object> cusNameColumn;
    public TableColumn<Object, Object> staffNameColumn;
    public TableColumn<Object, Object> ttCostColumn;
    public TableColumn<Object, Object> startDateColumn;
    public TableColumn<Object, Object> endDateColumn;

    private final ImplContract implContract = new ImplContract();
    private final ImplContractDetail implContractDetail = new ImplContractDetail();
    private final ImplCar implCar = new ImplCar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAddForm(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/CreateContract.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void showEditForm(ActionEvent actionEvent) {
    }

    public void delContract(ActionEvent actionEvent) throws SQLException {
        Contract contract = contractTable.getSelectionModel().getSelectedItem();

        implContractDetail.deleteContractDetail(contract.getId_contract());
        implContract.deleteContract(contract.getId_contract());

        List<Integer> list = implContractDetail.findIdCarByIdContract(contract.getId_contract());
        for (int i : list) {
            implCar.setCarStatus(i, "ON");
        }
    }

    public void exportPDF(ActionEvent actionEvent) {
    }

    public void refresh() throws SQLException {
        CONTRACTS.clear();
        showContract();
    }

    public void updateReturnDate(ActionEvent actionEvent) {
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void showContract() throws SQLException {
        implContract.findAll();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_contract"));
        cusNameColumn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        staffNameColumn.setCellValueFactory(new PropertyValueFactory<>("staff_name"));
        ttCostColumn.setCellValueFactory(new PropertyValueFactory<>("total_cost"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        contractTable.setItems(CONTRACTS);

    }


}
