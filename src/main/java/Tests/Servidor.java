package Tests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Servidor {
    public static List<Palabra> palabras = new ArrayList<>();
    public static String palabra;
    private static boolean[] letrasAdivinadas;

    private static int intentos;

    public static void main(String[] args) throws IOException {

        // Se elige un número aleatorio para obtener una palabra de la BBDD
        Random random = new Random();
        int numero = random.nextInt(50) + 1;

        // Leer palabras del fichero y guardarlo en una lista
        palabras = LecturaJSON.leerJSON();
        // Insertar mediante hibernate las palabras
        Hibernate.insertarJSON(palabras);

        // Obtener una palabra de la BBDD de forma aleatoria
        Palabra palabraHib = Hibernate.hacerConsulta(numero);
        palabra = palabraHib.getPalabra();

        letrasAdivinadas = new boolean[palabra.length()];
        intentos = palabra.length() / 2;

        //Crear el servidor en el puerto 6000
        int numeroPuerto = 6000;
        ServerSocket servidor = new ServerSocket(numeroPuerto);
        System.out.println("Esperando al Cliente...");
        //Esperar la conexión de un cliente
        Socket clienteConectado = servidor.accept();
        System.out.println("Cliente conectado");

        //flujos de entrada y salida para la comunicación con el cliente
        DataInputStream entrada = new DataInputStream(clienteConectado.getInputStream());
        DataOutputStream salida = new DataOutputStream(clienteConectado.getOutputStream());

        while (intentos > 0){
            String letra = entrada.readUTF();

            String resultado = comprobarLetraPalabra(letra);
            salida.writeUTF(resultado);

            if (intentos == 0) {
                break;
            }
        }

        entrada.close();
        salida.close();
        clienteConectado.close();
        servidor.close();

    }

    private static String comprobarLetraPalabra(String letra) {
        boolean encontrada = false;
        StringBuilder estadoActual = new StringBuilder();

        for (int i = 0; i < palabra.length(); i++) {
            if (String.valueOf(palabra.charAt(i)).equalsIgnoreCase(letra)) {
                letrasAdivinadas[i] = true;
                encontrada = true;
            }
        }

        if (!encontrada) {
            intentos--;
        }

        for (int i = 0; i < palabra.length(); i++) {
            if (letrasAdivinadas[i]) {
                estadoActual.append(palabra.charAt(i)).append(" ");
            } else {
                estadoActual.append("_ ");
            }
        }

        boolean todasAdivinadas = true;
        for (boolean letraAdivinada : letrasAdivinadas) {
            if (!letraAdivinada) {
                todasAdivinadas = false;
                break;
            }
        }

        if (todasAdivinadas) {
            return "¡Felicidades! Has adivinado la palabra: " + palabra + "\nPartida finalizada.";
        }

        if (intentos == 0) {
            return "Has perdido. La palabra era: " + palabra + "\nPartida finalizada.";
        }

        return encontrada ? "Correcto! Estado actual: " + estadoActual.toString() + " Intentos restantes: " + intentos
                : "Incorrecto! Estado actual: " + estadoActual.toString() + " Intentos restantes: " + intentos;
    }
}
