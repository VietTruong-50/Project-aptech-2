package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Customer;
import com.project.project2.service.ICustomer;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ImplCustomer implements ICustomer {

    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    @Override
    public List<Customer> findAll() throws SQLException {
        sql = "SELECT * FROM Customers";
        conn.setAutoCommit(false);
        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();
        conn.commit();
        while (rs.next()) {
            Customer customer = new Customer(rs.getInt("id_customer"), rs.getString("customer_name"),
                    rs.getString("idCard"), rs.getString("phone"), rs.getString("address")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CUSTOMER_LIST.add(customer);
        }
        return CUSTOMER_LIST;
    }

    @Override
    public Customer findByIdCard(String idCard) throws SQLException {
        Customer customer = null;
        sql = "SELECT * FROM Customers WHERE idCard = ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, idCard);
        rs = pr.executeQuery();
        conn.commit();
        if(rs.next()) {
            customer = new Customer(rs.getInt("id_customer"), rs.getString("customer_name"),
                    rs.getString("idCard"), rs.getString("phone"), rs.getString("address")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            return customer;
        }
        return null;
    }

    @Override
    public Customer findByIdCustomer(int id_customer) throws SQLException {
        Customer customer = null;
        sql = "SELECT * FROM Customers WHERE id_customer = ?";
        pr = conn.prepareStatement(sql);
        pr.setInt(1, id_customer);
        rs = pr.executeQuery();
        if(rs.next()) {
            customer = new Customer(rs.getInt("id_customer"), rs.getString("customer_name"),
                    rs.getString("idCard"), rs.getString("phone"), rs.getString("address")
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            return customer;
        }
        return null;
    }

    @Override
    public void insertCustomer(Customer customer) {
        try {
            sql = "INSERT INTO Customers( customer_name, idCard, phone, address, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ? ,?)";
            pr = conn.prepareStatement(sql);
            pr.setString(1, customer.getFull_name());
            pr.setString(2, customer.getIdCard());
            pr.setString(3, customer.getPhone());
            pr.setString(4, customer.getAddress());
            pr.setDate(5, Date.valueOf(LocalDate.now()));
            pr.setDate(6, Date.valueOf(LocalDate.now()));

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
    public void updateCustomer(Customer customer) {
        try{
            sql = "UPDATE Customers SET customer_name = ?, idCard = ?, phone = ?, address = ?, createdAt = ?, updatedAt = ? WHERE id_customer = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, customer.getFull_name());
            pr.setString(2, customer.getIdCard());
            pr.setString(3, customer.getPhone());
            pr.setString(4, customer.getAddress());
            pr.setDate(5, Date.valueOf(customer.getCreatedAt()));
            pr.setDate(6, Date.valueOf(customer.getUpdatedAt()));
            pr.setInt(7, customer.getId_customer());

            pr.execute();
        }catch (Exception e){
//            try{
//                conn.rollback();
//            }catch (SQLException ex){
//                ex.printStackTrace();
//            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int id) {
        try {
            sql = "DELETE FROM Customers WHERE id_customer = ?";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, id);
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
}
