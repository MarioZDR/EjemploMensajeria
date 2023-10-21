/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empaquetamiento;

/**
 *
 * @author mario
 */
public class Mensaje extends Evento{
    
    private String nombreJugador;
    private String mensaje;

    public Mensaje(String nombreJugador, String mensaje, TipoEvento tipo) {
        super(tipo);
        this.nombreJugador = nombreJugador;
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return nombreJugador+":"+mensaje;
    }
    
}
