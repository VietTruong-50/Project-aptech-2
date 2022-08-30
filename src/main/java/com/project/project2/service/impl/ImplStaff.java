package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Car;
import com.project.project2.model.Staff;
import com.project.project2.service.IStaff;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImplStaff implements IStaff {
    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;
    @Override
    public List<Staff> findAll() throws SQLException {
        sql = "SELECT * FROM Staffs";
        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff(rs.getInt("id_staff"), rs.getString("staff_name"), rs.getDate("birth").toLocalDate(), rs.getString("phone"),
                    rs.getString("role"),  rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            STAFF_LIST.add(staff);
        }
        return STAFF_LIST;
    }

    @Override
    public boolean insertStaff(Staff staff){
        try
        {
            sql = "INSERT INTO Staffs(staff_name, birth, phone, role, createdAt, updatedAt) Values (?, ?, ?, ?, ?, ?)";
            pr = conn.prepareStatement(sql);
            pr.setString(1, staff.getFull_name());
            pr.setDate(2, Date.valueOf(staff.getBirth()));
            pr.setString(3, staff.getPhone());
            pr.setString(4, staff.getRole());
            pr.setDate(5, Date.valueOf(staff.getCreatedAt()));
            pr.setDate(6, Date.valueOf(staff.getUpdatedAt()));
            pr.executeUpdate();
        } catch (SQLException e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteStaff(int id) {
        try
        {
            sql = "DELETE FROM Staffs where id_staff = ?";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, id);
            pr.executeUpdate();
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateStaff(Staff staff){
        try
        {
            sql = "UPDATE Staffs Set staff_name = ?, birth = ?, phone = ?, role = ?, createdAt = ?, updatedAt = ? where id_staff = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, staff.getFull_name());
            pr.setDate(2, Date.valueOf(staff.getBirth()));
            pr.setString(3, staff.getPhone());
            pr.setString(4, staff.getRole());
            pr.setDate(5, Date.valueOf(staff.getCreatedAt()));
            pr.setDate(6, Date.valueOf(staff.getUpdatedAt()));
            pr.setInt(7, staff.getId_staff());
            pr.executeUpdate();
        } catch (SQLException e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Staff> findStaffByRole(String role) throws SQLException {
        sql = "SELECT * FROM Staffs WHERE role = ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, role);
        rs = pr.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff(rs.getInt("id_staff"), rs.getString("staff_name"), rs.getDate("birth").toLocalDate(),"0" + rs.getString("phone"),
                    rs.getString("role"),  rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            STAFF_LIST.add(staff);
        }
        return STAFF_LIST;
    }
}
