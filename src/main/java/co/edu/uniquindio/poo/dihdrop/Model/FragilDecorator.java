package co.edu.uniquindio.poo.dihdrop.Model;

/**
 * Agregación del servicio producto fragil mediante patrón decorator
 */
public class FragilDecorator extends ServicioAdicional {


    /**
     *Definir de forma imnutable el costo adicional del servicio
     * @return double costo producto fragil
     */
    @Override
    public double getCostoAdicional() {
        return 3000;
    }

    @Override
    public String getDescripcion() {
        return "Manejo frágil";
    }
}