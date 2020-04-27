package controllers;

import carstore.CarStore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Car;
import models.Sale;

public class SalesController implements Initializable {

    @FXML
    private Button btnBack;
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
    private TableColumn<Sale, String> colCompany;

    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtCustomer;
    @FXML
    private DatePicker dtSaleDate;
    @FXML
    private ComboBox<Car> cmbCars;

    Car car = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.table.setItems(CarStore.saleList);

        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        this.colCustomer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        this.colDate.setCellValueFactory(new PropertyValueFactory<>("saleDate"));
        this.colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        this.colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        this.colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));

        CarStore.loadSales(CarStore.currentUser.getId());
        cmbCars.setItems(CarStore.carList);
        CarStore.loadCars();

        cmbCars.valueProperty().addListener((observable, oldValue, newValue) -> {
            car = newValue;
            if (car != null) {
                setTotal();
            }
        });
        txtQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            setTotal();
        });

    }

    @FXML
    private void handleBack(ActionEvent event) {
        CarStore.change("/views/Dashboard.fxml", "Dashboard");
    }

    private void setTotal() {
        int quantity = 0, price = 0;
        try {

            txtPrice.setText(car.getPrice() + "");
            quantity = Integer.parseInt(txtQuantity.getText());
            txtTotal.setText(car.getPrice() * quantity + "");
        } catch (Exception e) {
            txtTotal.setText("0");
        }

    }

    @FXML
    private void handleAdd(ActionEvent event) {

        if (car == null) {

            return;
        }
        int price = 0, quantity = 0, carId, userId;
        String customer, saleDate;

        customer = txtCustomer.getText().trim();
        saleDate = this.dtSaleDate.getValue().toString();
        try {
            quantity = Integer.parseInt(txtQuantity.getText());
            price = Integer.parseInt(txtPrice.getText());
        } catch (Exception e) {

        }
        userId = CarStore.currentUser.getId();

        carId = car.getId();

        if (quantity <= 0 || quantity > car.getBalance()) {

            return;
        }

        CarStore.addSale(price, quantity, carId, userId, customer, saleDate);
        CarStore.loadSales(userId);

        //public static int updateCar(int id, int price, int balance, int model, String company, String kind, String color) {
        CarStore.updateCar(car.getId(), car.getPrice(), car.getBalance() - quantity, car.getModel(), car.getCompany(), car.getKind(), car.getColor());
        CarStore.loadCars();
    }

}
