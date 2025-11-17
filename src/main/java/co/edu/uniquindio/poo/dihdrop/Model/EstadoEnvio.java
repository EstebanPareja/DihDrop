package co.edu.uniquindio.poo.dihdrop.Model;

public enum EstadoEnvio {
    SOLICITADO("Solicitado"),
    ASIGNADO("Asignado"),
    EN_RUTA("En Ruta"),
    ENTREGADO("Entregado"),
    INCIDENCIA("Con Incidencia"),
    CANCELADO("Cancelado");

    private final String descripcion;

    EstadoEnvio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
