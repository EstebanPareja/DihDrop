package co.edu.uniquindio.poo.dihdrop.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio {

    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double volumen;
    private double costoBase;
    private EstadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private Usuario usuario;
    private Repartidor repartidor;
    private List<ServicioAdicional> serviciosAdicionales;
    private List<EnvioObserver> observers = new ArrayList<>();
    private EstadoEnvioState estadoState = new EstadoSolicitado();

    /**
     * Constructor privado uso del patron  builder
     * @param builder
     */
    private Envio(EnvioBuilder builder) {
        this.idEnvio = builder.idEnvio;
        this.usuario = builder.usuario;
        this.origen = builder.origen;
        this.destino = builder.destino;
        this.peso = builder.peso;
        this.volumen = builder.volumen;
        this.serviciosAdicionales = builder.serviciosAdicionales;
        this.estado = EstadoEnvio.SOLICITADO;
        this.fechaCreacion = LocalDateTime.now();
        this.estadoState = new EstadoSolicitado();
        this.observers = new ArrayList<>();
        this.observers.add(new NotificacionEmail());
        this.observers.add(new NotificacionSMS());
        this.observers.add(new NotificacionPush());

    }

    /**
     * Clase builder anidada
     */
    public static class EnvioBuilder {
        private final String idEnvio;
        private final Usuario usuario;
        private final Direccion origen;
        private final Direccion destino;
        private double peso = 0.0;
        private double volumen = 0.0;
        private List<ServicioAdicional> serviciosAdicionales = new ArrayList<>();

        public EnvioBuilder(String idEnvio, Usuario usuario, Direccion origen, Direccion destino) {
            this.idEnvio = idEnvio;
            this.usuario = usuario;
            this.origen = origen;
            this.destino = destino;
        }

        public EnvioBuilder withPeso(double peso) {
            this.peso = peso;
            return this;
        }

        public EnvioBuilder withVolumen(double volumen) {
            this.volumen = volumen;
            return this;
        }

        public EnvioBuilder withServicioAdicional(ServicioAdicional servicio) {
            this.serviciosAdicionales.add(servicio);
            return this;
        }

        public Envio build() {
            return new Envio(this);
        }
    }


    /**
     * Metodo para actualizar el estado de un envio
     * @param nuevoEstado
     */
    public void actualizarEstado(EstadoEnvio nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("El envío " + idEnvio + " ha cambiado al estado: " + nuevoEstado.getDescripcion());
        notificarObservers("El envío ha cambiado al estado: " + nuevoEstado.getDescripcion());
    }


    /**
     * Metodo para registrar un observer del envío.
     * @param observer
     */
    public void agregarObserver(EnvioObserver observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }
        observers.add(observer);
    }

    /**
     * Metodo para eliminar un observer suscrito.
     * @param observer
     */
    public void eliminarObserver(EnvioObserver observer) {
        if (observers != null) {
            observers.remove(observer);
        }
    }

    /**
     * Metodo interno para notificar a todos los observers.
     * @param mensaje
     */
    private void notificarObservers(String mensaje) {
        if (observers == null || observers.isEmpty()) {
            return;
        }
        for (EnvioObserver observer : observers) {
            observer.notificar(this, mensaje);
        }
    }



    /**
     * Metodo para agregar un servicio adicional con el decorator
     * @param servicio
     */
    public void agregarServicioAdicional(ServicioAdicional servicio) {
        if (this.serviciosAdicionales == null) {
            this.serviciosAdicionales = new ArrayList<>();
        }
        this.serviciosAdicionales.add(servicio);
    }

    /**
     * Metodo para calcular el costo total de un envio
     * @return costo total
     */
    public double calcularCostoTotal() {
        double costoTotal = this.costoBase;
        if (serviciosAdicionales != null) {
            for (ServicioAdicional servicio : serviciosAdicionales) {
                costoTotal += servicio.getCostoAdicional();
            }
        }
        return costoTotal;
    }

    /**
     * Metodo para mostrar los detalles de un envio
     * @return lista de detalles
     */
    public String obtenerDetalleServicios() {
        if (serviciosAdicionales == null || serviciosAdicionales.isEmpty()) {
            return "Ninguno";
        }
        StringBuilder detalles = new StringBuilder();
        for (ServicioAdicional servicio : serviciosAdicionales) {
            detalles.append(servicio.getDescripcion()).append(", ");
        }
        return detalles.substring(0, detalles.length() - 2);
    }

    ///  Getters y setters

    public String getIdEnvio() { return idEnvio; }
    public Direccion getOrigen() { return origen; }
    public Direccion getDestino() { return destino; }
    public double getPeso() { return peso; }
    public double getVolumen() { return volumen; }
    public double getCostoBase() { return costoBase; }
    public EstadoEnvio getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public Usuario getUsuario() { return usuario; }
    public Repartidor getRepartidor() { return repartidor; }
    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }
    public EstadoEnvioState getEstadoState() { return estadoState; }


    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }
    public void setEstadoState(EstadoEnvioState nuevo) {
        this.estadoState = nuevo;
    }


    public void asignarRepartidorState(Repartidor repartidor) {
        estadoState.asignar(this, repartidor);
    }

    public void salirEnRuta() {
        estadoState.salirEnRuta(this);
    }

    public void marcarEntregado() {
        estadoState.entregar(this);
    }

    public void marcarIncidencia(String detalle) {
        estadoState.reportarIncidencia(this, detalle);
    }



}
