package com.project.project2.controller;

import com.jfoenix.controls.JFXButton;
import com.project.project2.model.*;
import com.project.project2.service.impl.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
import java.util.stream.Collectors;

import static com.project.project2.alert.AlertMaker.showWarning;
import static com.project.project2.service.ICar.CAR_LIST;
import static com.project.project2.service.ICustomer.CUSTOMER_LIST;
import static com.project.project2.service.IStaff.STAFF_LIST;

public class ContractDetailController implements Initializable {

    private final List<Car> carList = new ArrayList<>();
    private double total = 0;
    private static Customer customer = null;
    public Contract contract;
    public boolean isEditForm;

    public AnchorPane pane;
    public TextField cus_name;
    public TextField id_card;
    public TextField phoneTf;
    public TextField addressTf;
    public TextField depositTf;
    public TextField vatTf;
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
    public ComboBox<Staff> staffCbbox;

    private final ImplContractDetail implContractDetail = new ImplContractDetail();
    private final ImplContract implContract = new ImplContract();
    private final ImplCustomer implCustomer = new ImplCustomer();
    private final ImplCar implCar = new ImplCar();
    private final ImplStaff implStaff = new ImplStaff();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showCar();
            showCustomer();
            showStaff();
            customerTable.setVisible(false);
            pane2.setLayoutY(230);
            total = 0;
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
        implCustomer.findAll();

        idCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("id_customer"));
        nameCustomerColumn.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        idCardColumn.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerTable.setItems(CUSTOMER_LIST);
    }


    public void showCar() throws SQLException {
        CAR_LIST.clear();

        implCar.findCarsByStatus("Available");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_car"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("car_name"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacture"));
        seatNbColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("rental_cost"));

        carTable.setItems(CAR_LIST);
    }

    public void showStaff() throws SQLException {
        STAFF_LIST.clear();
        implStaff.findStaffByRole("Nhân viên");
        staffCbbox.getItems().addAll(STAFF_LIST);
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
        customer = CUSTOMER_LIST.stream().filter(x -> x.getId_customer() == contract.getId_customer()).findAny().orElse(null);
        Staff staff = STAFF_LIST.stream().filter(st -> st.getId_staff() == contract.getId_staff()).findAny().orElse(null);

        List<Integer> list = implContractDetail.findIdCarByIdContract(contract.getId_contract());
//        List<Integer> list = CAR_LIST.stream().map(Car::getId_car).collect(Collectors.toList());
        for (int i : list) {
            implCar.findCarsById(i);
        }
        customerCheckbox.setVisible(false);

        cus_name.setText(customer.getFull_name());
        id_card.setText(customer.getIdCard());
        phoneTf.setText("0" + customer.getPhone());
        addressTf.setText(customer.getAddress());
        staffCbbox.setValue(staff);
        startDate.setValue(contract.getStartDate());
        endDate.setValue(contract.getEndDate());
        System.out.println(contract.getTotal_cost() );
        total = contract.getTotal_cost()*100/(100+contract.getVAT());
        totalCost.setText((int) contract.getTotal_cost() + " VND");
        depositTf.setText(String.valueOf(contract.getDeposit()));
        vatTf.setText(String.valueOf(contract.getVAT()));


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
            contract.setId_staff(staffCbbox.getValue().getId_staff());
            contract.setTotal_cost(Double.parseDouble(totalCost.getText().replace(" VND", "")));
            contract.setStartDate(startDate.getValue());
            contract.setEndDate(endDate.getValue());
            contract.setCreatedAt(LocalDate.now());
            contract.setUpdatedAt(LocalDate.now());
            contract.setVAT(Integer.parseInt(vatTf.getText()));
            contract.setDeposit(Double.parseDouble(depositTf.getText().trim()));

            implContract.insertContract(contract);
            contract = implContract.findContractByIdCustomer(customer.getId_customer());

            if (contract != null) {
                for (Car c : carList) {
                    ContractDetail contractDetail = new ContractDetail();
                    contractDetail.setId_contract(contract.getId_contract());
                    contractDetail.setId_car(c.getId_car());

                    implCar.setCarStatus("Was rented", c.getId_car());
                    implContractDetail.insertContractDetail(contractDetail);
                }
                AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractController.fxml")));
                pane.getChildren().setAll(dashboard);
            }
        }
    }

    public void updateContract(ActionEvent actionEvent) throws IOException {
        contract.setId_staff(staffCbbox.getValue().getId_staff());
        contract.setDeposit(Double.parseDouble(depositTf.getText()));
        contract.setStartDate(startDate.getValue());
        contract.setEndDate(endDate.getValue());
        contract.setVAT(Integer.parseInt(vatTf.getText()));
        contract.setTotal_cost(Double.parseDouble(totalCost.getText().replace(" VND", "")));
        contract.setUpdatedAt(LocalDate.now());
        implContract.updateContract(contract);
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractController.fxml")));
        pane.getChildren().setAll(dashboard);
    }

    public void handleClickCustomerTable(MouseEvent mouseEvent) {
        customer = customerTable.getSelectionModel().getSelectedItem();

        if (customer != null) {
            cus_name.setText(customer.getFull_name());
            id_card.setText(customer.getIdCard());
            phoneTf.setText(customer.getPhone());
            addressTf.setText(customer.getAddress());
        }
    }

    public void handleClickCarTable(MouseEvent mouseEvent) {
        if (!isEditForm) {
            Car car = carTable.getSelectionModel().getSelectedItem();
            if(car != null){
                this.carList.add(car);
                total += car.getRental_cost();
                totalCost.setText((int)total + " VND");
                carTable.getItems().remove(car);
            }
        }
    }

    public void checkCustomer(ActionEvent actionEvent) {
        if (customerCheckbox.isSelected()) {
            setVisible(false, true, 429);
        } else {
            setVisible(true, false, 230);
        }
    }

    public void setVisible( boolean editable, boolean visible, double layoutY) {

        cus_name.setEditable(editable);
        id_card.setEditable(editable);
        phoneTf.setEditable(editable);
        addressTf.setEditable(editable);
        customerTable.setVisible(visible);

        pane2.setLayoutY(layoutY);
    }

    public void getTotalCost(KeyEvent keyEvent) {
        int vat = 1;
        if (!vatTf.getText().isBlank()) vat = Integer.parseInt(vatTf.getText());
        totalCost.setText((int)(total * (100 + vat)) / 100 + " VND");
    }
}
