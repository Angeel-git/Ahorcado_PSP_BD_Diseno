package com.example.ahorcado;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Escenas {
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Cargar escena inicial desde el lanzador
    public void cargarEscenaInicial(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LanzadorApp.class.getResource("Menu.fxml"));
            root = fxmlLoader.load();

            // Obtener tamaño de pantalla
            Rectangle2D limitePantalla = Screen.getPrimary().getVisualBounds();
            double ancho = limitePantalla.getWidth() * 0.8;
            double alto = limitePantalla.getHeight() * 0.8;

            // Configurar la escena
            scene = new Scene(root, ancho, alto);
            stage.setScene(scene);
            stage.setTitle("Ahorcado");

            // Centrar la ventana
            stage.setX(limitePantalla.getMinX() + (limitePantalla.getWidth() - ancho) / 2);
            stage.setY(limitePantalla.getMinY() + (limitePantalla.getHeight() - alto) / 2);

            stage.show();
        } catch (IOException e) {
            System.err.println("Error al cargar la escena inicial: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void cargarEscenaRegistro(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LanzadorApp.class.getResource("Registro.fxml"));
            root = fxmlLoader.load();

            // Obtener tamaño de pantalla
            Rectangle2D limitePantalla = Screen.getPrimary().getVisualBounds();
            double ancho = limitePantalla.getWidth() * 0.8;
            double alto = limitePantalla.getHeight() * 0.8;

            // Obtener el stage desde el evento
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crear nueva escena con tamaño dinámico
            scene = new Scene(root, ancho, alto);
            stage.setScene(scene);

            // Centrar la ventana
            stage.setX(limitePantalla.getMinX() + (limitePantalla.getWidth() - ancho) / 2);
            stage.setY(limitePantalla.getMinY() + (limitePantalla.getHeight() - alto) / 2);

            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cambiar de escena: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Carcar escena del juego
    public void cargarEscena1(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LanzadorApp.class.getResource("Juego.fxml"));
            root = fxmlLoader.load();

            // Obtener tamaño de pantalla
            Rectangle2D limitePantalla = Screen.getPrimary().getVisualBounds();
            double ancho = limitePantalla.getWidth() * 0.8;
            double alto = limitePantalla.getHeight() * 0.8;

            // Obtener el stage desde el evento
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crear nueva escena con tamaño dinámico
            scene = new Scene(root, ancho, alto);
            stage.setScene(scene);

            // Centrar la ventana
            stage.setX(limitePantalla.getMinX() + (limitePantalla.getWidth() - ancho) / 2);
            stage.setY(limitePantalla.getMinY() + (limitePantalla.getHeight() - alto) / 2);

            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cambiar de escena: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Carcar escena del menu desde cualquier otra escena
    public void cargarEscena0(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LanzadorApp.class.getResource("Menu.fxml"));
            root = fxmlLoader.load();

            // Obtener tamaño de pantalla
            Rectangle2D limitePantalla = Screen.getPrimary().getVisualBounds();
            double ancho = limitePantalla.getWidth() * 0.8;
            double alto = limitePantalla.getHeight() * 0.8;

            // Obtener el stage desde el evento
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Crear nueva escena con tamaño dinámico
            scene = new Scene(root, ancho, alto);
            stage.setScene(scene);

            // Centrar la ventana
            stage.setX(limitePantalla.getMinX() + (limitePantalla.getWidth() - ancho) / 2);
            stage.setY(limitePantalla.getMinY() + (limitePantalla.getHeight() - alto) / 2);

            stage.show();

        } catch (IOException e) {
            System.err.println("Error al cambiar de escena: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
