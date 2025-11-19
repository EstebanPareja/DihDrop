package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Logica de pago por tarjeta para ser implementada en el adapter
 */
public class PasarelaProcesarTajeta {
    public boolean cobrarConTarjeta(String numeroTarjeta, double monto, String referencia) {
        System.out.println("[PasarelaProcesarTajeta] Cobro con tarjeta: " + numeroTarjeta +
                " por $" + monto + " ref=" + referencia);
        return monto <= 2_000_000;
    }
}
