package co.edu.uniquindio.poo.dihdrop.Model;

public class NotificacionSMS implements EnvioObserver {
    @Override
    public void notificar(Envio envio, String mensaje) {
        System.out.println("[SMS] a " + envio.getUsuario().getTelefono() + ": " + mensaje);
    }
}
