package com.project.project2.service;

import com.project.project2.model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IStaff {
    ObservableList<Staff> STAFF_LIST = FXCollections.observableArrayList();
    List<Staff> findAll() throws SQLException;
    boolean insertStaff(Staff staff) throws SQLException;
    boolean deleteStaff(int id) throws SQLException;
    void updateStaff(Staff Staff) throws SQLException;
    List<Staff> findStaffByRole(String role) throws SQLException;

}
