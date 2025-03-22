package com.example.ahorcado;

import Tests.Alertas;
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
    @FXML public Label estadoJuego; //Muestra la palabra con las letras adivinadas
    @FXML private Label estadoServidor; // Indica si hay conexión con el servidor
    @FXML private TextField inputLetra; // Campo de texto para ingresar una letra

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

                Platform.runLater(() -> {
                    estadoServidor.setText("Conectado al servidor");
                    estadoServidor.setStyle("-fx-text-fill: green;");
                });

            } catch (IOException e) {
                Platform.runLater(() -> {
                    estadoServidor.setText("Error al conectar con el servidor");
                    estadoServidor.setStyle("-fx-text-fill: red;");
                });

                Alertas.error("Error de conexión", "No se pudo conectar al servidor", "Verifica que el servidor está en ejecución e intenta nuevamente.");
            }
        }).start();
    }

    // Metodo para enviar una letra al servidor(se llama desde el botón)
    @FXML
    private void enviarLetra() {
        String letra = inputLetra.getText().trim();

        if (letra.length() != 1) {
            Alertas.error("Entrada inválida", "Error en el formato de la letra", "Debes introducir solo una letra.");
            return;
        }

        // Limpiar el campo de entrada
        inputLetra.clear();

        new Thread(() -> {
            try {
                flujoSalida.writeUTF(letra); // Enviar letra al servidor
                String respuesta = flujoEntrada.readUTF(); // Recibir respuesta del servidor

                Platform.runLater(() -> estadoJuego.setText(respuesta)); // Mostrar el estado en la UI

                // Si la partida ha terminado, mostrar alerta y cerrar la conexión
                if (respuesta.contains("Partida finalizada.")) {
                    cerrarConexion();
                }

            } catch (IOException e) {
                Alertas.error("Error de comunicación", "No se pudo enviar la letra", "Revisa la conexión con el servidor.");
            }
        }).start();
    }

    @FXML
    public void volverMenu(ActionEvent event) {
        Escenas escena = new Escenas();
        escena.cargarEscena0(event);
    }


    // Metodo para cerrar la conexión con el servidor
    private void cerrarConexion() {
        try {
            if (flujoSalida != null) flujoSalida.close();
            if (flujoEntrada != null) flujoEntrada.close();
            if (cliente != null) cliente.close();
        } catch (IOException e) {
            Alertas.error("Error de conexión", "No se pudo cerrar la conexión", "Hubo un problema al cerrar la conexión con el servidor.");
        }
    }


}
