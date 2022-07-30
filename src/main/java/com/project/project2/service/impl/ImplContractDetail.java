package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Contract;
import com.project.project2.model.ContractDetail;
import com.project.project2.service.IContractDetail;

import java.sql.*;
import java.time.LocalDate;

public class ImplContractDetail implements IContractDetail {
    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    @Override
    public void insertContractDetail(ContractDetail contractDetail) {
        try{
            sql = "INSERT INTO ContractDetail(id_contract, id_car, VAT, deposit, returnDate)" +
                    " VALUES (?, ?, ?, ?, ?)";
            conn.prepareStatement(sql);

            pr.executeUpdate();
        }catch (Exception e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }
}
