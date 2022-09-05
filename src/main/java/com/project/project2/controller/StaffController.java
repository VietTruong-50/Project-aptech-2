package com.project.project2.controller;

import com.project.project2.connection.DBHandle;
import com.project.project2.model.Staff;
import com.project.project2.service.impl.ImplStaff;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.alert.AlertMaker.*;
import static com.project.project2.alert.AlertMaker.showError;
import static com.project.project2.service.IStaff.STAFF_LIST;

public class StaffController implements Initializable {

    private final ImplStaff implStaff = new ImplStaff();
    public AnchorPane root;
    public TextField nameStaffTf;
    public TextField phoneStaffTf;
    public TextField contractNbTf;
    public DatePicker birthStaffDp;
    public TextField searchTf;
    public Circle pointLight;
    public TableColumn<Objects, Objects> idColumn;
    public TableView<Staff> staffTable;
    public TableColumn<Objects, Objects> nameColumn;
    public TableColumn<Objects, Objects> birthColumn;
    public TableColumn<Objects, Objects> sdtColumn;
    public TableColumn<Objects, Objects> idCardColumn;
    public TextField idCardTf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showStaff() throws SQLException
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_staff"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("full_name"));
        sdtColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        birthColumn.setCellValueFactory(new PropertyValueFactory<>("birth"));
        idCardColumn.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        staffTable.setItems(STAFF_LIST);
        searchStaff(searchTf.textProperty(), staffTable);
    }

    public void searchStaff(StringProperty txtFind, TableView<Staff> staffTable)
    {
        FilteredList<Staff> filteredData = new FilteredList<>(STAFF_LIST, p -> true);
        txtFind.addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(staff -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (staff.getFull_name().toLowerCase().contains(lowerCaseFilter) ||
                        String.valueOf(staff.getBirth()).contains(lowerCaseFilter)) {
                    return true;
                } else return staff.getPhone().contains(lowerCaseFilter);
            });
        });
        SortedList<Staff> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(staffTable.comparatorProperty());
        staffTable.setItems(sortedData);
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void refreshTable() throws SQLException {
        STAFF_LIST.clear();
        nameStaffTf.clear();
        birthStaffDp.setValue(null);
        phoneStaffTf.clear();
        idCardTf.clear();
        contractNbTf.clear();
        implStaff.findAll();
        showStaff();
    }

    public void addStaff(ActionEvent actionEvent) throws SQLException{
        if (nameStaffTf.getText().isBlank() || birthStaffDp.getValue() == null ||
                phoneStaffTf.getText().isBlank()) {
            showWarning(null, "Vui lòng nhập đầy đủ thông tin!");
        } else if (idCardTf.getText().length() != 12) {
            showWarning(null, "ID must be 12 numbers");
        } else {
            Staff staff = new Staff();
            staff.setFull_name(nameStaffTf.getText().trim());
            staff.setBirth(birthStaffDp.getValue());
            staff.setPhone(phoneStaffTf.getText().trim());
            staff.setIdCard(idCardTf.getText());
            staff.setCreatedAt(LocalDate.now());
            staff.setUpdatedAt(LocalDate.now());
            implStaff.insertStaff(staff);
        }

        refreshTable();
    }

    public void updateStaff(ActionEvent actionEvent) throws SQLException {
        Staff staff = staffTable.getSelectionModel().getSelectedItem();
        if (staff == null) {
            showError("Error", "Haven't selected the staff to be edited");
        } else if (nameStaffTf.getText().isBlank() || birthStaffDp.getValue() == null ||
                phoneStaffTf.getText().isBlank()) {
            showWarning(null, "Please enter full information!");
        } else {
            staff.setFull_name(nameStaffTf.getText().trim());
            staff.setBirth(birthStaffDp.getValue());
            staff.setPhone(phoneStaffTf.getText().trim());
            staff.setIdCard(idCardTf.getText());
            staff.setCreatedAt(staff.getCreatedAt());
            staff.setUpdatedAt(LocalDate.now());
            implStaff.updateStaff(staff);
            refreshTable();
        }
    }

    public void delStaff(ActionEvent actionEvent) throws SQLException{
        Staff staff = staffTable.getSelectionModel().getSelectedItem();
        if (staff != null) {
            if (showConfirmation("staff").get() == ButtonType.OK) {
                if (implStaff.deleteStaff(staff.getId_staff())) {
                    showSuccess("Success", "Delete staff successfully!");
                    refreshTable();
                }else{
                    showError("Error", "This staff is related to a contract");
                }
            }
        }
    }



    public void handleClickTableView(MouseEvent mouseEvent) throws SQLException {
        Staff staff = staffTable.getSelectionModel().getSelectedItem();

        if (staff != null) {
            nameStaffTf.setText(staff.getFull_name());
            phoneStaffTf.setText(staff.getPhone());
            birthStaffDp.setValue(staff.getBirth());
            idCardTf.setText(staff.getIdCard());
            setContractNb(staff.getId_staff());
        }
    }

    public void setContractNb(int id) throws SQLException {
        ResultSet rs ;
        String query = "SELECT COUNT(*) AS nb_Contract FROM Contract JOIN Staffs ON " +
                "Contract.id_staff = Staffs.id_staff WHERE Staffs.id_staff = "+ id +" GROUP BY Contract.id_staff";
        rs = DBHandle.executeQuery(query);

        if(rs.next()){
            contractNbTf.setText(String.valueOf(rs.getInt("nb_Contract")));
        }else{
            contractNbTf.setText("0");
        }
    }
}
