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

    // --- Componentes FXML del Panel de Login ---
    @FXML private AnchorPane loginPane;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label loginMessageLabel;

    // --- Componentes FXML del Panel de Registro ---
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

    @FXML
    void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("Por favor, ingrese correo y contraseña.");
            return;
        }

        // --- Lógica de Autenticación (Simulada) ---
        if (email.equals("usuario@test.com") && password.equals("1234")) {

            // --- CAMBIO: Lógica de navegación ---
            // 1. Creamos un usuario de prueba para poder pasarlo a las otras vistas.
            Usuario usuarioSimulado = new Usuario("U001", "Juan Pérez", email, "555-1234");

            // 2. Obtenemos la ventana (Stage) actual desde cualquier componente, como el botón.
            Stage stageActual = (Stage) loginButton.getScene().getWindow();

            // 3. Usamos nuestra clase de Navegacion para abrir el dashboard.
            Navegacion.abrirDashboard(stageActual, usuarioSimulado);

        } else {
            loginMessageLabel.setText("Correo o contraseña incorrectos.");
        }
    }

    @FXML
    void handleRegister(ActionEvent event) {
        // ... (Esta lógica no necesita cambios por ahora)
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