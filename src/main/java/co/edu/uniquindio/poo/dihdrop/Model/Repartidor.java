package co.edu.uniquindio.poo.dihdrop.Model;

import java.util.List;

public class Repartidor {
    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoDisponibilidadRepartidor disponibilidad;
    private String zonaCobertura;

    /**
     * Constructo principal de la clase repartidor
     * @param idRepartidor
     * @param nombre
     * @param documento
     * @param telefono
     * @param zonaCobertura
     */
    public Repartidor(String idRepartidor, String nombre, String documento, String telefono, String zonaCobertura) {
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.zonaCobertura = zonaCobertura;
        this.disponibilidad = EstadoDisponibilidadRepartidor.INACTIVO; // Por defecto
    }

    /**
     * Metodo para modificar el estado de disponibilidad de un repartidor
     * @param nuevaDisponibilidad
     */
    public void cambiarDisponibilidad(EstadoDisponibilidadRepartidor nuevaDisponibilidad) {
        this.disponibilidad = nuevaDisponibilidad;
    }


    public List<Envio> consultarEnviosAsignados() {
        return List.of();
    }

    /// Getters y setters

    public String getIdRepartidor() { return idRepartidor; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public String getTelefono() { return telefono; }
    public EstadoDisponibilidadRepartidor getDisponibilidad() { return disponibilidad; }
    public String getZonaCobertura() { return zonaCobertura; }
}
