package com.project.project2.connection;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            //Connect by XAMPP
//            Class.forName("com.mysql.jdbc.Driver");
//            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/carrentalmanagent", "root", "");
            //Connect by MSSSQL
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setServerName("ROOKIEWHITE");
            ds.setUser("sa");
            ds.setPassword("123");
            ds.setPortNumber(1433);
            ds.setDatabaseName("CarRentalManagement");
            conn = ds.getConnection();
            System.out.println("okay");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getConnection());
    }
}
