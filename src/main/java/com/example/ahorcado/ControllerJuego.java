package com.example.ahorcado;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ControllerJuego {
    @FXML private Label estadoJuego; // Muestra el estado del juego
    @FXML private TextField inputLetra; // Campo de texto para ingresar una letra
    @FXML private Button botonEnviar; // Botón para enviar la letra

    private Socket cliente;
    private DataOutputStream flujoSalida;
    private DataInputStream flujoEntrada;

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
                Platform.runLater(() -> estadoJuego.setText("Conectado al servidor"));
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> estadoJuego.setText("Error al conectar con el servidor"));
            }
        }).start();
    }

    // Metodo para enviar una letra al servidor
    @FXML
    private void enviarLetra() {
        String letra = inputLetra.getText().trim();
        if (letra.isEmpty() || letra.length() > 1) {
            estadoJuego.setText("Introduce solo una letra.");
            return;
        }

        // Limpiar el campo de entrada
        inputLetra.clear();

        new Thread(() -> {
            try {
                flujoSalida.writeUTF(letra); // Enviar letra al servidor
                String respuesta = flujoEntrada.readUTF(); // Recibir respuesta del servidor

                Platform.runLater(() -> estadoJuego.setText(respuesta)); // Mostrar el estado en la UI

                // Si la partida ha terminado, cerrar la conexión
                if (respuesta.contains("Partida finalizada.")) {
                    cerrarConexion();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> estadoJuego.setText("Error al enviar la letra."));
            }
        }).start();
    }

    // Metodo para cerrar la conexión con el servidor
    private void cerrarConexion() {
        try {
            if (flujoSalida != null) flujoSalida.close();
            if (flujoEntrada != null) flujoEntrada.close();
            if (cliente != null) cliente.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
