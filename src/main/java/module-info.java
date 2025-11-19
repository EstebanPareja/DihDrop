module co.edu.uniquindio.poo.dihdrop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens co.edu.uniquindio.poo.dihdrop.Controller to javafx.fxml;
    opens co.edu.uniquindio.poo.dihdrop.Model to javafx.base, javafx.fxml;
    exports co.edu.uniquindio.poo.dihdrop;
    exports co.edu.uniquindio.poo.dihdrop.Controller;
}
