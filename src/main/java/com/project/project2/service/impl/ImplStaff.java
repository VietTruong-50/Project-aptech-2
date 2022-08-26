package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Car;
import com.project.project2.model.Staff;
import com.project.project2.service.IStaff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ImplStaff implements IStaff {
    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    public List<Staff> findAll() throws SQLException {
        sql = "SELECT * FROM Staffs";
        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();
        while (rs.next()) {
            Staff staff = new Staff(rs.getString("staff_name"), rs.getString("phone")
                    , rs.getInt("id_staff"), rs.getDate("birth").toLocalDate()
                    , rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());

            STAFFS.add(staff);
        }
        return STAFFS;
    }

}
