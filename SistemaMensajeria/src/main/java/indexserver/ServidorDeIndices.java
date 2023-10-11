package indexserver;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author mario
 */
public class ServidorDeIndices {
    
    private static List<String> peersRegistrados = new ArrayList<>();
    public static int PORT_SERVIDOR_INDICES = 3999; 

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT_SERVIDOR_INDICES)) {
            System.out.println("Index Server ejecutandose en " + PORT_SERVIDOR_INDICES);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Entro un peer a la red");
                new ServidorIndicesHilo(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Error al levantar el servidor de indices");
        }
    }
    
    // Método para registrar un par en el servidor de índices
    public static void registrarPeer(String peer) {
        peersRegistrados.add(peer);
    }

    // Método para buscar pares disponibles en el servidor de índices
    public static List<String> consultarPeers() {
        return (peersRegistrados);
    }
    
    public static void borrarRegistroPeer(String peer){
        peersRegistrados.remove(peer);
    }
}

