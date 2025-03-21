package com.example.ahorcado;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LanzadorApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LanzadorApp.class.getResource("Menu.fxml"));

        //Obtenemos la dimension de la pantalla
        Rectangle2D limitePantalla = Screen.getPrimary().getVisualBounds();


        //Cargamos la ecena con el 80% del tamaño de la pantalla
        Scene scene = new Scene(fxmlLoader.load(), limitePantalla.getWidth() * 0.8, limitePantalla.getHeight() * 0.8);

        //Ponemos titulo
        stage.setTitle("Ahorcado");

        //Centramos la ventana en la pantalla
        stage.setX(limitePantalla.getMinX() + (limitePantalla.getWidth() - scene.getWidth()) / 2);
        stage.setY(limitePantalla.getMinY() + (limitePantalla.getHeight() - scene.getHeight()) / 2);

        //Ponemos foto de logo
        try {
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("fondo.jpg")));
            stage.getIcons().add(icon);
        } catch (NullPointerException e) {
            System.err.println("Error al cargar el icono.");
        }

        //Lanzamos la ventana
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}