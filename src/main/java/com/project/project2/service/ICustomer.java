package com.project.project2.service;


import com.project.project2.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface ICustomer {
    ObservableList<Customer> CUSTOMER_LIST = FXCollections.observableArrayList();
    List<Customer> findAll() throws SQLException;
    void deleteCustomer(Customer customer) throws SQLException;
}
