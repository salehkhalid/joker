package controllers;

import carstore.CarStore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Car;

public class CarsController implements Initializable {

    @FXML
    private Button btnBack;
    @FXML
    private TableView<Car> table;
    @FXML
    private TableColumn<Car, Integer> colId;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtCompany;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableColumn<Car, String> colCompany;
    @FXML
    private TableColumn<Car, String> colKind;
    @FXML
    private TableColumn<Car, String> colColor;
    @FXML
    private TableColumn<Car, Integer> colModel;
    @FXML
    private TableColumn<Car, Integer> colPrice;
    @FXML
    private TableColumn<Car, Integer> colBalance;
    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtBalance;
    @FXML
    private ComboBox<String> cmbColor;
    @FXML
    private ComboBox<String> cmbKind;

    Car selected = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnDelete.setDisable(true);
        this.btnUpdate.setDisable(true);

        cmbColor.setItems(CarStore.colorList);
        cmbKind.setItems(CarStore.kindList);

        this.table.setItems(CarStore.carList);
        this.btnDelete.setDisable(true);
        this.btnUpdate.setDisable(true);
        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        this.colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        this.colKind.setCellValueFactory(new PropertyValueFactory<>("kind"));
        this.colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        this.colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        this.colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        CarStore.loadCars();
        this.table.getSelectionModel().selectedIndexProperty().addListener(x -> {
            selected = this.table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                this.btnDelete.setDisable(false);
                this.btnUpdate.setDisable(false);
                this.txtCompany.setText(selected.getCompany());
                this.txtId.setText(selected.getId() + "");
                this.txtPrice.setText(selected.getPrice() + "");
                this.txtBalance.setText(selected.getBalance() + "");
                this.txtModel.setText(selected.getModel() + "");
                this.cmbKind.setValue(selected.getKind());
                this.cmbColor.setValue(selected.getColor());
            } else {
                this.btnDelete.setDisable(true);
                this.btnUpdate.setDisable(true);
            }
        });

    }

    @FXML
    private void handleBack(ActionEvent event) {
        CarStore.change("/views/Dashboard.fxml", "Dashboard");
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        int price, balance, model;
        String company, kind, color;
        company = txtCompany.getText().trim();
        kind = cmbKind.getValue();
        color = cmbColor.getValue();
        if (company.isEmpty() || kind == null || color == null) {
            CarStore.showError("All Fields are required");
            return;
        }
        try {
            price = Integer.parseInt(txtPrice.getText());
            balance = Integer.parseInt(txtBalance.getText());
            model = Integer.parseInt(txtModel.getText());
        } catch (Exception e) {
            CarStore.showError("Price,Balance and Model Fields must be integer");
            return;

        }
        if (price <= 0 || balance < 0 || model <= 0) {
            CarStore.showError("Price,Balance and Model Fields must be integer");
            return;
        }
        CarStore.addCar(price, balance, model, company, kind, color);
        CarStore.loadCars();
        this.table.refresh();

    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if(selected==null){
             CarStore.showError("Please choose a car");
           
            return;
        }
        int price, balance, model;
        String company, kind, color;
        company = txtCompany.getText().trim();
        kind = cmbKind.getValue();
        color = cmbColor.getValue();
        if (company.isEmpty() || kind == null || color == null) {
            CarStore.showError("All Fields are required");
            return;
        }
        try {
            price = Integer.parseInt(txtPrice.getText());
            balance = Integer.parseInt(txtBalance.getText());
            model = Integer.parseInt(txtModel.getText());
        } catch (Exception e) {
            CarStore.showError("Price,Balance and Model Fields must be integer");
            return;

        }
        if (price <= 0 || balance < 0 || model <= 0) {
            CarStore.showError("Price,Balance and Model Fields must be integer");
            return;
        }
        CarStore.updateCar(selected.getId(), price, balance, model, company, kind, color);
        CarStore.loadCars();
        this.table.refresh();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
         if(selected==null){
             CarStore.showError("Please choose a car");
           
            return;
        }
           CarStore.deleteCar(selected.getId());
        CarStore.loadCars();
        this.table.refresh();
         
    }

}
