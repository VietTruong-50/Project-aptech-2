package com.project.project2.service;

import com.project.project2.model.Contract;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IContract {
    ObservableList<Contract> CONTRACTS = FXCollections.observableArrayList();
    List<Contract> findAll(boolean isUnsigned) throws SQLException;
    void insertContract(Contract contract);
    void updateContract(Contract contract);
    void deleteContract(int id);
    Contract findContractByIdCustomer(int id_customer, int id_staff) throws SQLException;
}
