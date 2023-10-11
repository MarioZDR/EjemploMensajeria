/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class ClientePeer implements Runnable {

    private String host = "localhost";
    private int puerto;

    private DataInputStream entradaServidorIndices;
    private PrintWriter salidaServidorIndices;
    private Socket socketServidorIndices;
    private final int puertoServidorIndices = 3999;

    private List<Socket> peersCercanos;

    public ClientePeer(int puerto) {
        this.puerto = puerto;
    }

    public void conectarServidorIndices() {
        try {
            socketServidorIndices = new Socket(host, puertoServidorIndices);
            entradaServidorIndices = new DataInputStream(this.socketServidorIndices.getInputStream());
            salidaServidorIndices = new PrintWriter(socketServidorIndices.getOutputStream(), true);
            salidaServidorIndices.println(this.host + ":" + this.puerto);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar al servidor de indices");
        }
    }

    public void enviarDatosBroadcast(String datos) {
        for (Socket peer : peersCercanos) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(peer.getOutputStream(), true);
                writer.println(datos);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void enviarDatos(String datos) throws IOException{
        List<String> direcciones = this.obtenerPeers();
        this.conectarPeers(direcciones);
        this.enviarDatosBroadcast(datos);
    }
    
    public List<String> obtenerPeers() {
        try {
            salidaServidorIndices.println("FIND_PEERS");
            int numeroPeers = entradaServidorIndices.readInt();
            List<String> direcciones = new ArrayList<>();
            for (int i = 0; i < numeroPeers; i++) {
                direcciones.add(entradaServidorIndices.readUTF());
            }
            return direcciones;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void conectarPeers(List<String> peersObtenidos) throws IOException {
        this.peersCercanos = new ArrayList<>();
        for (String peersObtenido : peersObtenidos) {
            String[] direccion = peersObtenido.split(":");
            String hostPeer = direccion[0];
            int puertoPeer = Integer.parseInt(direccion[1]);
            if (!(hostPeer.equalsIgnoreCase(this.host) && puertoPeer == this.puerto)) {
                Socket socket = new Socket(hostPeer, puertoPeer);
                peersCercanos.add(socket);
            }
        }
    }

    @Override
    public void run() {
        this.conectarServidorIndices();
    }
}
