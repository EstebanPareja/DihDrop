package co.edu.uniquindio.poo.dihdrop.Model;

import java.util.List;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoDisponibilidad disponibilidad;
    private String zonaCobertura;

    public Repartidor(String idRepartidor, String nombre, String documento, String telefono, String zonaCobertura) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.zonaCobertura = zonaCobertura;
        this.disponibilidad = EstadoDisponibilidad.INACTIVO; // Por defecto
    }

    public void cambiarDisponibilidad(EstadoDisponibilidad nuevaDisponibilidad) {
        this.disponibilidad = nuevaDisponibilidad;
    }

    // En un sistema real, este método consultaría al GestorEnvios
    public List<Envio> consultarEnviosAsignados() {
        // Lógica para obtener los envíos. Por simplicidad, se omite.
        // Ejemplo: return GestorEnvios.getInstancia().getEnviosPorRepartidor(this.idRepartidor);
        return List.of(); // Devuelve lista vacía como placeholder
    }

    // --- Getters y Setters ---

    public String getIdRepartidor() { return idRepartidor; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public String getTelefono() { return telefono; }
    public EstadoDisponibilidad getDisponibilidad() { return disponibilidad; }
    public String getZonaCobertura() { return zonaCobertura; }
}
