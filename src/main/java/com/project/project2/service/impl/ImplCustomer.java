package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Car;
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
    public Customer findCustomerByIdCard(String idCard) throws SQLException {
        Customer customer = null;
        sql = "SELECT * FROM Customers WHERE idCard = ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, idCard);
        rs = pr.executeQuery();
        conn.commit();
        while (rs.next()) {
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
            pr.setString(3, customer.getIdCard());
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
    public void deleteCustomer(Customer customer) {
        try {

            sql = "DELETE FROM Customers WHERE id_customer = ?";
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(sql);
            pr.setInt(1, customer.getId_customer());
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
}
