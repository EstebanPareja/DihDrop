package co.edu.uniquindio.poo.dihdrop.Model;

public class NotificacionEmail implements EnvioObserver {
    @Override
    public void notificar(Envio envio, String mensaje) {
        System.out.println("[EMAIL] a " + envio.getUsuario().getCorreoElectronico() + ": " + mensaje);
    }
}