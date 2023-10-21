/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empaquetamiento;

/**
 *
 * @author mario
 */
public class MoverFicha extends Evento{
    
    private String nombreJugador;
    private String numeroFicha;

    public MoverFicha(String nombreJugador, String numeroFicha, TipoEvento tipo) {
        super(tipo);
        this.nombreJugador = nombreJugador;
        this.numeroFicha = numeroFicha;
    }

    @Override
    public String toString() {
        return nombreJugador+" movio la ficha "+numeroFicha;
    }

    
    
}
