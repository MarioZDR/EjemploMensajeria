/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package indexserver;


import java.io.*;
import java.net.*;
import java.util.List;

/**
 *
 * @author mario
 */
public class ServidorIndicesHilo extends Thread {
    
    private Socket socket;

    public ServidorIndicesHilo(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            String action = in.readLine();
            if (action.equals("FIND_PEERS")) {
                System.out.println("Se obtiene una peticion de encontrar peers");
                List<String> availablePeers = ServidorDeIndices.consultarPeers();
                NodosCercanos nodos = new NodosCercanos(availablePeers);
                ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
                objOut.writeObject(nodos);
            }else{
                ServidorDeIndices.registrarPeer(action);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
