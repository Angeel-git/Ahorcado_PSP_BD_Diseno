package Tests;

import javafx.scene.control.Alert;

public class Alertas {
    //Static para que se pueda invocar sin instanciar la clase
    public static void error(String titulo, String cabercero, String contenido){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(cabercero);
        a.setContentText(contenido);
        a.showAndWait();
    }
}
