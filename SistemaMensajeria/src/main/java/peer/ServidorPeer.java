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
import java.io.IOException;

/**
 * ServidorPeer.class
 * @author
 */
public class ServidorPeer implements Runnable {

    private final int PUERTO;
    private ServerSocket servidor;
    
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
                new ServidorPeerHilo(clienteSocket, suscriptor).start();
            }
        } catch (Exception e) {
            this.cerrarServidor();
            System.out.println("Conexion finalizada");
        }
    }
    
    public void cerrarServidor(){
        try{
            this.servidor.close();
        }catch(IOException ex){
            System.out.println("No se pudo cerrar el servidor del peer");
        }
    }
    
    public void agregarSuscriptor(Conexion suscriptor){
        this.suscriptor = suscriptor;
    }
    
}

class ServidorPeerHilo extends Thread {

    private Socket socket;
    private BufferedReader lector;
    private Conexion suscriptor;

    public ServidorPeerHilo(Socket socket, Conexion suscriptor) {
        this.socket = socket;
        this.suscriptor = suscriptor;
    }

    @Override
    public void run() {
        try {
            while (true) {
                lector = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                String datosRecibidos = lector.readLine();
                this.suscriptor.recibirDatos(datosRecibidos);
            }
        } catch (IOException e) {
            this.cerrarConexion();
        } catch (Exception ex) {}
    }
    
    public void cerrarConexion(){
        try{
            this.socket.close();
        }catch(IOException ex){
            System.out.println("No se pudo cerrar la conexion con el servidor del peer");
        }
    }
}
