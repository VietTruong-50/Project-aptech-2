package com.project.project2.controller;

import com.project.project2.connection.DBHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;
import java.util.ResourceBundle;


public class ChartController implements Initializable {
    @FXML
    public LineChart<?, ?> lineChart;

    @FXML
    public PieChart pieChart;


    @FXML
    public ComboBox<?> ddMMYYCost;

    @FXML
    public CategoryAxis cateAxis;

    public AnchorPane root;

    final ObservableList<String> ddMMYY = FXCollections.observableArrayList("by Day", "by Month", "by Year");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ddMMYYCost.setValue(ddMMYY.get(0));
//        ddMMYYCost.setItems(ddMMYY);
//        try {
//            initLineChart();
//            initPieChart();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    @FXML
    public void getStatistic(ActionEvent actionEvent) throws SQLException {
        initLineChart();
        initPieChart();
    }

    public void initLineChart() throws SQLException {
        XYChart.Series series = new XYChart.Series();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        cateAxis.setAnimated(false);
        if (ddMMYYCost.getValue() == "by Day") {
            lineChart.getData().clear();
            lineChart.setTitle("Doanh thu theo ngày");
            for (int i = 1; i <= 31; i++) {
                series.getData().add(new XYChart.Data("Ngay " + i, getDailyCostByDdMmYy(i, "DAY")));
            }
            lineChart.getData().add(series);
        } else if (ddMMYYCost.getValue() == "by Month") {
            lineChart.getData().clear();
            lineChart.setTitle("Doanh thu theo tháng");
            for (int i = 1; i <= 12; i++) {
                series.getData().add(new XYChart.Data("Thang " + i, getDailyCostByDdMmYy(i, "MONTH")));
            }
            lineChart.getData().add(series);
        } else if (ddMMYYCost.getValue() == "by Year") {
            lineChart.getData().clear();
            lineChart.setTitle("Doanh thu theo năm");
            for (int i = year; i <= year + 2; i++) {
                series.getData().add(new XYChart.Data("Nam " + i, getDailyCostByDdMmYy(i, "YEAR")));
            }
            lineChart.getData().add(series);
        }
    }

    public void initPieChart() throws SQLException {
//        ObservableList<PieChart.Data> piechart = FXCollections.observableArrayList();
//
//        for(Staff staff : STAFFS){
//            piechart.add(new PieChart.Data(staff.getFull_name(), getNbContractPercent(staff.getId_staff())));
//        }
//        pieChart.setData(piechart);
    }

    public double getNbContractPercent(int id_staff) throws SQLException {
        ResultSet rs = null;
        String query = "SELECT (SELECT COUNT(*) FROM Contract WHERE id_staff = " + id_staff + ")*100/(SELECT COUNT(*) FROM Contract)";
        rs = DBHandle.executeQuery(query);
        if (rs.next()) {
            return rs.getDouble("percent_of_work");
        }
        return 0;
    }

    public int getDailyCostByDdMmYy(int number, String Type) throws SQLException {
        ResultSet rs = null;
        String query = "SELECT SUM(total_cost) AS cost_by " +
                "FROM Contract WHERE " + Type + "(startDate) = " + number + " GROUP BY " + Type + "(startDate)";
        rs = DBHandle.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("cost_by");
        }
        return 0;
    }
}
