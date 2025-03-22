package com.example.ahorcado;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import Tests.Hibernate;
import Tests.Jugador;

public class ControllerRegistro {

    @FXML
    private TextField inputNombre; // Campo de entrada de nombre

    @FXML
    private void registrarNombre() {
        String nombre = inputNombre.getText().trim();

        if (nombre.isEmpty()) {
            System.out.println("Por favor, introduce un nombre.");
            return;
        }

        // Crear jugador y guardarlo en la base de datos
        Jugador jugador = new Jugador();
        jugador.setNombre(nombre); // Asegúrate de que la clase Jugador tenga un setter para nombre

        Hibernate.insertarJugador(jugador);

        System.out.println("Jugador registrado: " + nombre);
    }

    public void irAJuego(ActionEvent event) {
        Escenas escena = new Escenas();
        escena.cargarEscena1(event);
    }
}
