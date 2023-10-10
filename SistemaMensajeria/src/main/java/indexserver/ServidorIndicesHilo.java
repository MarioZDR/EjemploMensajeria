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
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream());) {
            while (true) {
                String linea = entrada.readLine();
                if (linea.equals("FIND_PEERS")) {
                    List<String> peersDisponibles = ServidorDeIndices.consultarPeers();

                    salida.writeInt(peersDisponibles.size());

                    for (String direccionPeer : peersDisponibles) {
                        salida.writeUTF(direccionPeer);
                    }
                } else {
                    ServidorDeIndices.registrarPeer(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
