package co.edu.uniquindio.poo.dihdrop.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class GestorEnvios {


    /**
     * Patrón Singleton
     */
    private static GestorEnvios instancia;

    private List<Envio> envios;
    private CalculoTarifaStrategy tarifaStrategy; // Dependencia para el cálculo de tarifas


    private GestorEnvios() {
        envios = new ArrayList<>();
    }


    public static synchronized GestorEnvios getInstancia() {
        if (instancia == null) {
            instancia = new GestorEnvios();
        }
        return instancia;
    }


    /**
     *
     * @param strategy
     */
    public void setTarifaStrategy(CalculoTarifaStrategy strategy) {
        this.tarifaStrategy = strategy;
    }


    public Envio crearEnvio(Usuario user, Direccion org, Direccion dest, double peso, double volumen) {
        if (tarifaStrategy == null) {
            throw new IllegalStateException("La estrategia de cálculo de tarifa no ha sido establecida.");
        }

        String nuevoId = "ENV-" + (envios.size() + 1);

        // --- USO DEL PATRÓN BUILDER ---
        Envio nuevoEnvio = new Envio.EnvioBuilder(nuevoId, user, org, dest)
                .withPeso(peso)
                .withVolumen(volumen)
                .build();

        // Lógica de negocio: calcular el costo base después de crear el objeto
        double distanciaSimulada = 10.5; // Distancia en km (podría venir de un servicio externo)
        double costoBase = tarifaStrategy.calcular(peso, volumen, distanciaSimulada);
        nuevoEnvio.setCostoBase(costoBase);

        envios.add(nuevoEnvio);
        System.out.println("Envío creado con ID: " + nuevoEnvio.getIdEnvio() + " a través del Builder.");
        System.out.println("Total de envíos en Gestor después de crear: " + envios.size());
        return nuevoEnvio;
    }


    public void asignarRepartidor(String idEnvio, Repartidor rep) {
        findEnvioById(idEnvio).ifPresent(envio -> {
            envio.setRepartidor(rep);
            envio.actualizarEstado(EstadoEnvio.ASIGNADO);
        });
    }


    public void cancelarEnvio(String idEnvio) {
        findEnvioById(idEnvio).ifPresent(envio -> {
            if (envio.getEstado() == EstadoEnvio.SOLICITADO) {
                envio.actualizarEstado(EstadoEnvio.CANCELADO);
            } else {
                System.out.println("No se puede cancelar el envío " + idEnvio + " porque ya no está en estado SOLICITADO.");
            }
        });
    }


    public Optional<Envio> findEnvioById(String id) {
        return envios.stream()
                .filter(e -> e.getIdEnvio().equals(id))
                .findFirst();
    }


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