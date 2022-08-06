package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Contract;
import com.project.project2.service.IContract;

import java.sql.*;
import java.util.List;

public class ImplContract implements IContract {

    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    @Override
    public List<Contract> findAll() throws SQLException {
        sql = "SELECT * FROM CONTRACT_V";
        conn.setAutoCommit(false);
        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();
        conn.commit();
        while (rs.next()) {
            Contract contract = new Contract(rs.getInt("id_contract"), rs.getString("customer_name"), rs.getString("staff_name")
                    , rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), rs.getDouble("total_cost")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CONTRACTS.add(contract);
        }
        return CONTRACTS;
    }

    @Override
    public void insertContract(Contract contract) {
        try {
            sql = "INSERT INTO Contract(id_customer, id_staff, startDate, endDate, total_cost, createdAt, updatedAt)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(sql);
            pr.setInt(1, contract.getId_customer());
            pr.setInt(2, contract.getId_staff());
            pr.setDate(3, Date.valueOf(contract.getStartDate()));
            pr.setDate(4, Date.valueOf(contract.getEndDate()));
            pr.setDouble(5, contract.getTotal_cost());
            pr.setDate(6, Date.valueOf(contract.getCreatedAt()));
            pr.setDate(7, Date.valueOf(contract.getUpdatedAt()));

            pr.executeUpdate();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContract(int id) {
        try {
            sql = "DELETE FROM Contract WHERE id_contract = ?";
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(sql);
            pr.setInt(1, id);
            conn.commit();
            pr.executeUpdate();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Contract findContractByIdCustomer(int id_customer) throws SQLException {
        Contract contract = null;
        sql = "SELECT * FROM Contract WHERE id_customer = ?";
        pr = conn.prepareStatement(sql);
        pr.setInt(1, id_customer);
        rs = pr.executeQuery();
        conn.commit();
        if (rs.next()) {
            contract = new Contract(rs.getInt("id_contract"), rs.getInt("id_customer"), rs.getInt("id_staff")
                    , rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate(), rs.getDouble("total_cost")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            return contract;
        }
        return null;
    }
}
