package co.edu.uniquindio.poo.dihdrop.Model;

import java.time.LocalDateTime;

public class Pago {
    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private String metodo;      // "TARJETA", "PSE", "NEQUI"
    private boolean aprobado;
    private Envio envioAsociado;

    public Pago(String idPago, double monto, LocalDateTime fecha, String metodo, boolean aprobado, Envio envioAsociado) {
        this.idPago = idPago;
        this.monto = monto;
        this.fecha = fecha;
        this.metodo = metodo;
        this.aprobado = aprobado;
        this.envioAsociado = envioAsociado;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public Envio getEnvioAsociado() {
        return envioAsociado;
    }

    public void setEnvioAsociado(Envio envioAsociado) {
        this.envioAsociado = envioAsociado;
    }
}

