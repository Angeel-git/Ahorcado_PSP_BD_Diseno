package com.example.ahorcado;

import Tests.Servidor;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LanzadorApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //Llamamos a la clase Escenas para cargar la escena inicial
        Escenas escena = new Escenas();
        escena.cargarEscenaInicial(stage);

        // Ponemos el icono de la aplicación
        try {
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("fondo.jpg")));
            stage.getIcons().add(icon);
        } catch (NullPointerException e) {
            System.err.println("Error al cargar el icono.");
        }

        // Iniciar servidor al abrir la aplicación
        LanzarServidor();
    }

    private void LanzarServidor() {
        Thread serverThread = new Thread(() -> {
            try {
                Servidor.main(new String[]{});
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.setDaemon(true);
        serverThread.start();
        System.out.println("Servidor lanzado con éxito");
    }

    public static void main(String[] args) {
        launch();
    }
}
