package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Interface para el patron State
 */
public interface EstadoEnvioState {
    void asignar(Envio envio, Repartidor repartidor);
    void salirEnRuta(Envio envio);
    void entregar(Envio envio);
    void reportarIncidencia(Envio envio, String detalle);
}
