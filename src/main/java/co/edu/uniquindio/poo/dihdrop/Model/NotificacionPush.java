package co.edu.uniquindio.poo.dihdrop.Model;

public class NotificacionPush implements EnvioObserver {
    @Override
    public void notificar(Envio envio, String mensaje) {
        System.out.println("[PUSH] Envío " + envio.getIdEnvio() + ": " + mensaje);
    }
}
