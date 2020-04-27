package controllers;

import carstore.CarStore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.User;

public class UsersController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private CheckBox chkBlocked;
    @FXML
    private ComboBox<String> cmbRole;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, Integer> colId;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colRole;
    @FXML
    private TableColumn<User, Boolean> colBlocked;
    @FXML
    private Button btnBack;

    private User selected = null;
    @FXML
    private TextField txtSalary;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnDelete.setDisable(true);
        this.btnUpdate.setDisable(true);
        this.colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        this.colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.colBlocked.setCellValueFactory(new PropertyValueFactory<>("blocked"));

        this.table.setItems(CarStore.userList);

        CarStore.loadUsers();
        this.table.getSelectionModel().selectedIndexProperty().addListener(x -> {
            selected = this.table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                this.btnDelete.setDisable(false);
                this.btnUpdate.setDisable(false);
                this.txtEmail.setText(selected.getEmail());
                this.txtId.setText(selected.getId() + "");
                this.txtName.setText(selected.getName());
                this.txtPassword.setText(selected.getPassword());
                this.txtSalary.setText(selected.getSalary() + "");
                this.chkBlocked.setSelected(selected.isBlocked() );
                this.cmbRole.setValue(selected.getRole());
            } else {
                this.btnDelete.setDisable(true);
                this.btnUpdate.setDisable(true);
            }
        });

        this.cmbRole.setItems(CarStore.roleList);
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        String role = cmbRole.getSelectionModel().getSelectedItem();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if (role.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            CarStore.showError("All Fields are  required");
            return;
        }
        int salary = 0;
        try{
          salary = Integer.parseInt(txtSalary.getText());
        }catch(Exception e){
           CarStore.showError( e.getMessage());
            return; 
        }
        
        if(salary<=0){
              CarStore.showError("Salary must be positive");
            return;
        }
        boolean blocked = chkBlocked.isSelected();

        CarStore.addUser(name, email, role, password, salary, blocked);
        CarStore.loadUsers();
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if(selected==null){
            return;
        }
        String role = cmbRole.getSelectionModel().getSelectedItem();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if (role.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            CarStore.showError("All Fields are  required");
            return;
        }
        int salary = 0;
        try{
          salary = Integer.parseInt(txtSalary.getText());
        }catch(Exception e){
           CarStore.showError( e.getMessage());
            return; 
        }
        
        if(salary<=0){
              CarStore.showError("Salary must be positive");
            return;
        }
        boolean blocked = chkBlocked.isSelected();

        CarStore.updateUser(selected.getId(),name, email, role, password, salary, blocked);
        CarStore.loadUsers();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
           if(selected==null){
            return;
        }
           CarStore.deleteUser(selected.getId());
         CarStore.loadUsers();
    }

    @FXML
    private void handleBack(ActionEvent event) {
        CarStore.change("/views/Dashboard.fxml", "Dashboard");
    }

}
