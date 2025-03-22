package com.example.ahorcado;

import Tests.Hibernate;
import Tests.Partida;
import Tests.Palabra;
import Tests.Jugador;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ControllerJuego {

    @FXML public Label estadoJuego; // Muestra la palabra con las letras adivinadas
    @FXML private Label estadoServidor; // Indica si hay conexión con el servidor
    @FXML private TextField inputLetra; // Campo de texto para ingresar una letra

    private Socket cliente;
    private DataOutputStream flujoSalida;
    private DataInputStream flujoEntrada;

    private Jugador jugador; // Variable para almacenar al jugador
    private Palabra palabra; // Variable para almacenar la palabra

    public void initialize() {
        System.out.println("Estas en -> Juego");
        conectarServidor();
    }

    // Metodo para conectarse al servidor
    private void conectarServidor() {
        new Thread(() -> {
            try {
                cliente = new Socket("localhost", 6000);
                flujoSalida = new DataOutputStream(cliente.getOutputStream());
                flujoEntrada = new DataInputStream(cliente.getInputStream());

                Platform.runLater(() -> {
                    estadoServidor.setText("Conectado al servidor");
                    estadoServidor.setStyle("-fx-text-fill: green;");
                });

            } catch (IOException e) {
                Platform.runLater(() -> {
                    estadoServidor.setText("Error al conectar con el servidor");
                    estadoServidor.setStyle("-fx-text-fill: red;");
                });
            }
        }).start();
    }

    // Metodo para enviar una letra al servidor (se llama desde el botón)
    @FXML
    private void enviarLetra() {
        String letra = inputLetra.getText().trim();

        if (letra.length() != 1) {
            // Validación de la letra ingresada
            return;
        }

        // Limpiar el campo de entrada
        inputLetra.clear();

        new Thread(() -> {
            try {
                flujoSalida.writeUTF(letra); // Enviar letra al servidor
                String respuesta = flujoEntrada.readUTF(); // Recibir respuesta del servidor

                Platform.runLater(() -> estadoJuego.setText(respuesta)); // Mostrar el estado en la UI

                // Si la partida ha terminado, guardar el resultado y cerrar la conexión
                if (respuesta.contains("Partida finalizada.")) {
                    guardarResultado();
                    cerrarConexion();
                }

            } catch (IOException e) {
                // Manejo de errores
            }
        }).start();
    }

    // Guardar los resultados de la partida en la base de datos
    private void guardarResultado() {
        // Asumimos que el jugador y la palabra ya están definidos
        boolean gano = estadoJuego.getText().contains("¡Ganaste!"); // Ejemplo para determinar si el jugador ganó
        int intentos = 6 - Integer.parseInt(estadoJuego.getText().replaceAll("[^0-9]", "")); // Asumimos que los intentos se muestran en la UI

        // Crear la partida y guardarla
        Partida partida = new Partida(jugador, palabra, intentos, gano);
        Hibernate.insertarPartida(partida); // Llamada al metodo insertarPartida() para guardar la partida
    }

    // Metodo para cerrar la conexión con el servidor
    private void cerrarConexion() {
        try {
            if (flujoSalida != null) flujoSalida.close();
            if (flujoEntrada != null) flujoEntrada.close();
            if (cliente != null) cliente.close();
        } catch (IOException e) {
            // Manejo de errores al cerrar la conexión
        }
    }

    public void volverMenu(ActionEvent event) {
        Escenas escena = new Escenas();
        escena.cargarEscena0(event);
    }
}
