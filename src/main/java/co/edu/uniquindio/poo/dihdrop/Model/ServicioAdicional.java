package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Clase base abstracta para el patrón Decorator.
 * Define la interfaz para los servicios que añaden responsabilidades a un Envío.
 */

public abstract class ServicioAdicional {

    public abstract double getCostoAdicional();
    public abstract String getDescripcion();

}
