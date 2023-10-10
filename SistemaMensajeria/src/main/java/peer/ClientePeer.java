/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peer;

import indexserver.NodosCercanos;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class ClientePeer implements Runnable {

    private String serverAddress = "localhost";
    private int serverPort;
    
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream objIn;

    private Socket socketIndexServer;
    private final int portIndexServer = 3999;
    
    private List<Socket> peersCercanos;

    public ClientePeer(int serverPort) {
        this.peersCercanos = new ArrayList<>();
        this.serverPort = serverPort;
    }

    public void connectToServer() {
        try {
            socketIndexServer = new Socket(serverAddress, portIndexServer);
            in = new BufferedReader(new InputStreamReader(socketIndexServer.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar");
        }
    }

    public void sendMessageBroadcast(String message) {
        for (Socket peer : peersCercanos) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(peer.getOutputStream(), true);
                writer.println(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void obtenerPeers() {
        try {
            out = new PrintWriter(socketIndexServer.getOutputStream(), true);
            objIn = new ObjectInputStream(socketIndexServer.getInputStream());
            
            NodosCercanos nodosCercanos = (NodosCercanos)objIn.readObject();
            List<String> peersObtenidos = nodosCercanos.getNodosCercanos();
            
            this.conectarPeers(peersObtenidos);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void conectarPeers(List<String> peersObtenidos) throws IOException{
        for (String peersObtenido : peersObtenidos) {
            String [] direccion = peersObtenido.split(":");
            Socket socket = new Socket(direccion[0],Integer.parseInt(direccion[1]));
            peersCercanos.add(socket);
        }
    }
    
    @Override
    public void run() {
        System.out.println("Corriendo peer");
        this.connectToServer();
        this.obtenerPeers();
        System.out.println("Hay "+this.peersCercanos.size());
    }
}
