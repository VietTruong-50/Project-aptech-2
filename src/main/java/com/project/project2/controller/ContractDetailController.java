package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import com.project.project2.model.*;
import com.project.project2.service.impl.ImplCar;
import com.project.project2.service.impl.ImplContract;
import com.project.project2.service.impl.ImplContractDetail;
import com.project.project2.service.impl.ImplCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.alert.AlertMaker.showWarning;
import static com.project.project2.service.ICar.CAR_LIST;
import static com.project.project2.service.ICustomer.CUSTOMER_LIST;

public class ContractDetailController implements Initializable {

    private final List<Car> carList = new ArrayList<>();
    private static double total = 0;
    private static Customer customer = null;
    public Contract contract;
    public boolean isEditForm;

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
    public TableView<Car> carTable;
    public TableColumn<Object, Object> idColumn;
    public TableColumn<Object, Object> nameColumn;
    public TableColumn<Object, Object> manufacturerColumn;
    public TableColumn<Object, Object> seatNbColumn;
    public TableColumn<Object, Object> priceColumn;
    public TableColumn<Object, Object> modelColumn;
    public TableColumn<Object, Object> idCustomerColumn;
    public TableColumn<Object, Object> nameCustomerColumn;
    public TableView<Customer> customerTable;
    public TableColumn<Object, Object> phoneColumn;
    public TableColumn<Object, Object> idCardColumn;
    public TableColumn<Object, Object> addressColumn;
    public CheckBox customerCheckbox;
    public Pane pane2;
    public ScrollPane scrollPane;
    public Label totalCost;
    public JFXButton saveBtn;
    public JFXButton updateBtn;

