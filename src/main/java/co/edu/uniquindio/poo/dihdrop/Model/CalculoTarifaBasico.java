package co.edu.uniquindio.poo.dihdrop.Model;


/**
 * Implementación concreta de la estrategia de cálculo de tarifa.
 * Calcula el costo basado en un precio base por kg, volumen y km.
 */
public class CalculoTarifaBasico implements CalculoTarifaStrategy {

    private final double precioPorKg = 1500;
    private final double precioPorMetroCubico = 50000;
    private final double precioPorKm = 800;

    /**
     * Metodo para calcular el precio de un envio basico
     * @param peso
     * @param volumen
     * @param distancia
     * @return costo envio basico
     */
    @Override
    public double calcular(double peso, double volumen, double distancia) {
        double costoPeso = peso * precioPorKg;
        double costoVolumen = volumen * precioPorMetroCubico;
        double costoDistancia = distancia * precioPorKm;
        return costoPeso + costoVolumen + costoDistancia;
    }
}
