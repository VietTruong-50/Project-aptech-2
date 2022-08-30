package com.project.project2.controller;

import com.pdfjet.*;
import com.project.project2.model.Contract;
import com.project.project2.service.impl.ImplCar;
import com.project.project2.service.impl.ImplContract;
import com.project.project2.service.impl.ImplContractDetail;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.project.project2.alert.AlertMaker.showConfirmation;
import static com.project.project2.service.IContract.CONTRACTS;

public class ContractController implements Initializable {
    public AnchorPane root;
    public TableView<com.project.project2.model.Contract> contractTable;
    public TableColumn<Object, Object> idColumn;
    public TableColumn<Object, Object> cusNameColumn;
    public TableColumn<Object, Object> staffNameColumn;
    public TableColumn<Object, Object> ttCostColumn;
    public TableColumn<Object, Object> startDateColumn;
    public TableColumn<Object, Object> endDateColumn;
    public TextField searchTf;

    private final ImplContract implContract = new ImplContract();
    private final ImplContractDetail implContractDetail = new ImplContractDetail();
    private final ImplCar implCar = new ImplCar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAddForm(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractDetail.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void showEditForm(ActionEvent actionEvent) throws IOException, SQLException {
        Contract contract = contractTable.getSelectionModel().getSelectedItem();

        if(contract != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("/com/project/project2/ContractDetail.fxml")));
            AnchorPane dashboard = loader.load();
            ContractDetailController contractDetailController = loader.getController();
            contractDetailController.viewContractDetail(contract);
            contractDetailController.contract = contract;
            contractDetailController.isEditForm = true;
            root.getChildren().setAll(dashboard);
        }
    }

    public void delContract(ActionEvent actionEvent) throws SQLException {
        Contract contract = contractTable.getSelectionModel().getSelectedItem();
        if (contract != null) {
            if (showConfirmation("contract").get() == ButtonType.OK) {
                if (setCarStatus(contract.getId_contract())) {
                    if (implContractDetail.deleteContractDetail(contract.getId_contract())) {
                        implContract.deleteContract(contract.getId_contract());
                        refresh();
                    }
                }
            }
        }
    }

    public boolean setCarStatus(int idContract) {
        try {
            List<Integer> list = implContractDetail.findIdCarByIdContract(idContract);
            for (int i : list) {
                implCar.setCarStatus("Available", i);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void refresh() throws SQLException {
        CONTRACTS.clear();
        showContract(false);
    }

    public void updateReturnDate(ActionEvent actionEvent) {
    }

    public void setGoBackBtn(ActionEvent actionEvent) throws IOException {
        AnchorPane dashboard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/project/project2/MainDashboard.fxml")));
        root.getChildren().setAll(dashboard);
    }

    public void showContract(boolean isUnsigned) throws SQLException {

        if(isUnsigned){
            implContract.findAll(true);
        }else{
            implContract.findAll(false);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_contract"));
        cusNameColumn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        staffNameColumn.setCellValueFactory(new PropertyValueFactory<>("staff_name"));
        ttCostColumn.setCellValueFactory(new PropertyValueFactory<>("total_cost"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        contractTable.setItems(CONTRACTS);
        searchContract(searchTf.textProperty(), contractTable);
    }

    private void searchContract(StringProperty txtFind, TableView<Contract> contractTable) {
        FilteredList<Contract> filteredData = new FilteredList<>(CONTRACTS, p -> true);
        txtFind.addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(contract -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (contract.getCustomer_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (contract.getStaff_name().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else return String.valueOf(contract.getTotal_cost()).contains(lowerCaseFilter);
            });
        });

        SortedList<Contract> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(contractTable.comparatorProperty());
        contractTable.setItems(sortedData);
    }

    public void exportPDF(ActionEvent actionEvent) throws SQLException {
        FileChooser fc = new FileChooser();
        Stage primaryStage = new Stage();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF File", "*.pdf"));
        fc.setTitle("SAVE TO PDF");
        fc.setInitialFileName("DanhSachHopDong.pdf");
//				create the pdf and page
        File out = fc.showSaveDialog(primaryStage);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(out);
            PDF pdf = new PDF(fos);
            Page page = new Page(pdf, A4.LANDSCAPE);
//					font for the table heading
            Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
//					font for the pdf table data
            Font f2 = new Font(pdf, CoreFont.HELVETICA);

            Table table = new Table();
            List<List<Cell>> tableData = new ArrayList<>();

//					table row
            List<Cell> tableRow = new ArrayList<>();

//					create the headers and add them to the table row
            Cell cell = new Cell(f1, "ID");
            tableRow.add(cell);

            cell = new Cell(f1, "Customer");
            tableRow.add(cell);

            cell = new Cell(f1);
            TextBlock textBlock = new TextBlock(f1, "Staff");
            cell.setTextBlock(textBlock);
            tableRow.add(cell);

            cell = new Cell(f1, "Total cost");
            tableRow.add(cell);

            cell = new Cell(f1);
            textBlock = new TextBlock(f1, "VAT");
            cell.setTextBlock(textBlock);
            tableRow.add(cell);

            cell = new Cell(f1);
            textBlock = new TextBlock(f1, "Start date");
            cell.setTextBlock(textBlock);
            tableRow.add(cell);

            cell = new Cell(f1);
            textBlock = new TextBlock(f1, "End date");
            cell.setTextBlock(textBlock);
            tableRow.add(cell);

            cell = new Cell(f1, "Deposit");
            tableRow.add(cell);


            tableData.add(tableRow);

//					create a row for each and add row to table row
            CONTRACTS.clear();
            showContract(true);
            ObservableList<Contract> items = contractTable.getItems();
            for (Contract cont : items) {
                Cell idHD = new Cell(f2, String.valueOf(cont.getId_contract()));
                idHD.setPadding(4);
                Cell customerName = new Cell(f2, cont.getCustomer_name());
                customerName.setPadding(4);
                Cell staffName = new Cell(f2, cont.getStaff_name());
                staffName.setPadding(4);
                Cell ttCost = new Cell(f2, String.valueOf(cont.getTotal_cost()));
                ttCost.setPadding(4);
                Cell vat = new Cell(f2, cont.getVAT() + "%");
                vat.setPadding(4);
                Cell rentDay = new Cell(f2, cont.getStartDate().toString());
                rentDay.setPadding(4);
                Cell endDay = new Cell(f2, cont.getEndDate().toString());
                endDay.setPadding(4);
                Cell deposit = new Cell(f2, String.valueOf(cont.getDeposit()));
                deposit.setPadding(4);
                Cell returnDay = null;
//                if(cont.get() == null){
//                    returnDay = new Cell(f2, null);
//                }else{
//                    returnDay = new Cell(f2, cont.getNgay_tx().toString());
//                    returnDay.setPadding(4);
//                }

                tableRow = new ArrayList<Cell>();
                tableRow.add(idHD); tableRow.add(customerName); tableRow.add(staffName);
                tableRow.add(ttCost); tableRow.add(vat); tableRow.add(rentDay); tableRow.add(endDay);
                tableRow.add(deposit);
//						add row to table
                tableData.add(tableRow);
            }

            table.setData(tableData);
            table.setPosition(40f, 50f);
            table.setColumnWidth(0, 30f);
            table.setColumnWidth(1, 120f);
            table.setColumnWidth(2, 115f);
            table.setColumnWidth(3, 110f);
            table.setColumnWidth(4, 80f);
            table.setColumnWidth(5, 100f);
            table.setColumnWidth(6, 100f);
            table.setColumnWidth(7, 100f);
//					create a new page and add more table data to the bottom of the current page
            while (true) {
                table.drawOn(page);
                if (!table.hasMoreData()) {
                    table.resetRenderedPagesCount();
                    break;
                }
                page = new Page(pdf, A3.LANDSCAPE);
            }
            pdf.close();
            fos.flush();
            System.out.println("Saved to " + out.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();
    }
}
