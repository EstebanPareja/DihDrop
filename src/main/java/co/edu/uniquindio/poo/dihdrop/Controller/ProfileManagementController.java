package co.edu.uniquindio.poo.dihdrop.Controller;
import co.edu.uniquindio.poo.dihdrop.Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.UUID;

public class ProfileManagementController {

    @FXML private TextField nombreField;
    @FXML private TextField correoField;
    @FXML private TextField telefonoField;
    @FXML private ListView<Direccion> direccionesListView;
    @FXML private Button editarDireccionButton;
    @FXML private Button eliminarDireccionButton;
    @FXML private Label messageLabel;

    private Usuario usuarioActual;

    @FXML
    public void initialize() {
        direccionesListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    boolean isItemSelected = newSelection != null;
                    editarDireccionButton.setDisable(!isItemSelected);
                    eliminarDireccionButton.setDisable(!isItemSelected);
                }
        );
    }

    /**
     * Inicializa la vista con los datos del usuario.
     */
    public void initData(Usuario usuario) {
        this.usuarioActual = usuario;
        populateUserData();
        populateDireccionesList();
    }

    /**
     * Rellena los campos del formulario con los datos del usuario.
     */
    private void populateUserData() {
        nombreField.setText(usuarioActual.getNombreCompleto());
        correoField.setText(usuarioActual.getCorreoElectronico());
        telefonoField.setText(usuarioActual.getTelefono());
    }

    /**
     * Rellena la ListView con las direcciones frecuentes del usuario.
     */
    private void populateDireccionesList() {
        direccionesListView.setItems(FXCollections.observableArrayList(usuarioActual.getDireccionesFrecuentes()));
    }

    /**
     * Guarda los cambios realizados en la información personal del usuario.
     */
    @FXML
    void handleGuardarCambios(ActionEvent event) {
        if (nombreField.getText().isEmpty() || correoField.getText().isEmpty() || telefonoField.getText().isEmpty()) {
            messageLabel.setText("Todos los campos personales son obligatorios.");
            return;
        }

        usuarioActual.actualizarPerfil(
                nombreField.getText(),
                correoField.getText(),
                telefonoField.getText()
        );

        messageLabel.setText("¡Información personal actualizada con éxito!");
        System.out.println("Perfil actualizado para: " + usuarioActual.getNombreCompleto());
    }

    /**
     * Abre un diálogo para añadir una nueva dirección.
     */
    @FXML
    void handleAnadirDireccion(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("Casa");
        dialog.setTitle("Añadir Nueva Dirección");
        dialog.setHeaderText("Añade un alias y los detalles de tu nueva dirección.");
        dialog.setContentText("Alias (ej: Casa, Oficina):");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(alias -> {
            String calle = "Calle Nueva " + (int)(Math.random()*100);
            String ciudad = "Ciudad Principal";
            String id = "DIR-" + UUID.randomUUID().toString().substring(0, 4);

            Direccion nuevaDireccion = new Direccion(id, alias, calle, ciudad, "0,0");
            usuarioActual.agregarDireccion(nuevaDireccion);

            populateDireccionesList();
            messageLabel.setText("Dirección '" + alias + "' añadida.");
        });
    }

    /**
     * Permite editar la dirección seleccionada.
     */
    @FXML
    void handleEditarDireccion(ActionEvent event) {
        Direccion seleccionada = direccionesListView.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;


        TextInputDialog dialog = new TextInputDialog(seleccionada.getAlias());
        dialog.setTitle("Editar Dirección");
        dialog.setContentText("Nuevo alias:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(nuevoAlias -> {
            seleccionada.setAlias(nuevoAlias);
            populateDireccionesList();
            messageLabel.setText("Dirección actualizada.");
        });
    }

    /**
     * Metodo para volver al login desde cualquier vista
     * @param event evento de la interfaz
     */
    @FXML
    public void handleAtras(ActionEvent event) {
        Stage stageActual = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Navegacion.abrirLogin(stageActual);
    }


    /**
     * Elimina la dirección seleccionada de la lista.
     */
    @FXML
    void handleEliminarDireccion(ActionEvent event) {
        Direccion seleccionada = direccionesListView.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar esta dirección?");
        alert.setContentText(seleccionada.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            usuarioActual.eliminarDireccion(seleccionada.getIdDireccion());
            populateDireccionesList();
            messageLabel.setText("Dirección eliminada.");
        }
    }
}