/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peer;

import excepciones.ConexionException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

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
            registrarPeerIndexServer();
        } catch (Exception e) {
            System.out.println("No se pudo conectar al servidor de indices");
        }
    }

    private void registrarPeerIndexServer() {
        salidaServidorIndices.println(this.host + ":" + this.puerto);
    }

    public void enviarDatosBroadcast(String datos) throws ConexionException {
        for (Socket peer : peersCercanos) {
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

    public List<String> obtenerPeers() throws ConexionException {
        try {
            salidaServidorIndices.println("FIND_PEERS");
            int numeroPeers = entradaServidorIndices.readInt();
            List<String> direcciones = new ArrayList<>();
            for (int i = 0; i < numeroPeers; i++) {
                direcciones.add(entradaServidorIndices.readUTF());
            }
            return direcciones;
        } catch (IOException ex) {
            throw new ConexionException("No se pudo conectar con los otros usuarios");
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
            for (Socket peersCercano : peersCercanos) {
                if (peersCercano != null && !peersCercano.isClosed()) {
                    peersCercano.close();
                }
            }
        } catch (IOException ex) {
            System.out.println("No se pudo cerrar la conexion con los otros peers");
        }
    }

    public void conectarPeers(List<String> peersObtenidos) throws ConexionException {
        this.peersCercanos = new ArrayList<>();
        for (String peersObtenido : peersObtenidos) {
            String[] direccion = peersObtenido.split(":");
            String hostPeer = direccion[0];
            int puertoPeer = Integer.parseInt(direccion[1]);
            if (!(hostPeer.equalsIgnoreCase(this.host) && puertoPeer == this.puerto)) {
                Socket socket;
                try {
                    socket = new Socket(hostPeer, puertoPeer);
                    peersCercanos.add(socket);
                } catch (IOException ex) {
                    throw new ConexionException("No se pudo conectar con los otros usuarios");
                }
            }
        }
    }

    @Override
    public void run() {

    }
}
