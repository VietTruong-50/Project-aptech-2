package com.project.project2.service;

import com.project.project2.model.Contract;

import java.sql.SQLException;

public interface IContract {
    void insertContract(Contract contract);
    Contract findContractByIdCustomer(int id_customer) throws SQLException;
}
