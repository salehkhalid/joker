package controllers;

import carstore.CarStore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleLogin(ActionEvent event) {

        String email = txtEmail.getText();
        String password = txtPassword.getText();
        if (email.isEmpty() || password.isEmpty()) {
            CarStore.showError("All Fields are  required");
            return;
        }
        if (CarStore.login(email, password) == 1) {
            CarStore.change("/views/Dashboard.fxml", "Dashboard");
        } else {
            CarStore.showError("Wrong email or password");
        }

    }

}
