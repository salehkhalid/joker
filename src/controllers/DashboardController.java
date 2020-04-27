package controllers;

import carstore.CarStore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class DashboardController implements Initializable {

    @FXML
    private Button btnSales;
    @FXML
    private Button btnCars;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnUsers;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Button btnLogOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (CarStore.currentUser.getRole().equals("admin")) {
            btnReports.setVisible(true);
            btnCars.setVisible(true);
            btnSales.setVisible(true);
            btnUsers.setVisible(true);
        } else {
            btnReports.setVisible(false);
            btnCars.setVisible(false);
            btnSales.setVisible(true);
            btnUsers.setVisible(false);
        }

        CarStore.loadEmployeesSales();
    
        xAxis.setCategories(CarStore.employeeList);
        xAxis.setLabel("Employees");
        yAxis.setLabel("Sales");

        //Creating the Bar chart
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Car");

        for ( int i=0;i< CarStore.employeeSaleList.size();i++ ) {
           
            series1.getData().add(new XYChart.Data<>(CarStore.employeeList.get(i), CarStore.employeeSaleList.get(i)));
        }
 
        barChart.getData().addAll(series1);

    }

    @FXML
    private void handleSales(ActionEvent event) {
        CarStore.change("/views/Sales.fxml", "Sales");
    }

    @FXML
    private void handleCars(ActionEvent event) {
        CarStore.change("/views/Cars.fxml", "Cars");
    }

    @FXML
    private void handleReports(ActionEvent event) {
        CarStore.change("/views/Reports.fxml", "Reports");
    }

    @FXML
    private void handleUsers(ActionEvent event) {
        CarStore.change("/views/Users.fxml", "Users");
    }

    @FXML
    private void handleLogOut(ActionEvent event) {
        CarStore.currentUser=null;
          CarStore.change("/views/Login.fxml", "Login");
    }

}
