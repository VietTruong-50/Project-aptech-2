package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.ContractDetail;
import com.project.project2.service.IContractDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplContractDetail implements IContractDetail {
    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    @Override
    public void insertContractDetail(ContractDetail contractDetail) {
        try{
            sql = "INSERT INTO ContractDetail(id_contract, id_car, VAT, deposit)" +
                    " VALUES (?, ?, ?, ?)";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, contractDetail.getId_contract());
            pr.setInt(2, contractDetail.getId_car());
            pr.setInt(3, contractDetail.getVAT());
            pr.setDouble(4, contractDetail.getDeposit());

            pr.executeUpdate();
        }catch (Exception e){
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
            e.printStackTrace();
        }

    }

    @Override
    public void deleteContractDetail(int id_contract) {
        try {
            sql = "DELETE FROM ContractDetail WHERE id_contract = ?";
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(sql);
            pr.setInt(1, id_contract);
            conn.commit();
            pr.execute();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
