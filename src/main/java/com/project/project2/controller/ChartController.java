package com.project.project2.controller;

import com.project.project2.connection.DBHandle;
import com.project.project2.model.Staff;
import com.project.project2.service.impl.ImplStaff;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.service.IStaff.STAFF_LIST;


public class ChartController implements Initializable {
    private int total_contract = 0;

    @FXML
    public LineChart<?, ?> lineChart;

    @FXML
    public PieChart pieChart;

    @FXML
    public ComboBox<String> ddMMYYCost;

    @FXML
    public CategoryAxis cateAxis;

    public AnchorPane root;

    final ObservableList<String> ddMMYY = FXCollections.observableArrayList("by Day", "by Month", "by Year");

    private final ImplStaff implStaff = new ImplStaff();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ddMMYYCost.setValue(ddMMYY.get(0));
        ddMMYYCost.setItems(ddMMYY);
        try {
            initLineChart();
            initPieChart();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        Date date = new Date();
        LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = currentDate.getMonthValue();
        cateAxis.setAnimated(false);
        if (Objects.equals(ddMMYYCost.getValue(), "by Day")) {
            lineChart.getData().clear();
            lineChart.setTitle("Revenue by day");
            for (int i = 1; i <= 31; i++) {
                series.getData().add(new XYChart.Data("Day " + i, getDailyCostByDdMmYy(i, "DAY", month)));
            }
            lineChart.getData().add(series);
        } else if (Objects.equals(ddMMYYCost.getValue(), "by Month")) {
            lineChart.getData().clear();
            lineChart.setTitle("Revenue by month");
            for (int i = 1; i <= 12; i++) {
                series.getData().add(new XYChart.Data("Month " + i, getDailyCostByDdMmYy(i, "MONTH", 0)));
            }
            lineChart.getData().add(series);
        } else if (Objects.equals(ddMMYYCost.getValue(), "by Year")) {
            lineChart.getData().clear();
            lineChart.setTitle("Revenue by year");
            for (int i = year; i <= year + 3; i++) {
                series.getData().add(new XYChart.Data("Year " + i, getDailyCostByDdMmYy(i, "YEAR", 0)));
            }
            lineChart.getData().add(series);
        }
    }

    public void initPieChart() throws SQLException {
        STAFF_LIST.clear();
        implStaff.findAll();

        String query = "SELECT COUNT(*) AS total_contract FROM Contract";
        ResultSet rs = DBHandle.executeQuery(query);
        while (rs.next()) {
            total_contract += rs.getInt("total_contract");
        }
        ObservableList<PieChart.Data> piechart = FXCollections.observableArrayList();

        for(Staff staff : STAFF_LIST){
            piechart.add(new PieChart.Data(staff.getFull_name(), getNbContractPercent(staff.getId_staff())));
        }
        pieChart.setData(piechart);
    }

    public double getNbContractPercent(int id_staff) throws SQLException {
        ResultSet rs = null;
        String query = "SELECT COUNT(*) AS nb_Contract FROM Contract  WHERE id_staff = " + id_staff + " GROUP BY id_staff";
        rs = DBHandle.executeQuery(query);
        if(rs.next()){
            return rs.getDouble("nb_Contract") * 100 / total_contract;
        }
        return 0;
    }

    public int getDailyCostByDdMmYy(int number, String Type, int month) throws SQLException {
        ResultSet rs;
        String query = "SELECT SUM(total_cost) AS cost_by " +
                "FROM Contract WHERE " + Type + "(startDate) = " + number + " GROUP BY " + Type + "(startDate)";

        if(month != 0){
            query = "SELECT SUM(total_cost) AS cost_by " +
                    "FROM Contract WHERE " + Type + "(startDate) = " + number + " AND MONTH(startDate) = "
                    + month +  " GROUP BY " + Type + "(startDate)";
        }
        rs = DBHandle.executeQuery(query);
        if (rs.next()) {
            return rs.getInt("cost_by");
        }
        return 0;
    }
}
