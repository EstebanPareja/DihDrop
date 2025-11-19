package co.edu.uniquindio.poo.dihdrop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        var url = App.class.getResource("/LoginVista.fxml");
        System.out.println("URL /LoginVista.fxml = " + url);

        if (url == null) {
            throw new IllegalStateException("No se encontró /LoginVista.fxml en el classpath");
        }

        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

