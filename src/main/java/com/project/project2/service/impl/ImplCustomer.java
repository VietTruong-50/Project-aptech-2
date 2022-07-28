package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Car;
import com.project.project2.model.Customer;
import com.project.project2.service.ICustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            Customer customer = new Customer(rs.getInt("id_customer"), rs.getString("name"),
                    rs.getString("idCard"), rs.getString("phone"), rs.getString("address"));
            CUSTOMER_LIST.add(customer);
        }
        return CUSTOMER_LIST;
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {
        sql = "DELETE FROM Customers WHERE id_customer = ?";
        conn.setAutoCommit(false);
        pr = conn.prepareStatement(sql);
        pr.setInt(1, customer.getId_customer());
        pr.executeUpdate();
        conn.commit();
        try {
            conn.rollback();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
