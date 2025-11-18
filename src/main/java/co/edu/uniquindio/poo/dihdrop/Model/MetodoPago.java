package co.edu.uniquindio.poo.dihdrop.Model;

public class MetodoPago {
    private String idMetodoPago;
    private String tipo;
    private String detalles;


    /**
     * Constructor de la clase MetodoPago
     * @param idMetodoPago
     * @param tipo
     * @param detalles
     */
    public MetodoPago(String idMetodoPago, String tipo, String detalles) {
        this.idMetodoPago = idMetodoPago;
        this.tipo = tipo;
        this.detalles = detalles;
    }

    /// Getters y setters

    public String getIdMetodoPago() {
        return idMetodoPago;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDetalles() {
        return detalles;
    }
}
