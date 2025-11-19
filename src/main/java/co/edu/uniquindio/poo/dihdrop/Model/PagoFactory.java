package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Implementación del patron factory haciendo uso del adapter
 */

public class PagoFactory {

    public static ProcesadorPago crearProcesador(String tipoMetodo) {
        return switch (tipoMetodo) {
            case "TARJETA" -> new PasarelaTarjetaAdapter();
            case "PSE"     -> new PasarelaPSEAdapter();
            default -> throw new IllegalArgumentException("Método de pago no soportado: " + tipoMetodo);
        };
    }
}
