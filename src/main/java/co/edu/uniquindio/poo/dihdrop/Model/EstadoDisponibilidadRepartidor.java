package co.edu.uniquindio.poo.dihdrop.Model;

public enum EstadoDisponibilidadRepartidor {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    EN_RUTA("En Ruta");

    private final String descripcion;

    EstadoDisponibilidadRepartidor(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
