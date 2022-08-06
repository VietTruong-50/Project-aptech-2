package com.project.project2.controller;

import com.project.project2.model.Customer;
import com.project.project2.service.impl.ImplCustomer;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.alert.AlertMaker.*;
import static com.project.project2.service.ICustomer.CUSTOMER_LIST;

public class CustomerController implements Initializable {

    private final ImplCustomer implCustomer = new ImplCustomer();

    public TableColumn<Object, Object> idColumn;
    public TableColumn<Object, Object> nameColumn;
    public TableColumn<Object, Object> phoneColumn;
    public TableColumn<Object, Object> idCardColumn;
    public TableColumn<Object, Object> addressColumn;
    public TableView<Customer> customerTable;
    public AnchorPane root;
    public TextField searchTf;
    public TextField idTf;
    public TextField idCardTf;
    public TextField addressTf;
    public TextField phoneTf;
    public TextField nameTf;
    public Circle pointLight, pointLight1, pointLight2, pointLight3, pointLight4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refresh();
            searchCustomer(searchTf.textProperty(), customerTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleClickTableView(MouseEvent mouseEvent) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null) {
            nameTf.setEditable(true);
            phoneTf.setEditable(true);
            idCardTf.setEditable(false);
            addressTf.setEditable(true);
            idTf.setText("" + customer.getId_customer());
            nameTf.setText(customer.getFull_name());
            phoneTf.setText(customer.getPhone());
            idCardTf.setText(customer.getIdCard());
            addressTf.setText(customer.getAddress());
            pointLight.setFill(RadialGradient.valueOf("center 50% 50%, radius 50%,  green 70%, white 40%, magenta"));
            pointLight1.setFill(RadialGradient.valueOf("center 50% 50%, radius 50%,  green 70%, white 40%, magenta"));
            pointLight2.setFill(RadialGradient.valueOf("center 50% 50%, radius 50%,  green 70%, white 40%, magenta"));
            pointLight3.setFill(RadialGradient.valueOf("center 50% 50%, radius 50%,  green 70%, white 40%, magenta"));
            pointLight4.setFill(RadialGradient.valueOf("center 50% 50%, radius 50%,  green 70%, white 40%, magenta"));

        }
    }

    public void delCustomer(ActionEvent actionEvent) throws SQLException {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            if (showConfirmation("car").get() == ButtonType.OK) {
                implCustomer.deleteCustomer(customer.getId_customer());
                showSuccess("Success", "Delete customer success");
                refresh();
            }
        }
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void saveCustomer(ActionEvent actionEvent) throws SQLException {
        if (nameTf.getText().isBlank() || idCardTf.getText().isBlank() ||
                addressTf.getText().isBlank() || phoneTf.getText().isBlank()) {
            showWarning(null, "Please enter full information!");
        } else {
            Customer customer = new Customer();
            customer.setId_customer(Integer.parseInt(idTf.getText()));
            customer.setFull_name(nameTf.getText());
            customer.setIdCard(idCardTf.getText());
            customer.setAddress(addressTf.getText());
            customer.setPhone(phoneTf.getText());
            customer.setCreatedAt(LocalDate.now());
            customer.setUpdatedAt(LocalDate.now());

            implCustomer.updateCustomer(customer);
        }

        refresh();
    }

    public void refreshBtn(ActionEvent actionEvent) throws SQLException {
        refresh();
    }

    public void refresh() throws SQLException {
        CUSTOMER_LIST.clear();
        idTf.clear();
        idCardTf.clear();
        nameTf.clear();
        phoneTf.clear();
        addressTf.clear();
        showCustomer();
    }

    public void showCustomer() throws SQLException {
        implCustomer.findAll();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_customer"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idCardColumn.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerTable.setItems(CUSTOMER_LIST);
        searchCustomer(searchTf.textProperty(), customerTable);
    }

    public void searchCustomer(StringProperty txtFind, TableView<Customer> customerTable) {
        FilteredList<Customer> filteredData = new FilteredList<>(CUSTOMER_LIST, p -> true);
        txtFind.addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getFull_name().toLowerCase().contains(lowerCaseFilter) ||
                        customer.getIdCard().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return customer.getPhone().toLowerCase().contains(lowerCaseFilter);
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }
}
