package co.edu.uniquindio.poo.dihdrop.Controller;

import co.edu.uniquindio.poo.dihdrop.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class CreateShipmentController {

    @FXML private TextField origenCalleField;
    @FXML private TextField origenCiudadField;
    @FXML private TextField destinoCalleField;
    @FXML private TextField destinoCiudadField;
    @FXML private TextField pesoField;
    @FXML private TextField volumenField;
    @FXML private CheckBox seguroCheck;
    @FXML private CheckBox fragilCheck;
    @FXML private CheckBox firmaCheck;
    @FXML private Label costoLabel;
    @FXML private Button confirmarButton;
    @FXML private Label messageLabel;
    @FXML private ComboBox<String> metodoPagoCombo;
    @FXML private Label estadoPagoLabel;


    private Usuario usuarioActual;
    private double costoCalculado = 0.0;

    /**
     * Inicializa el controlador. Se asegura de que la estrategia de tarifa esté disponible.
     */
    @FXML
    public void initialize() {
        // Aseguramos que el gestor tenga una estrategia de cálculo.
        // En una aplicación real, esto se haría en un punto de arranque central.
        GestorEnvios.getInstancia().setTarifaStrategy(new CalculoTarifaBasico());
    }

    /**
     * Recibe el objeto Usuario desde la vista principal.
     */
    public void initData(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    /**
     * Maneja la acción de cotizar el envío.
     */
    @FXML
    void handleCotizar(ActionEvent event) {
        messageLabel.setText("");

        // 1. Validar entradas
        if (!validarCampos()) {
            return;
        }

        try {
            double peso = Double.parseDouble(pesoField.getText());
            double volumen = Double.parseDouble(volumenField.getText());

            // 2. Calcular costo base usando la estrategia
            // Simulación de distancia
            double distanciaSimulada = 25.0;
            costoCalculado = GestorEnvios.getInstancia()
                    .getTarifaStrategy()
                    .calcular(peso, volumen, distanciaSimulada);

            // 3. Añadir costos de servicios adicionales (simulado)
            if (seguroCheck.isSelected()) costoCalculado += 5000;
            if (fragilCheck.isSelected()) costoCalculado += 3000;
            if (firmaCheck.isSelected()) costoCalculado += 2000;

            // 4. Mostrar costo y habilitar botón de confirmación
            costoLabel.setText(String.format("$%.2f", costoCalculado));
            confirmarButton.setDisable(false);

        } catch (NumberFormatException e) {
            messageLabel.setText("Error: Peso y Volumen deben ser números.");
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
     * Maneja la confirmación y creación final del envío.
     */
    @FXML
    void handleConfirmarEnvio(ActionEvent event) {
        if (costoCalculado <= 0) {
            messageLabel.setText("Debe cotizar el envío primero.");
            return;
        }

        // Crear objetos Direccion
        Direccion origen = new Direccion("D-ORG", "Origen", origenCalleField.getText(), origenCiudadField.getText(), "0,0");
        Direccion destino = new Direccion("D-DEST", "Destino", destinoCalleField.getText(), destinoCiudadField.getText(), "1,1");

        // Crear el envío usando el Gestor
        Envio nuevoEnvio = GestorEnvios.getInstancia().crearEnvio(
                usuarioActual,
                origen,
                destino,
                Double.parseDouble(pesoField.getText()),
                Double.parseDouble(volumenField.getText())
        );

        // TODO: Aquí se podrían agregar los decoradores para servicios adicionales
        // al objeto 'nuevoEnvio' si fuera necesario registrar más detalles.

        System.out.println("Envío creado exitosamente con ID: " + nuevoEnvio.getIdEnvio());
        messageLabel.setText("¡Envío creado con éxito!");

        String metodo = metodoPagoCombo.getValue();

        Pago pago = new Pago(
                "P-" + nuevoEnvio.getIdEnvio(),
                costoCalculado,
                LocalDateTime.now(),
                metodo,
                false,
                nuevoEnvio
        );

// --> FACTORY
        ProcesadorPago procesador = PagoFactory.crearProcesador(metodo);

// --> ADAPTER
        boolean aprobado = procesador.procesar(pago);
        pago.setAprobado(aprobado);

        if (!aprobado) {
            estadoPagoLabel.setText("Pago rechazado");
            return;
        }
        estadoPagoLabel.setText("Pago aprobado");

// Notificación si quieres
        nuevoEnvio.agregarObserver(new NotificacionEmail());
        nuevoEnvio.agregarObserver(new NotificacionSMS());



        // Cerrar la ventana después de crear el envío
        cerrarVentana();
    }

    /**
     * Maneja la cancelación y cierra la ventana.
     */
    @FXML
    void handleCancelar(ActionEvent event) {
        cerrarVentana();
    }

    /**
     * Valida que los campos de texto no estén vacíos.
     */
    private boolean validarCampos() {
        if (origenCalleField.getText().isEmpty() || origenCiudadField.getText().isEmpty() ||
                destinoCalleField.getText().isEmpty() || destinoCiudadField.getText().isEmpty() ||
                pesoField.getText().isEmpty() || volumenField.getText().isEmpty()) {

            messageLabel.setText("Por favor, llene todos los campos.");
            return false;
        }
        return true;
    }






    /**
     * Método de utilidad para cerrar la ventana actual.
     */
    private void cerrarVentana() {
        Stage stage = (Stage) confirmarButton.getScene().getWindow();
        stage.close();
    }
}