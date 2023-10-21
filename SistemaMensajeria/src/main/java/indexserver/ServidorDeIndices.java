package indexserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * ServidorDeIndices.class
 * @author
 */
public class ServidorDeIndices {

    private static List<String> peersRegistrados = new ArrayList<>();
    public static int PUERTO_SERVIDOR_INDICES = 3999;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO_SERVIDOR_INDICES);
            System.out.println("Index Server ejecutandose en " + PUERTO_SERVIDOR_INDICES);

            while (true) {
                Socket socket = serverSocket.accept();
                new ServidorIndicesHilo(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Error al levantar el servidor de indices");
        }
    }

    // Método para registrar un par en el servidor de índices
    public static void registrarPeer(String peer) {
        System.out.println("Entro un peer a la red");
        peersRegistrados.add(peer);
    }

    // Método para buscar pares disponibles en el servidor de índices
    public static List<String> consultarPeers() {
        return (peersRegistrados);
    }

    public static void borrarRegistroPeer(String peer) {
        System.out.println("Un peer salio de la red");
        peersRegistrados.remove(peer);
    }
}

class ServidorIndicesHilo extends Thread {

    private Socket socket;
    private String direccion;

    public ServidorIndicesHilo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            while (true) {
                String linea = entrada.readLine();
                if (linea == null) {
                    this.borrarPeer();
                    break;
                }
                if (linea.equals("CONSULTAR")) {
                    List<String> peersDisponibles = ServidorDeIndices.consultarPeers();

                    salida.writeInt(peersDisponibles.size());

                    for (String direccionPeer : peersDisponibles) {
                        salida.writeUTF(direccionPeer);
                    }
                } else {
                    ServidorDeIndices.registrarPeer(linea);
                    this.direccion = linea;
                }
            }
        } catch (IOException e) {
            this.borrarPeer();
            this.cerrarServidor();
        }
    }

    private void borrarPeer() {
        ServidorDeIndices.borrarRegistroPeer(this.direccion);
    }

    public void cerrarServidor() {
        try {
            this.socket.close();
        } catch (IOException ex) {
            System.out.println("No se pudo cerrar el servidor del peer");
        }
    }
}
