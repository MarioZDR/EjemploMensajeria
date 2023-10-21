/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empaquetamiento;

/**
 *
 * @author mario
 */
public class SacarFichaPozo extends Evento {

    private String nombreJugador;
    private String numeroFicha;

    public SacarFichaPozo(String nombreJugador, String numeroFicha, TipoEvento tipo) {
        super(tipo);
        this.nombreJugador = nombreJugador;
        this.numeroFicha = numeroFicha;
    }

    @Override
    public String toString() {
        return nombreJugador+" saco la ficha "+numeroFicha;
    }

    
    
}
