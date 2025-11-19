package co.edu.uniquindio.poo.dihdrop.Model;

public class PagoFactory {

    public static ProcesadorPago crearProcesador(String tipoMetodo) {
        return switch (tipoMetodo) {
            case "TARJETA" -> new PasarelaXAdapter();
            case "PSE"     -> new PasarelaYAdapter();
            default -> throw new IllegalArgumentException("Método de pago no soportado: " + tipoMetodo);
        };
    }
}
