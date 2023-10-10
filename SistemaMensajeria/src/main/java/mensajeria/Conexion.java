/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mensajeria;

import peer.Peer;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author mario
 */
public class Conexion {
 
    private Mensajeria suscriptor;
    
    private Peer peer;
    private final int PUERTO = 4001; // 4000 4001 4002 4003 4004

    public Conexion() {
        this.peer = new Peer(PUERTO, this);
        this.peer.iniciar();
    }
    
    public void enviarDatos(Movimiento movimiento) throws Exception{
        String json = movimiento.convertirAJSON();
        peer.getLadoCliente().sendMessageBroadcast(json);
    }
    
    public void recibirDatos(String json) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Movimiento movimiento = objectMapper.readValue(json, Movimiento.class);
        suscriptor.recibirMensaje(movimiento);
    }
    
}
