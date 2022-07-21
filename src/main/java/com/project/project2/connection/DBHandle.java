package com.project.project2.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHandle {
    private static final Connection conn = DBConnection.getConnection();
    private static PreparedStatement pr;
    private static ResultSet rs;

    public static boolean executeUpdate(String query) {
        try {
            pr = conn.prepareStatement(query);
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean execute(String query) {
        try {
            pr = conn.prepareStatement(query);
            pr.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet executeQuery(String query) {
        try {
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(query);
            rs = pr.executeQuery();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
