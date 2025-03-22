package com.example.ahorcado;

import javafx.event.ActionEvent;
import Tests.Servidor;  //Importamos la clase servidor del otro paquete

import java.io.IOException;

public class ControllerMenu {

    private Escenas escena = new Escenas();


    public void initialize()
    {
        System.out.println("Estas en -> Menu");
        // Iniciar servidor al abrir la escena "Menu"
        LanzarServidor();
    }

    public void EscenaRegistro(ActionEvent event) {
        // Cargar la escena del juego
        escena.cargarEscenaRegistro(event);
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
}