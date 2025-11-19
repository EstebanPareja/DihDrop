package co.edu.uniquindio.poo.dihdrop.Controller;
import co.edu.uniquindio.poo.dihdrop.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private TableView<Envio> enviosTableView;
    @FXML private TableColumn<Envio, String> idColumn;
    @FXML private TableColumn<Envio, Direccion> destinoColumn;
    @FXML private TableColumn<Envio, EstadoEnvio> estadoColumn;
    @FXML private TableColumn<Envio, LocalDateTime> fechaColumn;

    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        fechaColumn.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : formatter.format(item));
            }
        });
    }

    public void initData(Usuario usuario) {
        this.usuarioActual = usuario;
        welcomeLabel.setText("Bienvenido, " + usuario.getNombreCompleto());
        cargarEnviosRecientes();
    }

    private void cargarEnviosRecientes() {
        GestorEnvios gestor = GestorEnvios.getInstancia();
        gestor.setTarifaStrategy(new CalculoTarifaBasico());
        System.out.println("Total de envíos vistos desde Dashboard: " + gestor.getTodosLosEnvios().size());
        if (gestor.getTodosLosEnvios().isEmpty()) {
            Direccion d1 = new Direccion("D1", "Oficina", "Calle Falsa 123", "Ciudad", "0,0");
            Direccion d2 = new Direccion("D2", "Bodega", "Avenida Siempre Viva 742", "Ciudad", "1,1");
            gestor.crearEnvio(this.usuarioActual, d1, d2, 5.0, 0.1);
            gestor.crearEnvio(this.usuarioActual, d2, d1, 2.0, 0.05);
        }

            ObservableList<Envio> envios = FXCollections.observableArrayList(gestor.getTodosLosEnvios());
            enviosTableView.setItems(envios);
    }

    @FXML
    void handleCrearEnvio(ActionEvent event) {
        Navegacion.abrirCrearEnvio(this.usuarioActual);

        cargarEnviosRecientes();
    }

    @FXML
    void handleGestionarPerfil(ActionEvent event) {
        Navegacion.abrirPerfil(this.usuarioActual);
        welcomeLabel.setText("Bienvenido, " + usuarioActual.getNombreCompleto());
    }

    @FXML
    void handleCerrarSesion(ActionEvent event) {
        Stage stageActual = (Stage) welcomeLabel.getScene().getWindow();
        Navegacion.abrirLogin(stageActual);
    }
}