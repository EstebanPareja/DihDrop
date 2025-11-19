package co.edu.uniquindio.poo.dihdrop.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportesFacade {

    private final GestorEnvios gestorEnvios = GestorEnvios.getInstancia();

    public List<Envio> obtenerEnviosPorFecha(LocalDate fecha) {
        return gestorEnvios.filtrarEnviosPorFecha(fecha);
    }

    public long contarEnviosEntregados() {
        return gestorEnvios.getTodosLosEnvios().stream()
                .filter(e -> e.getEstado() == EstadoEnvio.ENTREGADO)
                .count();
    }

    public double calcularIngresoTotal() {
        return gestorEnvios.getTodosLosEnvios().stream()
                .mapToDouble(Envio::calcularCostoTotal)
                .sum();
    }

    public Map<String, Long> incidenciasPorCiudad() {
        return gestorEnvios.getTodosLosEnvios().stream()
                .filter(e -> e.getEstado() == EstadoEnvio.INCIDENCIA)
                .collect(Collectors.groupingBy(
                        e -> e.getDestino().getCiudad(),
                        Collectors.counting()
                ));
    }
}