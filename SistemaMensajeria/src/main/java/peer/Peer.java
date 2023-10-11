/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package peer;

import conexion.Conexion;
import java.io.IOException;

/**
 *
 * @author mario
 */
public class Peer {

    private final int PUERTO;
    private ClientePeer ladoCliente;
    private ServidorPeer ladoServidor;

    public Peer(int PUERTO) {
        this.PUERTO = PUERTO;
        ladoCliente = new ClientePeer(PUERTO);
        ladoServidor = new ServidorPeer(PUERTO);
    }

    public void iniciar() {
       Thread hiloLadoCliente = new Thread(ladoCliente);
       Thread hiloLadoServer = new Thread(ladoServidor);
       hiloLadoCliente.start();
       hiloLadoServer.start();
    }

    public void enviarDatos(String datos) throws IOException{
        this.ladoCliente.enviarDatos(datos);
    }
    
    public void agregarSuscriptor(Conexion conexion){
        this.ladoServidor.agregarSuscriptor(conexion);
    }
    
    public int getPUERTO() {
        return PUERTO;
    }

    public ClientePeer getLadoCliente() {
        return ladoCliente;
    }

    public ServidorPeer getLadoServidor() {
        return ladoServidor;
    }

}