package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Agregación del servicio seguro mediante patrón decorator
 */

public class SeguroDecorator extends ServicioAdicional {

    private final double valorAsegurado;

    public SeguroDecorator(double valorAsegurado) {
        this.valorAsegurado = valorAsegurado;
    }

    /**
     * Metodo para calcular el total del servicio agregado
     * @return valor agregado
     */
    @Override
    public double getCostoAdicional() {
        return valorAsegurado * 0.01;
    }

    @Override
    public String getDescripcion() {
        return "Seguro por valor de $" + valorAsegurado;
    }
}
