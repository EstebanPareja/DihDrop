package co.edu.uniquindio.poo.dihdrop.Model;

public class FragilDecorator extends ServicioAdicional {

    @Override
    public double getCostoAdicional() {
        return 3000;
    }

    @Override
    public String getDescripcion() {
        return "Manejo frágil";
    }
}