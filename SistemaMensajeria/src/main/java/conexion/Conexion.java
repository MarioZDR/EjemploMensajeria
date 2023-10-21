/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import peer.Peer;
import empaquetamiento.Evento;
import excepciones.ConexionException;
import mensajeria.Mensajeria;

/**
 * Conexion.class
 * @author 
 */
public class Conexion {
    
    private Mensajeria suscriptor;
    
    private Peer peer;
    private final int PUERTO = 4005; // 4000 4001 4002 4003 4004

    public Conexion() {
        this.peer = new Peer(PUERTO);
        this.peer.agregarSuscriptor(this);
        this.peer.iniciar();
    }
    
    public void cerrarConexiones(){
        this.peer.desconectar();
    }
    
    public void agregarSuscriptor(Mensajeria suscriptor) {
        this.suscriptor = suscriptor;
    }
    
    public void enviarDatos(Evento datos) throws ConexionException {
        try {
            peer.enviarDatos(datos);
        } catch (ConexionException ex) {
            throw ex;
        }
    }
    
    public void recibirDatos(Evento datos) throws Exception {
        suscriptor.recibirMensaje(datos);
    }
    
}
