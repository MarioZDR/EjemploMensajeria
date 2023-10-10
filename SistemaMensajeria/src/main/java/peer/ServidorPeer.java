/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import mensajeria.Conexion;

/**
 *
 * @author mario
 */
public class ServidorPeer implements Runnable {

    private ServerSocket servidor;
    private BufferedReader lector;
    private PrintWriter escritor;

    private Conexion suscriptor;
    private final int PUERTO;

    public ServidorPeer(int PUERTO, Conexion suscriptor) {
        this.PUERTO = PUERTO;
        this.suscriptor = suscriptor;
    }

    @Override
    public void run() {
        try {
            servidor = new ServerSocket(this.PUERTO);
            System.out.println("Servidor escuchando en el puerto "+this.PUERTO);
            
            while (true) {
                System.out.println("Esperando respuesta..");
                Socket clienteSocket = servidor.accept();
                System.out.println("Se conecto uno..");
                lector = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
                
                String datosRecibidos = lector.readLine();
                this.suscriptor.recibirDatos(datosRecibidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
