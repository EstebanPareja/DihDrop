package co.edu.uniquindio.poo.dihdrop.Controller;
import co.edu.uniquindio.poo.dihdrop.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

    @FXML private AnchorPane loginPane;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label loginMessageLabel;
    @FXML private AnchorPane registerPane;
    @FXML private TextField registerNameField;
    @FXML private TextField registerEmailField;
    @FXML private TextField registerPhoneField;
    @FXML private PasswordField registerPasswordField;
    @FXML private Button registerButton;
    @FXML private Label registerMessageLabel;

    @FXML
    public void initialize() {
        loginMessageLabel.setText("");
        registerMessageLabel.setText("");
    }

    /**
     * Metodo para manejar el inicio de sesión.
     * Permite ingresar como administrador o como usuario normal.
     * @param event
     */
    @FXML
    void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("Por favor, ingrese correo y contraseña.");
            return;
        }

        Stage stageActual = (Stage) loginButton.getScene().getWindow();

        if (email.equals("admin@test.com") && password.equals("admin123")) {

            Administrador administrador = new Administrador(
                    "ADM-001",
                    "Administrador General",
                    email,
                    "555-0000"
            );

            Navegacion.abrirPanelAdministrador(stageActual, administrador);
            return;

        }

        if (email.equals("usuario@test.com") && password.equals("1234")) {

            Usuario usuarioSimulado = new Usuario(
                    "U001",
                    "Juan Pérez",
                    email,
                    "555-1234"
            );

            Navegacion.abrirDashboard(stageActual, usuarioSimulado);

        } else {
            loginMessageLabel.setText("Correo o contraseña incorrectos.");
        }
    }


    @FXML
    void handleRegister(ActionEvent event) {
        System.out.println("Intento de registro...");
        switchToLogin(event);
    }

    @FXML
    void switchToRegister(ActionEvent event) {
        loginPane.setVisible(false);
        registerPane.setVisible(true);
    }

    @FXML
    void switchToLogin(ActionEvent event) {
        registerPane.setVisible(false);
        loginPane.setVisible(true);
    }
}