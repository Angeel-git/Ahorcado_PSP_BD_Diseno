package Tests;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        String Host = "localhost";
        int Puerto = 6000;
        Socket cliente = new Socket(Host, Puerto);

        Scanner sc = new Scanner(System.in);

        DataOutputStream flujoSalida = new DataOutputStream(cliente.getOutputStream());
        DataInputStream flujoEntrada = new DataInputStream(cliente.getInputStream());

        while (true){
            System.out.print("Introduce una letra: ");
            String letra = sc.nextLine();

            flujoSalida.writeUTF(letra);

            String estado = flujoEntrada.readUTF();
            System.out.println(estado);

            if (estado.contains("Partida finalizada.")) {
                break;
            }
        }

        flujoSalida.close();
        flujoEntrada.close();
        cliente.close();
        sc.close();
    }
}
