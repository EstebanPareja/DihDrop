package co.edu.uniquindio.poo.dihdrop.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


public class GestorEnvios {


    /**
     * Patrón Singleton
     */

    private static GestorEnvios instancia;

    private List<Envio> envios;
    private CalculoTarifaStrategy tarifaStrategy; // Dependencia para el cálculo de tarifas

    /**
     * Constructor privado de la clase singleton
     */
    private GestorEnvios() {
        envios = new ArrayList<>();
    }


    /**
     * Creacion de la instancia única del gestor de envios
     * @return instancia gestorEnvios
     */
    public static synchronized GestorEnvios getInstancia() {
        if (instancia == null) {
            instancia = new GestorEnvios();
        }
        return instancia;
    }

    /**
     *Metodo para asignar una estrategia de tarifa aplicada al envío
     * @param strategy
     */

    public void setTarifaStrategy(CalculoTarifaStrategy strategy) {
        this.tarifaStrategy = strategy;
    }


    /**
     * Metodo para crear un envio y registrarlo
     * @param user
     * @param org
     * @param dest
     * @param peso
     * @param volumen
     * @return
     */
    public Envio crearEnvio(Usuario user, Direccion org, Direccion dest, double peso, double volumen) {
        if (tarifaStrategy == null) {
            throw new IllegalStateException("La estrategia de cálculo de tarifa no ha sido establecida.");
        }

        String nuevoId = "ENV-" + (envios.size() + 1);
        Envio nuevoEnvio = new Envio.EnvioBuilder(nuevoId, user, org, dest)
                .withPeso(peso)
                .withVolumen(volumen)
                .build();
        double distanciaSimulada = ThreadLocalRandom.current().nextDouble(1, 200);
        double costoBase = tarifaStrategy.calcular(peso, volumen, distanciaSimulada);
        nuevoEnvio.setCostoBase(costoBase);
        envios.add(nuevoEnvio);
        System.out.println("Envío creado con ID: " + nuevoEnvio.getIdEnvio() + " a través del Builder.");
        System.out.println("Total de envíos en Gestor después de crear: " + envios.size());
        return nuevoEnvio;
    }


    /**
     * Metodo para asignar un repartidor
     * @param idEnvio
     * @param rep
     */
    public void asignarRepartidor(String idEnvio, Repartidor rep) {
        findEnvioById(idEnvio).ifPresent(envio -> {
            if (envio.getEstadoState() == null) {
                envio.setEstadoState(new EstadoSolicitado());
            }
            envio.getEstadoState().asignar(envio, rep);
        });
    }


    /**
     * Metodo para actualizar el estado de un envío por su ID.
     * @param idEnvio
     * @param nuevoEstado
     */
    public void actualizarEstadoEnvio(String idEnvio, EstadoEnvio nuevoEstado) {
        findEnvioById(idEnvio).ifPresent(envio -> {
            envio.actualizarEstado(nuevoEstado);
        });
    }



    /**
     * Metodo para cancelar un envio
     * @param idEnvio
     */
    public void cancelarEnvio(String idEnvio) {
        findEnvioById(idEnvio).ifPresent(envio -> {
            if (envio.getEstado() == EstadoEnvio.SOLICITADO) {
                envio.actualizarEstado(EstadoEnvio.CANCELADO);
            } else {
                System.out.println("No se puede cancelar el envío " + idEnvio + " porque ya no está en estado SOLICITADO.");
            }
        });
    }


    /**
     * Metodo para buscar un envio por id
     * @param id
     * @return envio
     */
    public Optional<Envio> findEnvioById(String id) {
        return envios.stream()
                .filter(e -> e.getIdEnvio().equals(id))
                .findFirst();
    }


    /**
     *  Metodo para buscar un envio por una fecha en especifico
     * @param fecha
     * @return envio
     */
    public List<Envio> filtrarEnviosPorFecha(LocalDate fecha) {
        return envios.stream()
                .filter(e -> e.getFechaCreacion().toLocalDate().isEqual(fecha))
                .collect(Collectors.toList());
    }


    public List<Envio> getTodosLosEnvios() {
        return new ArrayList<>(envios);
    }

    public CalculoTarifaStrategy getTarifaStrategy() {
        return tarifaStrategy;
    }
}