package co.edu.uniquindio.poo.dihdrop.Model;

public class EstadoIncidencia implements EstadoEnvioState {

    @Override
    public void asignar(Envio envio, Repartidor repartidor) {
        System.out.println("No se puede asignar mientras hay una incidencia.");
    }

    @Override
    public void salirEnRuta(Envio envio) {
        System.out.println("No se puede salir en ruta mientras hay una incidencia.");
    }

    @Override
    public void entregar(Envio envio) {
        System.out.println("No se puede entregar mientras el envío está en incidencia.");
    }

    @Override
    public void reportarIncidencia(Envio envio, String detalle) {
        System.out.println("Ya está en estado de incidencia.");
    }
}
