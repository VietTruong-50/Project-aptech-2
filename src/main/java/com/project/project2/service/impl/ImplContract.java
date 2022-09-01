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
    public List<Contract> findAll(boolean isUnsigned) throws SQLException {
        sql = "SELECT * FROM CONTRACT_V";
        if(isUnsigned){
            sql = "SELECT * FROM CONTRACT_UNSIGNED_V";
        }
        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();
        while (rs.next()) {
            Contract contract = new Contract(rs.getInt("id_contract"), rs.getInt("id_customer"), rs.getInt("id_staff"), rs.getString("staff_name")
                    , rs.getString("customer_name"), rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate()
                    , rs.getDouble("total_cost"), rs.getInt("VAT"), rs.getDouble("deposit")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CONTRACTS.add(contract);
        }
        return CONTRACTS;
    }

    @Override
    public void insertContract(Contract contract) {
        try {
            sql = "INSERT INTO Contract(id_customer, id_staff, startDate, endDate, total_cost, VAT, deposit, createdAt, updatedAt)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? )";
            pr = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            pr.setInt(1, contract.getId_customer());
            pr.setInt(2, contract.getId_staff());
            pr.setDate(3, Date.valueOf(contract.getStartDate()));
            pr.setDate(4, Date.valueOf(contract.getEndDate()));
            pr.setDouble(5, contract.getTotal_cost());
            pr.setInt(6, contract.getVAT());
            pr.setDouble(7, contract.getDeposit());
            pr.setDate(8, Date.valueOf(contract.getCreatedAt()));
            pr.setDate(9, Date.valueOf(contract.getUpdatedAt()));

            pr.executeUpdate();
            conn.commit();
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
    public void updateContract(Contract contract) {
        try {
            sql = "UPDATE Contract SET id_staff = ?, startDate = ?, endDate = ?, VAT = ?, total_cost = ?, deposit = ?, updatedAt = ? WHERE id_contract = ?";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, contract.getId_staff());
            pr.setDate(2, Date.valueOf(contract.getStartDate()));
            pr.setDate(3, Date.valueOf(contract.getEndDate()));
            pr.setInt(4, contract.getVAT());
            pr.setDouble(5, contract.getTotal_cost());
            pr.setDouble(6, contract.getDeposit());
            pr.setDate(7, Date.valueOf(contract.getUpdatedAt()));
            pr.setInt(8, contract.getId_contract());

            pr.execute();
        } catch (Exception e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteContract(int id) {
        try {
            sql = "DELETE FROM Contract WHERE id_contract = ?";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, id);
            pr.executeUpdate();
        } catch (Exception e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
            e.printStackTrace();
        }
    }

    @Override
    public Contract findContractByIdCustomer(int id_customer, int id_staff) throws SQLException {
        Contract contract = null;
        sql = "SELECT * FROM Contract WHERE id_customer = ? AND id_staff = ?";
        pr = conn.prepareStatement(sql);
        pr.setInt(1, id_customer);
        pr.setInt(2, id_staff);
        rs = pr.executeQuery();
        if (rs.next()) {
            contract = new Contract(rs.getInt("id_contract"), rs.getInt("id_customer"), rs.getInt("id_staff")
                    , rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate()
                    , rs.getDouble("total_cost"), rs.getInt("VAT"), rs.getDouble("deposit")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            return contract;
        }
        return null;
    }

    @Override
    public Contract findContractByIdCar(int id_car) throws SQLException {
        Contract contract = null;
        sql = "SELECT * FROM Contract" +
                " JOIN ContractDetail ON Contract.id_contract = ContractDetail.id_contract " +
                " JOIN Car ON Car.id_car = ContractDetail.id_car " +
                " WHERE Car.id_car = ?";
        pr = conn.prepareStatement(sql);
        pr.setInt(1, id_car);
        rs = pr.executeQuery();
        if (rs.next()) {
            contract = new Contract(rs.getInt("id_contract"), rs.getInt("id_customer"), rs.getInt("id_staff")
                    , rs.getDate("startDate").toLocalDate(), rs.getDate("endDate").toLocalDate()
                    , rs.getDouble("total_cost"), rs.getInt("VAT"), rs.getDouble("deposit")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            return contract;
        }
        return null;
    }
}
