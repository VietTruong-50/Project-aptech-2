package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.connection.DBHandle;
import com.project.project2.model.Car;
import com.project.project2.repository.CarRepository;
import com.project.project2.service.ICar;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ImplCar implements ICar {

    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    @Override
    public List<Car> findAllCar() throws SQLException {
            sql = "SELECT * FROM Car";
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(sql);
            rs = pr.executeQuery();
            conn.commit();
            while (rs.next()) {
                Car car = new Car(rs.getInt("id_car"), rs.getString("car_name"), rs.getString("manufacture"), rs.getInt("seats"),
                        rs.getInt("rental_cost"), rs.getString("model"), rs.getString("car_status"));
                CARLIST.add(car);
            }
            return CARLIST;
    }

    @Override
    public void insertCar(Car car, File file) {
        try{
            System.out.println(car);
            sql = "INSERT INTO Car(id_car, car_name, manufacture, seats, rental_cost, model, car_status, cimage) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, car.getId_car());
            pr.setString(2, car.getCar_name());
            pr.setString(3, car.getManufacture());
            pr.setInt(4, car.getSeats());
            pr.setInt(5, car.getRental_cost());
            pr.setString(6, car.getModel());
            pr.setString(7, car.getCar_status());
            if (file != null) {
                FileInputStream fis = new FileInputStream(file);
                pr.setBinaryStream(8, fis, (int) file.length());
            } else {
                pr.setBinaryStream(8, null);
            }
            pr.executeUpdate();
        }catch (Exception e){
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(Car car) {
        try {
            sql = "DELETE FROM Car WHERE id_car = ?";
            conn.setAutoCommit(false);
            pr = conn.prepareStatement(sql);
            pr.setInt(1, car.getId_car());
            pr.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
