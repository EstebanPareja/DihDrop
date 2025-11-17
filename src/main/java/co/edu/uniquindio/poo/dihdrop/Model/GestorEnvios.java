package co.edu.uniquindio.poo.dihdrop.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Clase centralizada para gestionar toda la lógica de negocio relacionada con los envíos.
 * Implementa el patrón Singleton para garantizar una única instancia en toda la aplicación,
 * actuando como un punto de control central para las operaciones de envíos.
 */
public class GestorEnvios {

    // --- Implementación del patrón Singleton ---
    private static GestorEnvios instancia;

    private List<Envio> envios;
    private CalculoTarifaStrategy tarifaStrategy; // Dependencia para el cálculo de tarifas

    /**
     * Constructor privado para prevenir la instanciación directa desde fuera de la clase.
     * Inicializa la lista de envíos.
     */
    private GestorEnvios() {
        envios = new ArrayList<>();
    }

    /**
     * Método estático para obtener la única instancia de la clase.
     * Es 'synchronized' para ser seguro en entornos multi-hilo (thread-safe).
     * @return La instancia única de GestorEnvios.
     */
    public static synchronized GestorEnvios getInstancia() {
        if (instancia == null) {
            instancia = new GestorEnvios();
        }
        return instancia;
    }
    // --- Fin del Singleton ---

    /**
     * Establece la estrategia que se usará para calcular las tarifas de los envíos.
     * Esto permite cambiar el algoritmo de cálculo dinámicamente (Patrón Strategy).
     * @param strategy La implementación de CalculoTarifaStrategy a utilizar.
     */
    public void setTarifaStrategy(CalculoTarifaStrategy strategy) {
        this.tarifaStrategy = strategy;
    }

    /**
     * Crea un nuevo envío utilizando el patrón Builder para construir el objeto Envio.
     * Calcula su costo base usando la estrategia de tarifa configurada.
     * @param user El usuario que solicita el envío.
     * @param org La dirección de origen.
     * @param dest La dirección de destino.
     * @param peso El peso del paquete en kg.
     * @param volumen El volumen del paquete en m3.
     * @return El objeto Envio recién creado y añadido al sistema.
     * @throws IllegalStateException si no se ha configurado una estrategia de tarifas.
     */
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

        this.envios.add(nuevoEnvio);
        System.out.println("Envío creado con ID: " + nuevoEnvio.getIdEnvio() + " a través del Builder.");

        return nuevoEnvio;
    }

    /**
     * Asigna un repartidor a un envío existente y actualiza su estado a 'ASIGNADO'.
     * @param idEnvio El ID del envío a modificar.
     * @param rep El repartidor que se asignará.
     */
    public void asignarRepartidor(String idEnvio, Repartidor rep) {
        findEnvioById(idEnvio).ifPresent(envio -> {
            envio.setRepartidor(rep);
            envio.actualizarEstado(EstadoEnvio.ASIGNADO);
        });
    }

    /**
     * Cancela un envío si cumple con las reglas de negocio (solo puede cancelarse si está en estado 'SOLICITADO').
     * @param idEnvio El ID del envío a cancelar.
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
     * Busca un envío por su ID.
     * @param id El ID del envío a buscar.
     * @return un Optional que contiene el envío si se encuentra, o un Optional vacío si no.
     */
    public Optional<Envio> findEnvioById(String id) {
        return envios.stream()
                .filter(e -> e.getIdEnvio().equals(id))
                .findFirst();
    }

    /**
     * Filtra y devuelve una lista de envíos creados en una fecha específica.
     * @param fecha La fecha para la cual se quieren obtener los envíos.
     * @return Una lista de envíos.
     */
    public List<Envio> filtrarEnviosPorFecha(LocalDate fecha) {
        return envios.stream()
                .filter(e -> e.getFechaCreacion().toLocalDate().isEqual(fecha))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve una copia de la lista de todos los envíos registrados en el sistema.
     * Se devuelve una copia para proteger la lista original de modificaciones externas (encapsulación).
     * @return Una lista con todos los envíos.
     */
    public List<Envio> getTodosLosEnvios() {
        return new ArrayList<>(envios);
    }

    public CalculoTarifaStrategy getTarifaStrategy() {
        return tarifaStrategy;
    }
}