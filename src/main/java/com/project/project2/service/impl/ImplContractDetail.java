package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.ContractDetail;
import com.project.project2.service.IContractDetail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImplContractDetail implements IContractDetail {
    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    @Override
    public void insertContractDetail(ContractDetail contractDetail) {
        try{
            sql = "INSERT INTO ContractDetail(id_contract, id_car) " +
                    "VALUES (?, ?)";
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(sql);
            pr.setInt(1, contractDetail.getId_contract());
            pr.setInt(2, contractDetail.getId_car());

            pr.executeUpdate();
            conn.commit();
        }catch (Exception e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    @Override
    public void updateContractDetail(ContractDetail contractDetail) {
//        try {
//            sql = "UPDATE ContractDetail SET deposit = ? WHERE id_contract = ?";
//            pr = conn.prepareStatement(sql);
//            pr.setDouble(1, contractDetail.getDeposit());
//            pr.setInt(2, contractDetail.getId_contract());
//
//            pr.executeUpdate();
//        } catch (Exception e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean deleteContractDetail(int id_contract) {
        try {
            sql = "DELETE FROM ContractDetail WHERE id_contract = ?";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, id_contract);
            pr.executeUpdate();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Integer> findIdCarByIdContract(int idContract) throws SQLException {
        List<Integer> list = new ArrayList<>();
        sql = "SELECT id_car FROM ContractDetail WHERE id_contract = ?";
        pr = conn.prepareStatement(sql);
        pr.setInt(1, idContract);
        rs = pr.executeQuery();
        while (rs.next()){
            list.add(rs.getInt("id_car"));
        }
        return list;
    }
}
