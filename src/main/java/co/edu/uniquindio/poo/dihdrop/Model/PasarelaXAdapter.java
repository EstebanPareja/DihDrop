package co.edu.uniquindio.poo.dihdrop.Model;

public class PasarelaXAdapter implements ProcesadorPago {

    private final PasarelaXSDK sdk = new PasarelaXSDK();

    @Override
    public boolean procesar(Pago pago) {
        String numeroTarjetaSimulada = "4111-XXXX-XXXX-1234";
        System.out.println("[PasarelaXAdapter] Usando Pasarela X para pago " + pago.getIdPago());

        return sdk.cobrarConTarjeta(
                numeroTarjetaSimulada,
                pago.getMonto(),
                pago.getIdPago()
        );
    }
}
