package co.edu.uniquindio.poo.dihdrop.Model;

public class PasarelaXSDK {
    public boolean cobrarConTarjeta(String numeroTarjeta, double monto, String referencia) {
        System.out.println("[PasarelaXSDK] Cobro con tarjeta: " + numeroTarjeta +
                " por $" + monto + " ref=" + referencia);

        // Lógica simulada: aprueba si el monto es menor a 2 millones.
        return monto <= 2_000_000;
    }
}
