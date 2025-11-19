package co.edu.uniquindio.poo.dihdrop.Model;

public class PasarelaYAdapter implements ProcesadorPago {

    private final PasarelaYSDK sdk = new PasarelaYSDK();

    @Override
    public boolean procesar(Pago pago) {
        String cuentaSimulada = "CUENTA-12345";
        System.out.println("[PasarelaYAdapter] Usando Pasarela Y para pago " + pago.getIdPago());

        int codigo = sdk.crearTransaccionDebito(
                cuentaSimulada,
                pago.getMonto()
        );

        return codigo == 0;
    }
}
