/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import empaquetamiento.Movimiento;
import peer.Peer;
import com.fasterxml.jackson.databind.ObjectMapper;
import excepciones.ConexionException;
import mensajeria.Mensajeria;

/**
 *
 * @author mario
 */
public class Conexion {
    
    private Mensajeria suscriptor;
    
    private Peer peer;
    private final int PUERTO = 4008; // 4000 4001 4002 4003 4004

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
    
    public void enviarDatos(Movimiento movimiento) throws ConexionException {
        try {
            String json = this.empaquetarDatos(movimiento);
            peer.enviarDatos(json);
        } catch (ConexionException ex) {
            throw ex;
        }
    }
    
    public String empaquetarDatos(Movimiento movimiento) throws ConexionException {
        try {
            return movimiento.convertirAJSON();
        } catch (Exception ex) {
            throw new ConexionException("Hubo un error al enviar el mensaje en el empaquetado de datos");
        }
    }
    
    public void recibirDatos(String json) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Movimiento movimiento = objectMapper.readValue(json, Movimiento.class);
        suscriptor.recibirMensaje(movimiento);
    }
    
}
