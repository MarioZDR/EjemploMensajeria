/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import empaquetamiento.Movimiento;
import peer.Peer;
import com.fasterxml.jackson.databind.ObjectMapper;
import mensajeria.Mensajeria;

/**
 *
 * @author mario
 */
public class Conexion {
 
    private Mensajeria suscriptor;
    
    private Peer peer;
    private final int PUERTO = 4002; // 4000 4001 4002 4003 4004

    public Conexion() {
        this.peer = new Peer(PUERTO);
        this.peer.agregarSuscriptor(this);
        this.peer.iniciar();
    }
    
    public void agregarSuscriptor(Mensajeria suscriptor){
        this.suscriptor = suscriptor;
    }
    
    public void enviarDatos(Movimiento movimiento) throws Exception{
        String json = movimiento.convertirAJSON();
        peer.enviarDatos(json);
    }
    
    public void recibirDatos(String json) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Movimiento movimiento = objectMapper.readValue(json, Movimiento.class);
        suscriptor.recibirMensaje(movimiento);
    }
    
}
