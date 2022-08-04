package com.project.project2.service;

import com.project.project2.model.Car;
import com.project.project2.model.ContractDetail;

import java.sql.SQLException;
import java.util.List;

public interface IContractDetail {
    void insertContractDetail(ContractDetail contractDetail);
    void deleteContractDetail(int id_contract);
    List<Integer> findIdCarByIdContract(int idContract) throws SQLException;
}
