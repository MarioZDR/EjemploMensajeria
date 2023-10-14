/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peer;

import excepciones.ConexionException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClientePeer.class
 * @author
 */
public class ClientePeer implements Runnable {

    private String host = "localhost";
    private int puerto;

    private DataInputStream entradaServidorIndices;
    private PrintWriter salidaServidorIndices;
    private Socket socketServidorIndices;
    private final int puertoServidorIndices = 3999;

    private Map<String, Socket> peersCercanos;

    public ClientePeer(int puerto) {
        this.puerto = puerto;
    }

    public void conectarServidorIndices() {
        try {
            this.peersCercanos = new HashMap<>();
            socketServidorIndices = new Socket(host, puertoServidorIndices);
            entradaServidorIndices = new DataInputStream(this.socketServidorIndices.getInputStream());
            salidaServidorIndices = new PrintWriter(socketServidorIndices.getOutputStream(), true);
            registrarPeerIndexServer();
        } catch (Exception e) {
            System.out.println("No se pudo conectar al servidor de indices");
            System.exit(0);
        }
    }

    private void registrarPeerIndexServer() {
        salidaServidorIndices.println(this.host + ":" + this.puerto);
    }

    public void enviarDatosBroadcast(String datos) throws ConexionException {
        for (Socket peer : peersCercanos.values()) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(peer.getOutputStream(), true);
                writer.println(datos);
            } catch (IOException ex) {
                throw new ConexionException("No se pudo enviar el mensaje");
            }
        }
    }

    public void enviarDatos(String datos) throws ConexionException {
        try {
            List<String> direcciones = this.obtenerPeers();
            this.conectarPeers(direcciones);
            this.enviarDatosBroadcast(datos);
        } catch (ConexionException ex) {
            throw ex;
        }
    }

    public List<String> obtenerPeers(){
        try {
            if (!socketServidorIndices.isClosed()) {
                salidaServidorIndices.println("CONSULTAR");
                int numeroPeers = entradaServidorIndices.readInt();
                List<String> direcciones = new ArrayList<>();
                for (int i = 0; i < numeroPeers; i++) {
                    direcciones.add(entradaServidorIndices.readUTF());
                }
                return direcciones;
            }
            return new ArrayList<>();
        } catch (IOException ex) {
            this.cerrarConexionServidorIndices();
            return new ArrayList<>();
        }
    }

    public void cerrarConexionServidorIndices() {
        try {
            socketServidorIndices.close();
        } catch (IOException ex) {
            System.out.println("No se pudo cerrar la conexion con el servidor de indices");
        }
    }

    public void cerrarConexiones() {
        try {
            for (Socket peersCercano : peersCercanos.values()) {
                if (peersCercano != null && !peersCercano.isClosed()) {
                    peersCercano.close();
                }
            }
        } catch (IOException ex) {
            System.out.println("No se pudo cerrar la conexion con los otros peers");
        }
    }

    public void conectarPeers(List<String> peersObtenidos) throws ConexionException {
        for (String peersObtenido : peersObtenidos) {
            String[] direccion = peersObtenido.split(":");
            String hostPeer = direccion[0];
            int puertoPeer = Integer.parseInt(direccion[1]);
            if (!(hostPeer.equalsIgnoreCase(this.host) && puertoPeer == this.puerto)) {
                if (!peersCercanos.containsKey(peersObtenido)) {
                    Socket socket;
                    try {
                        socket = new Socket(hostPeer, puertoPeer);
                        peersCercanos.put(peersObtenido, socket);
                    } catch (IOException ex) {
                        throw new ConexionException("No se pudo conectar con los otros usuarios");
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        this.conectarServidorIndices();
    }
}
