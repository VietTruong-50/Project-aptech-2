package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Car;
import com.project.project2.model.Staff;
import com.project.project2.service.IStaff;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
            Staff staff = new Staff(rs.getInt("id_staff"), rs.getString("staff_name"), rs.getDate("birth").toLocalDate(), rs.getString("phone"),  rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            STAFF_LIST.add(staff);
        }
        return STAFF_LIST;
    }

    @Override
    public boolean insertStaff(Staff staff) throws SQLException{
        try
        {
            sql = "INSERT INTO Staffs Values (?, ?, ?, ?, ?)";
            pr = conn.prepareStatement(sql);
            pr.setString(1, staff.getFull_name());
            pr.setDate(2, Date.valueOf(staff.getBirth()));
            pr.setString(3, staff.getPhone());
            pr.setDate(4, Date.valueOf(staff.getCreatedAt()));
            pr.setDate(5, Date.valueOf(staff.getUpdatedAt()));
            int count = pr.executeUpdate();
            if(count > 0)
                return true;
            else
                return false;
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
    public boolean deleteStaff(int id) throws SQLException {
        try
        {
            sql = "DELETE FROM Staffs where id_staff = ?";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, id);
            int count = pr.executeUpdate();
            if(count > 0)
                return true;
            else
                return false;
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
    public boolean updateStaff(Staff staff) throws SQLException{
        try
        {
            sql = "UPDATE Staffs Set staff_name = ?, birth = ?, phone = ?, createdAt = ?, updatedAt = ? where id_staff = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, staff.getFull_name());
            pr.setDate(2, Date.valueOf(staff.getBirth()));
            pr.setString(3, staff.getPhone());
            pr.setDate(4, Date.valueOf(staff.getCreatedAt()));
            pr.setDate(5, Date.valueOf(staff.getUpdatedAt()));
            pr.setInt(6, staff.getId_staff());
            int count = pr.executeUpdate();
            if(count > 0)
                return true;
            else
                return false;
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
    public List<Staff> findStaffByName(String name) throws SQLException {
        List<Staff> sName_Staff = new ArrayList<>();
        sql = "SELECT * FROM Staffs where staff_name like ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, "%" + name + "%");
        rs = pr.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff(rs.getInt("id_staff"), rs.getString("staff_name"), rs.getDate("birth").toLocalDate(), rs.getString("phone"),  rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            sName_Staff.add(staff);
        }
        return sName_Staff;
    }

    @Override
    public List<Staff> findStaffByBirth(String birth) throws SQLException {
        List<Staff> bName_Staff = new ArrayList<>();
        sql = "SELECT * FROM Staffs where birth = ?";
        pr = conn.prepareStatement(sql);
        pr.setDate(1, Date.valueOf(birth));
        rs = pr.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff(rs.getInt("id_staff"), rs.getString("staff_name"), rs.getDate("birth").toLocalDate(), rs.getString("phone"),  rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            bName_Staff.add(staff);
        }
        return bName_Staff;
    }

    @Override
    public List<Staff> findStaffByPhone(String phone) throws SQLException {
        List<Staff> pName_Staff = new ArrayList<>();
        sql = "SELECT * FROM Staffs where phone like ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, "%" + phone + "%");
        rs = pr.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff(rs.getInt("id_staff"), rs.getString("staff_name"), rs.getDate("birth").toLocalDate(), rs.getString("phone"),  rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            pName_Staff.add(staff);
        }
        return pName_Staff;
    }

    @Override
    public void importFileExcel(File file) {

    }
}
