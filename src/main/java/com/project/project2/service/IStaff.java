package com.project.project2.service;

import com.project.project2.model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface IStaff {
    ObservableList<Staff> STAFF_LIST = FXCollections.observableArrayList();
    List<Staff> findAll() throws SQLException;
    boolean insertStaff(Staff staff) throws SQLException;
    boolean deleteStaff(int id) throws SQLException;
    boolean updateStaff(Staff Staff) throws SQLException;
    List<Staff> findStaffByName(String name) throws SQLException;
    List<Staff> findStaffByBirth(String birth) throws SQLException;
    List<Staff> findStaffByPhone(String phone) throws SQLException;
    void importFileExcel(File file);
}
