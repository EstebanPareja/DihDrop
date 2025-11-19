package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Implementación concreta para un envio en ruta del patrón State
 */
public class EstadoEnRuta implements EstadoEnvioState {

    @Override
    public void asignar(Envio envio, Repartidor repartidor) {
        System.out.println("El envío ya está en ruta, no se puede reasignar.");
    }

    @Override
    public void salirEnRuta(Envio envio) {
        System.out.println("El envío ya está en ruta.");
    }

    @Override
    public void entregar(Envio envio) {
        envio.actualizarEstado(EstadoEnvio.ENTREGADO);
        envio.setEstadoState(new EstadoEntregado());
    }

    @Override
    public void reportarIncidencia(Envio envio, String detalle) {
        envio.actualizarEstado(EstadoEnvio.INCIDENCIA);
        envio.setEstadoState(new EstadoIncidencia());
    }
}
