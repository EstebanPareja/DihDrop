package co.edu.uniquindio.poo.dihdrop.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Administrador {

    private String idAdministrador;
    private String nombreCompleto;
    private String correoElectronico;
    private String telefono;
    private List<Usuario> listaUsuarios;
    private List<Repartidor> listaRepartidores;

    /**
     * Constructor de la clase Administrador
     * @param idAdministrador identificador único del administrador
     * @param nombreCompleto nombre completo del administrador
     * @param correoElectronico correo de contacto
     * @param telefono teléfono de contacto
     */
    public Administrador(String idAdministrador, String nombreCompleto, String correoElectronico, String telefono) {
        this.idAdministrador = idAdministrador;
        this.nombreCompleto = nombreCompleto;
        this.correoElectronico = correoElectronico;
        this.telefono = telefono;
        this.listaUsuarios = new ArrayList<>();
        this.listaRepartidores = new ArrayList<>();
    }

    /**
     * Metodo para registrar un nuevo usuario en la lista
     * @param usuario
     * @return boolean
     */
    public boolean registrarUsuario(Usuario usuario) {
        Optional<Usuario> existente = listaUsuarios.stream()
                .filter(u -> u.getIdUsuario().equals(usuario.getIdUsuario()))
                .findFirst();
        if (existente.isPresent()) {
            return false;
        }
        listaUsuarios.add(usuario);
        return true;
    }

    /**
     * Metodo para eliminar un usuario de la lista por su id
     * @param idUsuario
     * @return boolean
     */
    public boolean eliminarUsuarioPorId(String idUsuario) {
        return listaUsuarios.removeIf(u -> u.getIdUsuario().equals(idUsuario));
    }

    /**
     * Metodo para buscar un usuario por su id
     * @param idUsuario
     * @return Usuario
     */
    public Usuario buscarUsuarioPorId(String idUsuario) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getIdUsuario().equals(idUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Metodo para registrar un nuevo repartidor en la lista
     * @param repartidor
     * @return boolean
     */
    public boolean registrarRepartidor(Repartidor repartidor) {
        Optional<Repartidor> existente = listaRepartidores.stream()
                .filter(r -> r.getIdRepartidor().equals(repartidor.getIdRepartidor()))
                .findFirst();

        if (existente.isPresent()) {
            return false;
        }
        listaRepartidores.add(repartidor);
        return true;
    }

    /**
     * Metodo para cambiar la disponibilidad de un repartidor
     * @param idRepartidor
     * @param nuevaDisponibilidad
     * @return boolean
     */
    public boolean actualizarDisponibilidadRepartidor(String idRepartidor, EstadoDisponibilidadRepartidor nuevaDisponibilidad) {
        for (Repartidor repartidor : listaRepartidores) {
            if (repartidor.getIdRepartidor().equals(idRepartidor)) {
                repartidor.cambiarDisponibilidad(nuevaDisponibilidad);
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo para buscar un repartidor por su id
     * @param idRepartidor
     * @return Repartidor
     */
    public Repartidor buscarRepartidorPorId(String idRepartidor) {
        for (Repartidor repartidor : listaRepartidores) {
            if (repartidor.getIdRepartidor().equals(idRepartidor)) {
                return repartidor;
            }
        }
        return null;
    }

    /**
     * Metodo para crear un envio para un usuario usando el GestorEnvios
     * @param idUsuario
     * @param origen
     * @param destino
     * @param peso
     * @param volumen
     * @return Envio
     */
    public Envio crearEnvioParaUsuario(String idUsuario, Direccion origen, Direccion destino, double peso, double volumen) {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            return null;
        }
        GestorEnvios gestorEnvios = GestorEnvios.getInstancia();
        return gestorEnvios.crearEnvio(usuario, origen, destino, peso, volumen);
    }

    /**
     * Metodo para asignar un envio a un repartidor
     * @param idEnvio
     * @param idRepartidor
     * @return boolean
     */
    public boolean asignarEnvioARepartidor(String idEnvio, String idRepartidor) {
        Repartidor repartidor = buscarRepartidorPorId(idRepartidor);
        if (repartidor == null) {
            return false;
        }
        GestorEnvios gestorEnvios = GestorEnvios.getInstancia();
        gestorEnvios.asignarRepartidor(idEnvio, repartidor);
        return true;
    }

    /**
     * Metodo para cancelar un envio a través del GestorEnvios
     * @param idEnvio
     */
    public void cancelarEnvio(String idEnvio) {
        GestorEnvios gestorEnvios = GestorEnvios.getInstancia();
        gestorEnvios.cancelarEnvio(idEnvio);
    }


    public List<Envio> obtenerTodosLosEnvios() {
        GestorEnvios gestorEnvios = GestorEnvios.getInstancia();
        return gestorEnvios.getTodosLosEnvios();
    }



    public String getIdAdministrador() {
        return idAdministrador;
    }


    public String getNombreCompleto() {
        return nombreCompleto;
    }


    public String getCorreoElectronico() {
        return correoElectronico;
    }


    public String getTelefono() {
        return telefono;
    }


    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }


    public List<Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }
}

