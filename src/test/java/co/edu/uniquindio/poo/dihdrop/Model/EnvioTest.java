package co.edu.uniquindio.poo.dihdrop.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnvioTest {

    private Usuario usuario;
    private Direccion origen;
    private Direccion destino;
    private ServicioAdicional seguro;
    private ServicioAdicional embalaje;

    @BeforeEach
    void setUp() {
        // TODO: Ajusta estos constructores a los que tengas en tu proyecto
        usuario = new Usuario("0000", "Juan Perez", "juanperez@gmail.com","12345");
        origen = new Direccion("Calle 1 #2-3", "Ciudad A","1", "A","12,54,78");
        destino = new Direccion("Carrera 4 #5-6", "Ciudad B", "3", "B","12,54,78");

        seguro = new SeguroDecorator( 5000);
        embalaje = new SeguroDecorator( 3000) {
        };
    }

    private Envio crearEnvioBase() {
        // Creamos un envío usando el Builder
        Envio envio = new Envio.EnvioBuilder("ENV-001", usuario, origen, destino)
                .withPeso(2.5)
                .withVolumen(0.1)
                .withServicioAdicional(seguro)
                .build();

        envio.setCostoBase(10000.0); // costo base configurable
        return envio;
    }

    @Test
    void builderDebeConstruirEnvioConCamposObligatorios() {
        Envio envio = new Envio.EnvioBuilder("ENV-002", usuario, origen, destino)
                .withPeso(1.0)
                .withVolumen(0.05)
                .build();

        assertNotNull(envio);
        assertEquals("ENV-002", envio.getIdEnvio());
        assertEquals(usuario, envio.getUsuario());
        assertEquals(origen, envio.getOrigen());
        assertEquals(destino, envio.getDestino());
        assertEquals(1.0, envio.getPeso());
        assertEquals(0.05, envio.getVolumen());
        assertEquals(EstadoEnvio.SOLICITADO, envio.getEstado());
        assertNotNull(envio.getFechaCreacion());
    }

    @Test
    void actualizarEstadoDebeCambiarElEstadoDelEnvio() {
        Envio envio = crearEnvioBase();

        assertEquals(EstadoEnvio.SOLICITADO, envio.getEstado());

        envio.actualizarEstado(EstadoEnvio.EN_RUTA);

        assertEquals(EstadoEnvio.EN_RUTA, envio.getEstado());
    }

    @Test
    void agregarServicioAdicionalDebeAñadirloALaLista() {
        Envio envio = crearEnvioBase();

        int tamañoInicial = envio.getServiciosAdicionales() != null
                ? envio.getServiciosAdicionales().size()
                : 0;

        envio.agregarServicioAdicional(embalaje);

        List<ServicioAdicional> servicios = envio.getServiciosAdicionales();
        assertNotNull(servicios);
        assertEquals(tamañoInicial + 1, servicios.size());
        assertTrue(servicios.contains(embalaje));
    }

    @Test
    void calcularCostoTotalDebeSumarCostoBaseMasServicios() {
        Envio envio = crearEnvioBase();
        // En crearEnvioBase ya se agrega "seguro" con costo 50 (1% del total)
        envio.agregarServicioAdicional(embalaje); // +30 (1% del total)

        // costoBase = 10000
        double costoTotal = envio.calcularCostoTotal();

        assertEquals(10000.0 + 50.0 + 30.0, costoTotal);
    }

    @Test
    void calcularCostoTotalConListaVaciaDebeRetornarSoloCostoBase() {
        Envio envio = new Envio.EnvioBuilder("ENV-003", usuario, origen, destino)
                .withPeso(3.0)
                .withVolumen(0.2)
                .build();

        envio.setCostoBase(8000.0);

        double costoTotal = envio.calcularCostoTotal();

        assertEquals(8000.0, costoTotal);
    }

    @Test
    void obtenerDetalleServiciosDebeRetornarNingunoSiNoHayServicios() {
        Envio envio = new Envio.EnvioBuilder("ENV-004", usuario, origen, destino)
                .build();

        String detalle = envio.obtenerDetalleServicios();

        assertEquals("Ninguno", detalle);
    }

    @Test
    void obtenerDetalleServiciosDebeListarServiciosSeparadosPorComa() {
        Envio envio = new Envio.EnvioBuilder("ENV-005", usuario, origen, destino)
                .withServicioAdicional(seguro)
                .withServicioAdicional(embalaje)
                .build();

        String detalle = envio.obtenerDetalleServicios();

        // El orden depende de cómo se agregan en el builder
        assertTrue(detalle.contains(seguro.getDescripcion()));
        assertTrue(detalle.contains(embalaje.getDescripcion()));
        assertTrue(detalle.contains(",")); // debe estar separado por coma
    }

    @Test
    void settersDeCostoBaseRepartidorYFechaEstimadaDebenActualizarValores() {
        Envio envio = crearEnvioBase();

        Repartidor repartidor = new Repartidor("R1", "Carlos", "123456789","321876543","norte");
        LocalDateTime fechaEstimada = LocalDateTime.now().plusDays(2);

        envio.setCostoBase(15000.0);
        envio.setRepartidor(repartidor);
        envio.setFechaEstimadaEntrega(fechaEstimada);

        assertEquals(15000.0, envio.getCostoBase());
        assertEquals(repartidor, envio.getRepartidor());
        assertEquals(fechaEstimada, envio.getFechaEstimadaEntrega());
    }
}
