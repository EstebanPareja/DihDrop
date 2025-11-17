package co.edu.uniquindio.poo.dihdrop.Controller;
import co.edu.uniquindio.poo.dihdrop.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ShipmentHistoryController {

    @FXML private DatePicker dateFromPicker;
    @FXML private DatePicker dateToPicker;
    @FXML private ComboBox<EstadoEnvio> statusFilterCombo;
    @FXML private TableView<Envio> historyTableView;
    @FXML private TableColumn<Envio, String> idColumn;
    @FXML private TableColumn<Envio, LocalDateTime> fechaColumn;
    @FXML private TableColumn<Envio, Direccion> origenColumn;
    @FXML private TableColumn<Envio, Direccion> destinoColumn;
    @FXML private TableColumn<Envio, Double> costoColumn;
    @FXML private TableColumn<Envio, EstadoEnvio> estadoColumn;
    @FXML private Button detailsButton;

    private Usuario usuarioActual;
    private List<Envio> masterShipmentList;

    @FXML
    public void initialize() {
        statusFilterCombo.setItems(FXCollections.observableArrayList(EstadoEnvio.values()));
        setupTableColumns();
        historyTableView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> detailsButton.setDisable(newSelection == null)
        );
    }

    public void initData(Usuario usuario) {
        this.usuarioActual = usuario;
        loadUserShipments();
    }

    private void loadUserShipments() {
        this.masterShipmentList = GestorEnvios.getInstancia().getTodosLosEnvios();
        historyTableView.setItems(FXCollections.observableArrayList(masterShipmentList));
    }

    @FXML
    void handleFiltrar(ActionEvent event) {
        LocalDate fromDate = dateFromPicker.getValue();
        LocalDate toDate = dateToPicker.getValue();
        EstadoEnvio status = statusFilterCombo.getValue();

        List<Envio> filteredList = masterShipmentList.stream()
                .filter(envio -> fromDate == null || !envio.getFechaCreacion().toLocalDate().isBefore(fromDate))
                .filter(envio -> toDate == null || !envio.getFechaCreacion().toLocalDate().isAfter(toDate))
                .filter(envio -> status == null || envio.getEstado() == status)
                .collect(Collectors.toList());

        historyTableView.setItems(FXCollections.observableArrayList(filteredList));
    }

    @FXML
    void handleLimpiarFiltros(ActionEvent event) {
        dateFromPicker.setValue(null);
        dateToPicker.setValue(null);
        statusFilterCombo.setValue(null);
        historyTableView.setItems(FXCollections.observableArrayList(masterShipmentList));
    }

    // --- CAMBIO: Lógica de navegación ---
    @FXML
    void handleVerDetalles(ActionEvent event) {
        Envio selectedEnvio = historyTableView.getSelectionModel().getSelectedItem();
        if (selectedEnvio != null) {
            // Usamos la clase de navegación para abrir la ventana de detalles.
            Navegacion.abrirDetallesEnvio(selectedEnvio);
        }
    }

    @FXML
    void handleDescargarReporte(ActionEvent event) {
        System.out.println("Descargando reporte en formato CSV...");
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        origenColumn.setCellValueFactory(new PropertyValueFactory<>("origen"));
        destinoColumn.setCellValueFactory(new PropertyValueFactory<>("destino"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory<>("estado"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        fechaColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : formatter.format(item));
            }
        });

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        costoColumn.setCellValueFactory(new PropertyValueFactory<>("costoBase"));
        costoColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : currencyFormat.format(item));
            }
        });
    }
}