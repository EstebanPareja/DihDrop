package co.edu.uniquindio.poo.dihdrop.Model;

public class FirmaDecorator extends ServicioAdicional {

    @Override
    public double getCostoAdicional() {
        return 2000;
    }

    @Override
    public String getDescripcion() {
        return "Firma requerida";
    }
}
