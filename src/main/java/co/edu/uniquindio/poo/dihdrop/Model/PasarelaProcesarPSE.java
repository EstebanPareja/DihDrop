package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Logica de pago por PSE para ser implementada en el adapter
 */
public class PasarelaProcesarPSE {
    public int crearTransaccionDebito(String idCuenta, double valor) {
        System.out.println("[PasarelaProcesarPSE] Débito desde cuenta " + idCuenta +
                " por $" + valor);
        if (valor <= 0) {
            return 100;
        }
        return 0;
    }
}
