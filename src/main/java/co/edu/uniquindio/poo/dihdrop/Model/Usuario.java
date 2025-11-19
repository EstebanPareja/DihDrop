package co.edu.uniquindio.poo.dihdrop.Model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String idUsuario;
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private List<Direccion> direccionesFrecuentes;
    private List<MetodoPago> metodosDePago;

    /**
     * Constructor de la clase usuario
     * @param idUsuario
     * @param nombreCompleto
     * @param correoElectronico
     * @param telefono
     */
    public Usuario(String idUsuario, String nombreCompleto, String correoElectronico, String telefono) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.direccionesFrecuentes = new ArrayList<>();
        this.metodosDePago = new ArrayList<>();
    }

    /**
     * Metodo para actualizar la informacion del perfil de un usuario
     * @param nombre
     * @param correo
     * @param telefono
     */
    public void actualizarPerfil(String nombre, String correo, String telefono) {
        this.nombreCompleto = nombre;
        this.correoElectronico = correo;
        this.telefono = telefono;
    }

    /**
     * Metodo para agregar una direccion al usuario
     * @param direccion
     */
    public void agregarDireccion(Direccion direccion) {
        this.direccionesFrecuentes.add(direccion);
    }

    /**
     * Metodo para eliminar una direccion de un usuario
     * @param idDireccion
     */
    public void eliminarDireccion(String idDireccion) {
        this.direccionesFrecuentes.removeIf(d -> d.getIdDireccion().equals(idDireccion));
    }

    /**
     * Metodo para agregarle un metodo de pago a un usuario
     * @param metodo
     */
    public void agregarMetodoPago(MetodoPago metodo) {
        this.metodosDePago.add(metodo);
    }

    ///  Getters y setters

    public String getIdUsuario() { return idUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreoElectronico() { return correoElectronico; }
    public String getTelefono() { return telefono; }
    public List<Direccion> getDireccionesFrecuentes() { return direccionesFrecuentes; }
    public List<MetodoPago> getMetodosDePago() { return metodosDePago; }
}
