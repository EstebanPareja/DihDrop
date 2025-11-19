package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Implementación concreta para un envio entregado del patrón State
 */
public class EstadoEntregado implements EstadoEnvioState {

    @Override
    public void asignar(Envio envio, Repartidor repartidor) {
        System.out.println("El envío ya fue entregado.");
    }

    @Override
    public void salirEnRuta(Envio envio) {
        System.out.println("El envío ya fue entregado.");
    }

    @Override
    public void entregar(Envio envio) {
        System.out.println("El envío ya fue entregado anteriormente.");
    }

    @Override
    public void reportarIncidencia(Envio envio, String detalle) {
        System.out.println("No se pueden reportar incidencias después de la entrega.");
    }
}
