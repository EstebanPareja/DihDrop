package co.edu.uniquindio.poo.dihdrop.Controller;
import co.edu.uniquindio.poo.dihdrop.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ShipmentDetailsController {

    @FXML private Label idLabel;
    @FXML private Label estadoLabel;
    @FXML private Label fechaCreacionLabel;
    @FXML private Label repartidorLabel;
    @FXML private Label fechaEstimadaLabel;
    @FXML private Label origenLabel;
    @FXML private Label destinoLabel;
    @FXML private Label paqueteLabel;
    @FXML private TextArea costosArea;
    @FXML private Button cerrarButton;
    private Envio envio;


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    /**
     * El método principal para inicializar la vista con los datos de un envío específico.
     * Este método debe ser llamado desde el controlador anterior (ShipmentHistoryController).
     * @param envio El objeto Envio cuyos detalles se van a mostrar.
     */
    public void initData(Envio envio) {
        this.envio = envio;
        if (envio == null) {
            return;
        }

        // Poblar la información principal
        idLabel.setText(envio.getIdEnvio());
        estadoLabel.setText(envio.getEstado().getDescripcion());
        fechaCreacionLabel.setText(formatDate(envio.getFechaCreacion()));
        fechaEstimadaLabel.setText(formatDate(envio.getFechaEstimadaEntrega()));

        // Manejar el caso de que el repartidor no esté asignado
        Repartidor repartidor = envio.getRepartidor();
        repartidorLabel.setText(repartidor != null ? repartidor.getNombre() : "Pendiente de asignación");

        // Poblar detalles de direcciones y paquete
        origenLabel.setText(envio.getOrigen().toString());
        destinoLabel.setText(envio.getDestino().toString());
        paqueteLabel.setText(String.format("%.2f kg, %.2f m³", envio.getPeso(), envio.getVolumen()));

        // Construir el desglose de costos
        buildCostosText(envio);
    }

    @FXML
    void handleAsignar(ActionEvent e){
        envio.asignarRepartidorState(new Repartidor("R1","Pedro","123","111","Zona"));
        reload();
    }

    @FXML
    void handleEnRuta(ActionEvent e){
        envio.salirEnRuta();
        reload();
    }

    @FXML
    void handleEntregar(ActionEvent e){
        envio.marcarEntregado();
        reload();
    }

    @FXML
    void handleIncidencia(ActionEvent e){
        envio.marcarIncidencia("Retraso por clima");
        reload();
    }


    /**
     * Construye el texto para el área de desglose de costos.
     */
    private void buildCostosText(Envio envio) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Costo Base: \t\t%s\n", currencyFormatter.format(envio.getCostoBase())));

        if (envio.getServiciosAdicionales() != null && !envio.getServiciosAdicionales().isEmpty()) {
            sb.append("Servicios Adicionales:\n");
            for (ServicioAdicional servicio : envio.getServiciosAdicionales()) {
                sb.append(String.format("- %s: \t%s\n", servicio.getDescripcion(), currencyFormatter.format(servicio.getCostoAdicional())));
            }
        }

        sb.append("------------------------------------\n");
        sb.append(String.format("COSTO TOTAL: \t%s", currencyFormatter.format(envio.calcularCostoTotal())));

        costosArea.setText(sb.toString());
    }

    /**
     * Formatea un objeto LocalDateTime a un String legible. Maneja valores nulos.
     */
    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "No disponible";
        }
        return dateTime.format(formatter);
    }

    /**
     * Maneja el evento del botón "Cerrar" para cerrar la ventana.
     */
    @FXML
    void handleCerrar(ActionEvent event) {
        Stage stage = (Stage) cerrarButton.getScene().getWindow();
        stage.close();
    }
    private void reload() {
        estadoLabel.setText(envio.getEstado().getDescripcion());

        Repartidor r = envio.getRepartidor();
        repartidorLabel.setText(r != null ? r.getNombre() : "Pendiente de asignación");

        fechaEstimadaLabel.setText(formatDate(envio.getFechaEstimadaEntrega()));
        buildCostosText(envio);
    }
}