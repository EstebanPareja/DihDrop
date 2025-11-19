package co.edu.uniquindio.poo.dihdrop.Controller;

import co.edu.uniquindio.poo.dihdrop.App;
import co.edu.uniquindio.poo.dihdrop.Model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Navegacion {

    // Rutas a los archivos FXML (Asegúrate de que coincidan con tu estructura de carpetas)

    private static final String VISTA_LOGIN = "/LoginVista.fxml";
    private static final String VISTA_DASHBOARD = "/MainDashboardVista.fxml";
    private static final String VISTA_CREAR_ENVIO = "/CreateShipmentVista.fxml";
    private static final String VISTA_HISTORIAL = "/ShipmentHistoryVista.fxml";
    private static final String VISTA_DETALLES = "/ShipmentDetailsVista.fxml";
    private static final String VISTA_PERFIL = "/ProfileManagementVista.fxml";
    /**
     * Abre la ventana de Login. Se usa al iniciar la app o al cerrar sesión.
     */
    public static void abrirLogin(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(VISTA_LOGIN));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Plataforma Logística - Acceso");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void abrirDashboard(Stage stageAnterior, Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(VISTA_DASHBOARD));
            Parent root = loader.load();

            MainDashboardController controller = loader.getController();
            controller.initData(usuario);

            Stage stage = stageAnterior;
            stage.setTitle("Plataforma Logística - Panel Principal");
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void abrirCrearEnvio(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(VISTA_CREAR_ENVIO));
            Parent root = loader.load();

            CreateShipmentController controller = loader.getController();
            controller.initData(usuario);

            Stage stage = new Stage();
            stage.setTitle("Crear Nuevo Envío");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void abrirHistorial(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(VISTA_HISTORIAL));
            Parent root = loader.load();

            ShipmentHistoryController controller = loader.getController();
            controller.initData(usuario);

            Stage stage = new Stage();
            stage.setTitle("Historial de Envíos");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void abrirDetallesEnvio(Envio envio) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(VISTA_DETALLES));
            Parent root = loader.load();

            ShipmentDetailsController controller = loader.getController();
            controller.initData(envio);

            Stage stage = new Stage();
            stage.setTitle("Detalles del Envío " + envio.getIdEnvio());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void abrirPerfil(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(VISTA_PERFIL));
            Parent root = loader.load();

            ProfileManagementController controller = loader.getController();
            controller.initData(usuario);

            Stage stage = new Stage();
            stage.setTitle("Mi Perfil");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
