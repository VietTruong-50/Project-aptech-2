package com.project.project2.controller;

import com.project.project2.model.Car;
import com.project.project2.model.Contract;
import com.project.project2.service.impl.ImplCar;
import com.project.project2.service.impl.ImplContract;
import com.project.project2.service.impl.ImplContractDetail;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.alert.AlertMaker.showConfirmation;
import static com.project.project2.service.ICar.CAR_LIST;
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
    public TextField searchTf;

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
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractDetail.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void showEditForm(ActionEvent actionEvent) throws IOException, SQLException {
        Contract contract = contractTable.getSelectionModel().getSelectedItem();

        if(contract != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractDetail.fxml")));
            AnchorPane dashboard = loader.load();
            ContractDetailController contractDetailController = loader.getController();
            contractDetailController.viewContractDetail(contract);
            contractDetailController.contract = contract;
            contractDetailController.isEditForm = true;
            root.getChildren().setAll(dashboard);
        }
    }

    public void delContract(ActionEvent actionEvent) throws SQLException {
        Contract contract = contractTable.getSelectionModel().getSelectedItem();
        if (contract != null) {
            if (showConfirmation("contract").get() == ButtonType.OK) {
                if (setCarStatus(contract.getId_contract())) {
                    if (implContractDetail.deleteContractDetail(contract.getId_contract())) {
                        implContract.deleteContract(contract.getId_contract());
                        refresh();
                    }
                }
            }
        }
    }

    public boolean setCarStatus(int idContract) {
        try {
            List<Integer> list = implContractDetail.findIdCarByIdContract(idContract);
            for (int i : list) {
                implCar.setCarStatus("ON", i);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
        searchContract(searchTf.textProperty(), contractTable);
    }

    private void searchContract(StringProperty txtFind, TableView<Contract> contractTable) {
        FilteredList<Contract> filteredData = new FilteredList<>(CONTRACTS, p -> true);
        txtFind.addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(contract -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (contract.getCustomer_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (contract.getStaff_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return String.valueOf(contract.getTotal_cost()).contains(lowerCaseFilter);
            });
        });

        SortedList<Contract> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(contractTable.comparatorProperty());
        contractTable.setItems(sortedData);
    }
}
