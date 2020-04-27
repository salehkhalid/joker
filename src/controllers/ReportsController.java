package controllers;

import carstore.CarStore;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Sale;

public class ReportsController implements Initializable {

    @FXML
    private TableView<Sale> table;
    @FXML
    private TableColumn<Sale, Integer> colId;
    @FXML
    private TableColumn<Sale, String> colCustomer;
    @FXML
    private TableColumn<Sale, String> colDate;
    @FXML
    private TableColumn<Sale, Integer> colQuantity;
    @FXML
    private TableColumn<Sale, Integer> colPrice;
    @FXML
    private TableColumn<Sale, Integer> colTotal;
    @FXML
    private TableColumn<Sale, String> colEmployee;
    @FXML
    private TextField txtTotal;
    @FXML
    private Button btnReport;
    @FXML
    private DatePicker dpTo;
    @FXML
    private Button btnBack;
    @FXML
    private DatePicker dpFrom;
    @FXML
    private TableColumn<Sale, String> colCompany;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        this.colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        this.colDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        this.colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        this.colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        this.colEmployee.setCellValueFactory(new PropertyValueFactory<>("employee"));
this.table.setItems(CarStore.saleList);
    }

    @FXML
    private void handleBack(ActionEvent event) {
          CarStore.change("/views/Dashboard.fxml", "Dashboard");
    }

    @FXML
    private void handleReport(ActionEvent event) {

        if(dpFrom.getValue()==null){
            CarStore.showError("Choose First Date");
            return;
        }
        if(dpTo.getValue()==null){
            CarStore.showError("Choose Second Date");
            return;
        }
        String fromDate, toDate;
        fromDate = dpFrom.getValue().format(DateTimeFormatter.ISO_DATE);
        toDate = dpTo.getValue().format(DateTimeFormatter.ISO_DATE);
        CarStore.loadSales(fromDate, toDate);
        this.table.refresh();
        int total=0;
        for(Sale s:CarStore.saleList){
            total+=s.getTotal();
        }
txtTotal.setText(total+"");
    }

}
