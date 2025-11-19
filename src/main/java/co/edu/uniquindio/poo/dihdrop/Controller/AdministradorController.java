package co.edu.uniquindio.poo.dihdrop.Controller;

import co.edu.uniquindio.poo.dihdrop.Model.Administrador;
import co.edu.uniquindio.poo.dihdrop.Model.EstadoDisponibilidadRepartidor;
import co.edu.uniquindio.poo.dihdrop.Model.ReportesFacade;
import java.util.Map;
import javafx.scene.control.Alert;
import co.edu.uniquindio.poo.dihdrop.Model.Envio;
import co.edu.uniquindio.poo.dihdrop.Model.EstadoEnvio;
import co.edu.uniquindio.poo.dihdrop.Model.GestorEnvios;
import co.edu.uniquindio.poo.dihdrop.Model.Repartidor;
import co.edu.uniquindio.poo.dihdrop.Model.Usuario;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class AdministradorController {

    private final ReportesFacade reportesFacade = new ReportesFacade();
    @FXML
    private Label adminNombreLabel;
    @FXML
    private TableView<Usuario> usuariosTableView;
    @FXML
    private TableColumn<Usuario, String> usuarioIdColumn;
    @FXML
    private TableColumn<Usuario, String> usuarioNombreColumn;
    @FXML
    private TableColumn<Usuario, String> usuarioCorreoColumn;
    @FXML
    private TableColumn<Usuario, String> usuarioTelefonoColumn;
    @FXML
    private TableView<Repartidor> repartidoresTableView;
    @FXML
    private TableColumn<Repartidor, String> repartidorIdColumn;
    @FXML
    private TableColumn<Repartidor, String> repartidorNombreColumn;
    @FXML
    private TableColumn<Repartidor, String> repartidorDocumentoColumn;
    @FXML
    private TableColumn<Repartidor, String> repartidorTelefonoColumn;
    @FXML
    private TableColumn<Repartidor, EstadoDisponibilidadRepartidor> repartidorEstadoColumn;
    @FXML
    private TableColumn<Repartidor, String> repartidorZonaColumn;
    @FXML
    private TableView<Envio> enviosTableView;
    @FXML
    private TableColumn<Envio, String> envioIdColumn;
    @FXML
    private TableColumn<Envio, EstadoEnvio> envioEstadoColumn;
    @FXML
    private TableColumn<Envio, String> envioUsuarioColumn;
    @FXML
    private ComboBox<EstadoEnvio> envioEstadoCombo;
    @FXML
    private Label envioMensajeLabel;
    @FXML
    private TextField usuarioIdField;
    @FXML
    private TextField usuarioNombreField;
    @FXML
    private TextField usuarioCorreoField;
    @FXML
    private TextField usuarioTelefonoField;
    @FXML
    private Label usuarioMensajeLabel;
    @FXML
    private TextField repartidorIdField;
    @FXML
    private TextField repartidorNombreField;
    @FXML
    private TextField repartidorDocumentoField;
    @FXML
    private TextField repartidorTelefonoField;
    @FXML
    private TextField repartidorZonaField;
    @FXML
    private ComboBox<EstadoDisponibilidadRepartidor> repartidorEstadoCombo;
    @FXML
    private Label repartidorMensajeLabel;

    private Administrador administradorActual;
    private ObservableList<Usuario> usuariosObservable;
    private ObservableList<Repartidor> repartidoresObservable;
    private ObservableList<Envio> enviosObservable;


    @FXML
    public void initialize() {

        usuarioIdColumn.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        usuarioNombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        usuarioCorreoColumn.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        usuarioTelefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        repartidorIdColumn.setCellValueFactory(new PropertyValueFactory<>("idRepartidor"));
        repartidorNombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        repartidorDocumentoColumn.setCellValueFactory(new PropertyValueFactory<>("documento"));
        repartidorTelefonoColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        repartidorEstadoColumn.setCellValueFactory(new PropertyValueFactory<>("disponibilidad"));
        repartidorZonaColumn.setCellValueFactory(new PropertyValueFactory<>("zonaCobertura"));
        envioIdColumn.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        envioEstadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));
        envioUsuarioColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(
                        cellData.getValue().getUsuario() != null
                                ? cellData.getValue().getUsuario().getNombreCompleto()
                                : "Sin usuario"
                )
        );
        usuariosObservable = FXCollections.observableArrayList();
        repartidoresObservable = FXCollections.observableArrayList();
        enviosObservable = FXCollections.observableArrayList();

        usuariosTableView.setItems(usuariosObservable);
        repartidoresTableView.setItems(repartidoresObservable);
        enviosTableView.setItems(enviosObservable);
        repartidorEstadoCombo.setItems(FXCollections.observableArrayList(EstadoDisponibilidadRepartidor.values()));
        usuarioMensajeLabel.setText("");
        repartidorMensajeLabel.setText("");
        envioEstadoCombo.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));
        if (envioMensajeLabel != null) {
            envioMensajeLabel.setText("");
        }
    }

    /**
     * Metodo para inicializar los datos del administrador
     * @param administrador
     */
    public void initData(Administrador administrador) {
        this.administradorActual = administrador;
        adminNombreLabel.setText("Administrador: " + administrador.getNombreCompleto());

        List<Usuario> usuarios = administrador.getListaUsuarios();
        List<Repartidor> repartidores = administrador.getListaRepartidores();
        List<Envio> envios = GestorEnvios.getInstancia().getTodosLosEnvios();

        usuariosObservable.setAll(usuarios);
        repartidoresObservable.setAll(repartidores);
        enviosObservable.setAll(envios);
    }

    /**
     * Metodo para manejar la creación de un nuevo usuario
     * @param event
     */
    @FXML
    public void handleCrearUsuario(ActionEvent event) {
        String id = usuarioIdField.getText();
        String nombre = usuarioNombreField.getText();
        String correo = usuarioCorreoField.getText();
        String telefono = usuarioTelefonoField.getText();

        if (id.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            usuarioMensajeLabel.setText("Complete todos los campos del usuario.");
            return;
        }

        Usuario nuevo = new Usuario(id, nombre, correo, telefono);
        boolean agregado = administradorActual.registrarUsuario(nuevo);

        if (agregado) {
            usuariosObservable.add(nuevo);
            usuarioMensajeLabel.setText("Usuario registrado correctamente.");
            usuarioIdField.clear();
            usuarioNombreField.clear();
            usuarioCorreoField.clear();
            usuarioTelefonoField.clear();
        } else {
            usuarioMensajeLabel.setText("Ya existe un usuario con ese ID.");
        }
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
     * Metodo para manejar la eliminación del usuario seleccionado
     * @param event
     */
    @FXML
    public void handleEliminarUsuario(ActionEvent event) {
        Usuario seleccionado = usuariosTableView.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            usuarioMensajeLabel.setText("Seleccione un usuario para eliminar.");
            return;
        }

        boolean eliminado = administradorActual.eliminarUsuarioPorId(seleccionado.getIdUsuario());
        if (eliminado) {
            usuariosObservable.remove(seleccionado);
            usuarioMensajeLabel.setText("Usuario eliminado.");
        } else {
            usuarioMensajeLabel.setText("No se pudo eliminar el usuario.");
        }
    }

    /**
     * Metodo para manejar la creación de un nuevo repartidor
     * @param event
     */
    @FXML
    public void handleCrearRepartidor(ActionEvent event) {
        String id = repartidorIdField.getText();
        String nombre = repartidorNombreField.getText();
        String documento = repartidorDocumentoField.getText();
        String telefono = repartidorTelefonoField.getText();
        String zona = repartidorZonaField.getText();
        EstadoDisponibilidadRepartidor estado = repartidorEstadoCombo.getValue();

        if (id.isEmpty() || nombre.isEmpty() || documento.isEmpty() ||
                telefono.isEmpty() || zona.isEmpty() || estado == null) {
            repartidorMensajeLabel.setText("Complete todos los campos del repartidor.");
            return;
        }

        Repartidor nuevo = new Repartidor(id, nombre, documento, telefono, zona);
        nuevo.cambiarDisponibilidad(estado);

        boolean agregado = administradorActual.registrarRepartidor(nuevo);
        if (agregado) {
            repartidoresObservable.add(nuevo);
            repartidorMensajeLabel.setText("Repartidor registrado correctamente.");
            repartidorIdField.clear();
            repartidorNombreField.clear();
            repartidorDocumentoField.clear();
            repartidorTelefonoField.clear();
            repartidorZonaField.clear();
            repartidorEstadoCombo.getSelectionModel().clearSelection();
        } else {
            repartidorMensajeLabel.setText("Ya existe un repartidor con ese ID.");
        }
    }

    /**
     * Metodo para manejar el cambio de disponibilidad del repartidor seleccionado
     * @param event
     */
    @FXML
    public void handleCambiarDisponibilidad(ActionEvent event) {
        Repartidor seleccionado = repartidoresTableView.getSelectionModel().getSelectedItem();
        EstadoDisponibilidadRepartidor nuevoEstado = repartidorEstadoCombo.getValue();

        if (seleccionado == null) {
            repartidorMensajeLabel.setText("Seleccione un repartidor.");
            return;
        }
        if (nuevoEstado == null) {
            repartidorMensajeLabel.setText("Seleccione un estado.");
            return;
        }

        boolean actualizado = administradorActual.actualizarDisponibilidadRepartidor(
                seleccionado.getIdRepartidor(), nuevoEstado
        );

        if (actualizado) {
            repartidoresTableView.refresh();
            repartidorMensajeLabel.setText("Disponibilidad actualizada.");
        } else {
            repartidorMensajeLabel.setText("No se pudo actualizar la disponibilidad.");
        }
    }

    /**
     * Metodo para recargar la lista de envíos desde el GestorEnvios
     * @param event
     */
    @FXML
    public void handleRefrescarEnvios(ActionEvent event) {
        List<Envio> envios = GestorEnvios.getInstancia().getTodosLosEnvios();
        enviosObservable.setAll(envios);
    }
    /**
     * Metodo para cambiar el estado del envío seleccionado en la tabla.
     * @param event
     */
    @FXML
    public void handleCambiarEstadoEnvio(ActionEvent event) {
        Envio seleccionado = enviosTableView.getSelectionModel().getSelectedItem();
        EstadoEnvio nuevoEstado = envioEstadoCombo.getValue();
        if (seleccionado == null) {
            envioMensajeLabel.setText("Seleccione un envío de la tabla.");
            return;
        }
        if (nuevoEstado == null) {
            envioMensajeLabel.setText("Seleccione un estado en el combo.");
            return;
        }
        GestorEnvios gestor = GestorEnvios.getInstancia();
        gestor.actualizarEstadoEnvio(seleccionado.getIdEnvio(), nuevoEstado);
        enviosObservable.setAll(gestor.getTodosLosEnvios());
        enviosTableView.refresh();

        envioMensajeLabel.setText("Estado actualizado a: " + nuevoEstado.getDescripcion());
    }

    /**
     * Metodo para mostrar un reporte básico usando el ReportesFacade.
     */
    @FXML
    public void handleVerReportes(ActionEvent event) {
        long entregados = reportesFacade.contarEnviosEntregados();
        double ingresoTotal = reportesFacade.calcularIngresoTotal();
        Map<String, Long> incidenciasCiudad = reportesFacade.incidenciasPorCiudad();

        StringBuilder contenido = new StringBuilder();
        contenido.append("Envíos entregados: ").append(entregados).append("\n");
        contenido.append("Ingreso total: $").append(String.format("%.2f", ingresoTotal)).append("\n");
        contenido.append("Incidencias por ciudad:\n");

        if (incidenciasCiudad.isEmpty()) {
            contenido.append("  - No hay incidencias registradas.\n");
        } else {
            incidenciasCiudad.forEach((ciudad, cantidad) ->
                    contenido.append("  - ").append(ciudad).append(": ").append(cantidad).append("\n")
            );
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reporte de envíos");
        alert.setHeaderText("Resumen general de la operación");
        alert.setContentText(contenido.toString());
        alert.showAndWait();
    }


    /**
     * Metodo para cerrar sesión y volver a la vista de Login
     * @param event
     */
    @FXML
    public void handleCerrarSesion(ActionEvent event) {
        Stage stageActual = (Stage) adminNombreLabel.getScene().getWindow();
        Navegacion.abrirLogin(stageActual);
    }
}
