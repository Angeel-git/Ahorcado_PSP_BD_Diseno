package com.example.ahorcado;

import javafx.event.ActionEvent;
import Tests.Servidor;  //Importamos la clase servidor del otro paquete

import java.io.IOException;

public class ControllerMenu {

    private Escenas escena = new Escenas();



    public void initialize()
    {
        System.out.println("Estas en -> Menu");
    }



    public void EscenaJuego(ActionEvent event) {
        // Iniciar el servidor en un hilo separado
        Thread serverThread = new Thread(() -> {
            try {
                Servidor.main(new String[]{}); // Ejecutar servidor
            } catch (IOException e) {
                e.printStackTrace(); // Mostrar el error en la consola
            }
        });

        serverThread.setDaemon(true); // Permite que el servidor se cierre al cerrar la app
        serverThread.start();

        System.out.println("Servidor lanzado con éxito");

        // Cargar la escena del juego
        escena.cargarEscena1(event);
    }
}