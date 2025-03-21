package com.example.ahorcado;

import javafx.event.ActionEvent;

public class ControllerMenu {

    private Escenas escena = new Escenas();


    public void initialize()
    {
        System.out.println("Estas en -> Menu");
    }




    public void EscenaJuego(ActionEvent event) {
        escena.cargarEscena1(event);
    }
}
