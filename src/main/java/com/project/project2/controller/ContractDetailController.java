package com.project.project2.controller;

import com.project.project2.model.Car;
import com.project.project2.model.Contract;
import com.project.project2.model.ContractDetail;
import com.project.project2.model.Customer;
import com.project.project2.service.impl.ImplCar;
import com.project.project2.service.impl.ImplContract;
import com.project.project2.service.impl.ImplContractDetail;
import com.project.project2.service.impl.ImplCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ContractDetailController implements Initializable {

    private List<Car> carList = new ArrayList<>();
    private static double total = 0;

    public Label license_plates;
    public AnchorPane pane;
    public Label total_cost;
    public TextField cus_name;
    public TextField staff_id;
    public TextField id_card;
    public TextField phoneTf;
    public TextField addressTf;
    public TextField depositTf;
    public DatePicker startDate;
    public DatePicker endDate;

    private final ImplContractDetail implContractDetail = new ImplContractDetail();
    private final ImplContract implContract = new ImplContract();
    private final ImplCustomer implCustomer = new ImplCustomer();
    private final ImplCar implCar = new ImplCar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setLabel(List<Car> list) {
        this.carList = list;
        StringBuilder lp = new StringBuilder();
        for (Car c : list) {
            lp.append(c.getLicense_plates().trim().concat(", "));
            total += c.getRental_cost();
        }
        total_cost.setText((total * 115 / 100) + " VND");
        license_plates.setText(lp.toString());
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractController.fxml")));
        pane.getChildren().setAll(dashboard);
    }

    public void saveContract(ActionEvent actionEvent) throws SQLException {

        Customer customer = new Customer();
        customer.setFull_name(cus_name.getText().trim());
        customer.setIdCard(id_card.getText().trim());
        customer.setAddress(addressTf.getText().trim());
        customer.setPhone(phoneTf.getText().trim());

        implCustomer.insertCustomer(customer);
        customer = implCustomer.findCustomerByIdCard(id_card.getText().trim());

        if (customer != null) {
            Contract contract = new Contract();
            contract.setId_customer(customer.getId_customer());
            contract.setId_staff(Integer.parseInt(staff_id.getText().trim()));
            contract.setTotal_cost((total * 115 / 100));
            contract.setStartDate(startDate.getValue());
            contract.setEndDate(endDate.getValue());
            contract.setCreatedAt(LocalDate.now());
            contract.setUpdatedAt(LocalDate.now());

            implContract.insertContract(contract);
            contract = implContract.findContractByIdCustomer(customer.getId_customer());

            if (contract != null) {
                for (Car c : carList) {
                    ContractDetail contractDetail = new ContractDetail();
                    contractDetail.setId_contract(contract.getId_contract());
                    contractDetail.setId_car(c.getId_car());
                    contractDetail.setVAT(15);
                    contractDetail.setDeposit(Double.parseDouble(depositTf.getText().trim()));

                    implCar.setCarStatus(c.getId_car(), "OFF");
                    implContractDetail.insertContractDetail(contractDetail);
                }
            }
        }
    }
}
