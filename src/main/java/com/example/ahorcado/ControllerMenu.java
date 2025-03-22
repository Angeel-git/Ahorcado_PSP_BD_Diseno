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
        // Cargar la escena del juego
        escena.cargarEscena1(event);
    }
}