package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Interfaz para el patrón Strategy.
 * Define el método que deben implementar todas las estrategias de cálculo de tarifas.
 */
public interface CalculoTarifaStrategy {
    double calcular(double peso, double volumen, double distancia);
}
