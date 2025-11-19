package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Interface del patron adapter que implementan todos los tipos de pago
 */

public interface ProcesadorPago {
    boolean procesar(Pago pago);
}
