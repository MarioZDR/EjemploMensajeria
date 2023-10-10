/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import conexion.Conexion;

/**
 *
 * @author mario
 */
public class ServidorPeer implements Runnable {

    private final int PUERTO;
    private ServerSocket servidor;
    private BufferedReader lector;

    private Conexion suscriptor;
    

    public ServidorPeer(int PUERTO) {
        this.PUERTO = PUERTO;
    }

    @Override
    public void run() {
        try {
            servidor = new ServerSocket(this.PUERTO);
            
            while (true) {
                Socket clienteSocket = servidor.accept();
                lector = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                String datosRecibidos = lector.readLine();
                this.suscriptor.recibirDatos(datosRecibidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void agregarSuscriptor(Conexion suscriptor){
        this.suscriptor = suscriptor;
    }
}
