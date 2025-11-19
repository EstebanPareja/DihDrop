package co.edu.uniquindio.poo.dihdrop.Model;

public class EstadoAsignado implements EstadoEnvioState {

    @Override
    public void asignar(Envio envio, Repartidor repartidor) {
        System.out.println("El envío ya tiene un repartidor.");
    }

    @Override
    public void salirEnRuta(Envio envio) {
        envio.actualizarEstado(EstadoEnvio.EN_RUTA);
        envio.setEstadoState(new EstadoEnRuta());
    }

    @Override
    public void entregar(Envio envio) {
        System.out.println("No se puede entregar si el envío aún no ha salido a ruta.");
    }

    @Override
    public void reportarIncidencia(Envio envio, String detalle) {
        envio.actualizarEstado(EstadoEnvio.INCIDENCIA);
        envio.setEstadoState(new EstadoIncidencia());
    }
}