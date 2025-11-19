package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Agregación del servicio seguro mediante patrón decorator
 */
public class FirmaDecorator extends ServicioAdicional {

    /**
     *Definir de forma imnutable el costo adicional del servicio
     * @return double costo firma
     */
    @Override
    public final double getCostoAdicional() {
        return 2000;
    }

    @Override
    public String getDescripcion() {
        return "Firma requerida";
    }
}
