package com.project.project2.service.impl;

import com.project.project2.connection.DBConnection;
import com.project.project2.model.Car;
import com.project.project2.service.ICar;

import java.io.File;
import java.sql.*;
import java.util.List;

public class ImplCar implements ICar {

    private final Connection conn = DBConnection.getConnection();
    private ResultSet rs = null;
    private PreparedStatement pr = null;
    private String sql;

    @Override
    public List<Car> findAll() throws SQLException {
        sql = "SELECT * FROM Car";
        pr = conn.prepareStatement(sql);
        rs = pr.executeQuery();
        while (rs.next()) {
            Car car = new Car(rs.getInt("id_car"), rs.getString("car_name"), rs.getString("manufacture"), rs.getInt("seats"),
                    rs.getDouble("rental_cost"), rs.getString("model"), rs.getString("car_status"), rs.getString("cimage")
                    , rs.getString("license_plates"), rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CAR_LIST.add(car);
        }
        return CAR_LIST;
    }

    @Override
    public void insertCar(Car car, File file) {
        try {
            System.out.println(car);
            sql = "INSERT INTO Car(car_name, manufacture, seats, rental_cost, model, car_status, cimage, license_plates, createdAt, updatedAt) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pr = conn.prepareStatement(sql);
            pr.setString(1, car.getCar_name());
            pr.setString(2, car.getManufacture());
            pr.setInt(3, car.getSeats());
            pr.setDouble(4, car.getRental_cost());
            pr.setString(5, car.getModel());
            pr.setString(6, car.getCar_status());
            pr.setString(7, car.getCimageSrc());
//            if (file != null) {
//                FileInputStream fis = new FileInputStream(file);
//                pr.setBinaryStream(7, fis, (int) file.length());
//            } else {
//                pr.setBinaryStream(7, null);
//            }
            pr.setString(8, car.getLicense_plates());
            pr.setDate(9, Date.valueOf(car.getCreatedAt()));
            pr.setDate(10, Date.valueOf(car.getUpdatedAt()));

            pr.executeUpdate();
        } catch (Exception e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteCar(Car car) {
        try {
            sql = "DELETE FROM Car WHERE id_car = ?";
            pr = conn.prepareStatement(sql);
            pr.setInt(1, car.getId_car());
            pr.executeUpdate();
            return true;
        } catch (Exception e) {
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
    public void updateCar(Car car, File file) {
        try {
            sql = "UPDATE Car SET car_name = ?, manufacture = ?, seats = ?, rental_cost = ?, model = ?, car_status = ?, cimage = ?, license_plates = ?, updatedAt = ? WHERE id_car = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, car.getCar_name());
            pr.setString(2, car.getManufacture());
            pr.setInt(3, car.getSeats());
            pr.setDouble(4, car.getRental_cost());
            pr.setString(5, car.getModel());
            pr.setString(6, car.getCar_status());
            pr.setString(7, car.getCimageSrc());
//            if(file != null){
//                FileInputStream fis = new FileInputStream(file);
//                pr.setBinaryStream(7, fis, (int) file.length());
//            }else {
//                pr.setBinaryStream(7, null);
//            }
            pr.setString(8, car.getLicense_plates());
            pr.setDate(9, Date.valueOf(car.getCreatedAt()));
            pr.setInt(10, car.getId_car());
            pr.execute();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void findCarsByStatus(String status) throws SQLException {
        sql = "SELECT * FROM Car WHERE car_status = ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, status);
        rs = pr.executeQuery();
        while (rs.next()) {
            Car car = new Car(rs.getInt("id_car"), rs.getString("car_name"), rs.getString("manufacture"), rs.getInt("seats"),
                    rs.getInt("rental_cost"), rs.getString("model"), rs.getString("car_status"), rs.getString("cimage"),
                    rs.getString("license_plates"), rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CAR_LIST.add(car);
        }
    }

    @Override
    public void findCarsBySeat(int seat) throws SQLException {
        sql = "SELECT * FROM Car WHERE seats = ?";
        pr = conn.prepareStatement(sql);
        pr.setInt(1, seat);
        rs = pr.executeQuery();
        while (rs.next()) {
            Car car = new Car(rs.getInt("id_car"), rs.getString("car_name"), rs.getString("manufacture"), rs.getInt("seats"),
                    rs.getInt("rental_cost"), rs.getString("model"), rs.getString("car_status"), rs.getString("cimage"),
                    rs.getString("license_plates"), rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CAR_LIST.add(car);
        }
    }

    @Override
    public void findCarsByStatusAndSeat(String status, int seat) throws SQLException {
        sql = "SELECT * FROM Car WHERE car_status = ? AND seats = ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, status);
        pr.setInt(2, seat);
        rs = pr.executeQuery();
        while (rs.next()) {
            Car car = new Car(rs.getInt("id_car"), rs.getString("car_name"), rs.getString("manufacture"), rs.getInt("seats"),
                    rs.getInt("rental_cost"), rs.getString("model"), rs.getString("car_status"), rs.getString("cimage"),
                    rs.getString("license_plates"), rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CAR_LIST.add(car);
        }
    }

    @Override
    public void findCarsById(int id) throws SQLException {
        sql = "SELECT * FROM Car WHERE id_car = ?";
        pr = conn.prepareStatement(sql);
        pr.setInt(1, id);
        rs = pr.executeQuery();
        if (rs.next()) {
            Car car = new Car(rs.getInt("id_car"), rs.getString("car_name"), rs.getString("manufacture"), rs.getInt("seats"),
                    rs.getInt("rental_cost"), rs.getString("model"), rs.getString("car_status"), rs.getString("cimage"),
                    rs.getString("license_plates"), rs.getDate("createdAt").toLocalDate(), rs.getDate("updatedAt").toLocalDate());
            CAR_LIST.add(car);
        }
    }

    @Override
    public boolean findCarByLicensePlates(String lcPlate) throws SQLException {
        sql = "SELECT * FROM Car WHERE license_plates = ?";
        pr = conn.prepareStatement(sql);
        pr.setString(1, lcPlate);
        rs = pr.executeQuery();
        return rs.next();
    }

    @Override
    public void importFileExcel(File file) {
//        try {
//            sql = "INSERT INTO Car(car_name, manufacture, seats, rental_cost, model, car_status, cimage, license_plates) " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";;
//            pr = conn.prepareStatement(sql);
//            if (file != null) {
//                FileInputStream fis = new FileInputStream(file);
//
//                XSSFWorkbook wb = new XSSFWorkbook(fis);
//                XSSFSheet sheet = wb.getSheetAt(0);
//                Row row;
//                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                    row = sheet.getRow(i);
//                    pr.setString(1, row.getCell(0).getStringCellValue());
//                    pr.setString(2, row.getCell(1).getStringCellValue());
//                    pr.setInt(3, (int) row.getCell(2).getNumericCellValue());
//                    pr.setInt(4, (int) row.getCell(3).getNumericCellValue());
//                    pr.setString(5, row.getCell(4).getStringCellValue());
//                    pr.setString(6, row.getCell(5).getStringCellValue());
//                    pr.setString(7, row.getCell(6).getStringCellValue());
//                    pr.setString(8, row.getCell(7).getStringCellValue());
//                    pr.execute();
//                }
//                pr.close();
//                fis.close();
//            }
//        } catch (SQLException | IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void setCarStatus(String status, int id) {
        try {
            String sql = "UPDATE Car SET car_status = ? WHERE id_car = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, status);
            pr.setInt(2, id);
            pr.execute();
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
