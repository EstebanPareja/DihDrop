package co.edu.uniquindio.poo.dihdrop.Model;

public class EstadoSolicitado implements EstadoEnvioState {

    @Override
    public void asignar(Envio envio, Repartidor repartidor) {
        envio.setRepartidor(repartidor);
        envio.actualizarEstado(EstadoEnvio.ASIGNADO);
        envio.setEstadoState(new EstadoAsignado()); // transición
    }

    @Override
    public void salirEnRuta(Envio envio) {
        System.out.println("No se puede salir en ruta si el envío aún no ha sido asignado.");
    }

    @Override
    public void entregar(Envio envio) { /* similar, mensaje de error */ }

    @Override
    public void reportarIncidencia(Envio envio, String detalle) {
        envio.actualizarEstado(EstadoEnvio.INCIDENCIA);
        envio.setEstadoState(new EstadoIncidencia());
    }
}