    private final ImplContractDetail implContractDetail = new ImplContractDetail();
    private final ImplContract implContract = new ImplContract();
    private final ImplCustomer implCustomer = new ImplCustomer();
    private final ImplCar implCar = new ImplCar();
    public CheckBox staffCheckbox;
    public TableView<Staff> staffTable;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showCar();
            showCustomer();
            showStaff();
            customerTable.setVisible(false);
            pane2.setLayoutY(230);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractController.fxml")));
        pane.getChildren().setAll(dashboard);
    }

    public void showCustomer() throws SQLException {
        CUSTOMER_LIST.clear();
        implCustomer.findAllCustomerWithoutContract();

        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("id_customer"));
        nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idCardColumn.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerTable.setItems(CUSTOMER_LIST);
    }


    public void showCar() throws SQLException {
        CAR_LIST.clear();

        implCar.findCarByStatus("ON");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_car"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("car_name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacture"));
        seatNbColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rental_cost"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        carTable.setItems(CAR_LIST);
    }

    public void showStaff() throws SQLException {

    }

    public void saveContract(ActionEvent actionEvent) throws SQLException, IOException {
        if (!customerCheckbox.isSelected()) {
            if (id_card.getText().length() != 12) {
                showWarning(null, "ID must be 12 numbers");
            } else if (phoneTf.getText().length() != 10) {
                showWarning(null, "Phone must be 10 numbers");
            } else {
                customer = new Customer();
                customer.setFull_name(cus_name.getText().trim());
                customer.setIdCard(id_card.getText().trim());
                customer.setAddress(addressTf.getText().trim());
                customer.setPhone(phoneTf.getText().trim());

                implCustomer.insertCustomer(customer);
                customer = implCustomer.findByIdCard(id_card.getText().trim());
                insertContract();
            }
        } else {
            insertContract();
        }
    }

    public void viewContractDetail(Contract contract) throws SQLException {
        customer = implCustomer.findByIdCustomer(contract.getId_customer());

        List<Integer> list = implContractDetail.findIdCarByIdContract(contract.getId_contract());
        for (int i : list) {
            implCar.findCarById(i);
        }
        customerCheckbox.setVisible(false);

        cus_name.setText(customer.getFull_name());
        id_card.setText(customer.getIdCard());
        phoneTf.setText("0" + customer.getPhone());
        addressTf.setText(customer.getAddress());
        startDate.setValue(contract.getStartDate());
        endDate.setValue(contract.getEndDate());
        totalCost.setText(String.valueOf(contract.getTotal_cost()));
        staff_id.setText(String.valueOf(contract.getId_staff()));
        depositTf.setText(String.valueOf(contract.getDeposit()));

        cus_name.setEditable(false);
        id_card.setEditable(false);
        phoneTf.setEditable(false);
        addressTf.setEditable(false);
        updateBtn.setVisible(true);
        saveBtn.setVisible(false);
    }

    public void insertContract() throws IOException, SQLException {
        if (customer != null) {
            Contract contract = new Contract();
            contract.setId_customer(customer.getId_customer());
            contract.setId_staff(Integer.parseInt(staff_id.getText().trim()));
            contract.setTotal_cost((total * 115 / 100));
            contract.setStartDate(startDate.getValue());
            contract.setEndDate(endDate.getValue());
            contract.setCreatedAt(LocalDate.now());
            contract.setUpdatedAt(LocalDate.now());
            contract.setVAT(15);
            contract.setDeposit(Double.parseDouble(depositTf.getText().trim()));

            implContract.insertContract(contract);
            contract = implContract.findContractByIdCustomer(customer.getId_customer());

            if (contract != null) {
                for (Car c : carList) {
                    ContractDetail contractDetail = new ContractDetail();
                    contractDetail.setId_contract(contract.getId_contract());
                    contractDetail.setId_car(c.getId_car());

                    implCar.setCarStatus("OFF", c.getId_car());
                    implContractDetail.insertContractDetail(contractDetail);
                }
                AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractController.fxml")));
                pane.getChildren().setAll(dashboard);
            }
        }
    }

    public void updateContract(ActionEvent actionEvent) throws IOException {
        contract.setId_staff(Integer.parseInt(staff_id.getText()));
        contract.setDeposit(Double.parseDouble(depositTf.getText()));
        contract.setStartDate(startDate.getValue());
        contract.setEndDate(endDate.getValue());
        implContract.updateContract(contract);
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractController.fxml")));
        pane.getChildren().setAll(dashboard);
    }

    public void handleClickCustomerTable(MouseEvent mouseEvent) {
        customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null) {
            cus_name.setText(customer.getFull_name());
            id_card.setText(customer.getIdCard());
            phoneTf.setText("0" + customer.getPhone());
            addressTf.setText(customer.getAddress());
        }
    }

    public void handleClickCarTable(MouseEvent mouseEvent) {
        if(!isEditForm) {
            Car car = carTable.getSelectionModel().getSelectedItem();
            this.carList.add(car);
            total += car.getRental_cost();
            totalCost.setText((total * 115) / 100 + "VND");
            carTable.getItems().remove(car);
        }
    }

    public void checkCustomer(ActionEvent actionEvent) {
        if (customerCheckbox.isSelected()) {
            setVisible(true,false, true, 429);
            staffCheckbox.setDisable(true);
        } else {
            setVisible(true, true, false, 230);
            staffCheckbox.setDisable(false);
        }
    }

    public void setVisible(boolean flag,boolean editable, boolean visible, double layoutY) {
        if(flag){
            cus_name.setEditable(editable);
            id_card.setEditable(editable);
            phoneTf.setEditable(editable);
            addressTf.setEditable(editable);
            customerTable.setVisible(visible);
            staffTable.setVisible(false);
        }else{
            staffTable.setVisible(visible);
            customerTable.setVisible(false);
        }
        pane2.setLayoutY(layoutY);
    }

    public void chooseStaff(ActionEvent actionEvent) {
        if (staffCheckbox.isSelected()) {
            setVisible(false,false, true, 429);
            customerCheckbox.setDisable(true);
        } else {
            setVisible(false,true, false, 230);
            customerCheckbox.setDisable(false);
        }
    }


    public void handleClickStaffTable(MouseEvent mouseEvent) {
        Staff staff = staffTable.getSelectionModel().getSelectedItem();

        if (staff != null) {
            staff_id.setText(String.valueOf(staff.getId_staff()));
        }
    }
}
