package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * implemenytación de Metodo de pago por tarjeta usando el patron adapter
 */
public class PasarelaTarjetaAdapter implements ProcesadorPago {

    private final PasarelaProcesarTajeta sdk = new PasarelaProcesarTajeta();

    @Override
    public boolean procesar(Pago pago) {
        String numeroTarjetaSimulada = "4111-XXXX-XXXX-1234";
        System.out.println("[PasarelaTarjetaAdapter] Usando Pasarela X para pago " + pago.getIdPago());

        return sdk.cobrarConTarjeta(
                numeroTarjetaSimulada,
                pago.getMonto(),
                pago.getIdPago()
        );
    }
}
