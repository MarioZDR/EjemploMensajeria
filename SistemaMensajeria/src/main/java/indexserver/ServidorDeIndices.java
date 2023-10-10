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
                registrarPeer(socket);
                System.out.println("Ahora hay "+consultarPeers().size());
                new ServidorIndicesHilo(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para registrar un par en el servidor de índices
    public static synchronized void registrarPeer(Socket peer) {
        peersRegistrados.add(peer.getLocalSocketAddress().toString().substring(1));
    }

    // Método para buscar pares disponibles en el servidor de índices
    public static synchronized List<String> consultarPeers() {
        return (peersRegistrados);
    }
}
