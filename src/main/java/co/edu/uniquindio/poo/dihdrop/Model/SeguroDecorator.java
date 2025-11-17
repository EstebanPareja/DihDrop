package co.edu.uniquindio.poo.dihdrop.Model;

public class SeguroDecorator extends ServicioAdicional {

    private final double valorAsegurado;

    public SeguroDecorator(double valorAsegurado) {
        this.valorAsegurado = valorAsegurado;
    }

    @Override
    public double getCostoAdicional() {
        // El costo del seguro es el 1% del valor asegurado
        return valorAsegurado * 0.01;
    }

    @Override
    public String getDescripcion() {
        return "Seguro por valor de $" + valorAsegurado;
    }
}
