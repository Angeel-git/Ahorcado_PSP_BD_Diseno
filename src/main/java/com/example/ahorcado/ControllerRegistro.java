package com.example.ahorcado;

import Tests.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Tests.Hibernate;
import Tests.Jugador;

public class ControllerRegistro {

    @FXML private TextField inputNombre1; // Campo para el nombre del Jugador 1

    public static Jugador jugador1;

    @FXML
    private void initialize() {

    }

    public void irAJuego(ActionEvent event) {
        // Si los nombres se registran correctamente, carga la escena
        if (registrarNombres()) {
            Escenas escena = new Escenas();
            escena.cargarEscena1(event);
        } else {
            System.out.println("No se pudo registrar a los jugadores. Verifique los nombres.");
        }
    }

    private boolean registrarNombres() {
        String nombre1 = inputNombre1.getText().trim();

        if (nombre1.isEmpty()) {
            System.out.println("Por favor, introduce todos los nombres.");
            Alertas alerta = new Alertas();
            Alertas.error("Nombre vacio", "Nombre vacio", "Debes introducir el nombre del jugador");
            return false;  // Devolvemos false si faltan nombres
        }
        // Jugador 1
        if (!Hibernate.jugadorExiste(nombre1)) {
            jugador1 = new Jugador();
            jugador1.setNombre(nombre1);
            Hibernate.insertarJugador(jugador1);
            System.out.println("Jugador 1 registrado: " + nombre1);
        } else {
            jugador1 = Hibernate.obtenerJugador(nombre1);
            System.out.println("Jugador 1 ya existía: " + nombre1);
        }
        return true;
    }
}
