package com.project.project2.controller;

import com.project.project2.model.Customer;
import com.project.project2.service.impl.ImplCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import java.util.Objects;
import java.util.ResourceBundle;

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

    public void handleClickTableView(MouseEvent mouseEvent) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if(customer != null){
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

    public void delCustomer(ActionEvent actionEvent) {
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void saveBtn(ActionEvent actionEvent) {
    }

    public void refreshBtn(ActionEvent actionEvent) throws SQLException {
        refresh();
    }

    public void refresh() throws SQLException {
        CUSTOMER_LIST.clear();
        showCustomer();
    }

    public void showCustomer() throws SQLException {
        implCustomer.findAll();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_customer"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("car_name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idCardColumn.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerTable.setItems(CUSTOMER_LIST);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
