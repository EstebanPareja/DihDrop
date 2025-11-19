package co.edu.uniquindio.poo.dihdrop.Model;

public class PasarelaYSDK {
    public int crearTransaccionDebito(String idCuenta, double valor) {
        System.out.println("[PasarelaYSDK] Débito desde cuenta " + idCuenta +
                " por $" + valor);

        // Lógica simulada: falla si el valor es 0 o negativo.
        if (valor <= 0) {
            return 100; // código de error
        }
        return 0; // OK
    }
}
