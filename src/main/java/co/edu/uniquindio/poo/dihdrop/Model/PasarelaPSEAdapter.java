package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * implementación de un Metodo pago por PSE usando el patron adapter
 */
public class PasarelaPSEAdapter implements ProcesadorPago {

    private final PasarelaProcesarPSE sdk = new PasarelaProcesarPSE();

    @Override
    public boolean procesar(Pago pago) {
        String cuentaSimulada = "CUENTA-12345";
        System.out.println("[PasarelaPSEAdapter] Usando Pasarela Y para pago " + pago.getIdPago());

        int codigo = sdk.crearTransaccionDebito(
                cuentaSimulada,
                pago.getMonto()
        );

        return codigo == 0;
    }
}
