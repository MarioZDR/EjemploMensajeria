/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empaquetamiento;

import java.io.Serializable;

/**
 *
 * @author mario
 */
public class Evento implements Serializable{
    
    protected TipoEvento tipo;

    public Evento(TipoEvento tipo) {
        this.tipo = tipo;
    }

    public TipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(TipoEvento tipo) {
        this.tipo = tipo;
    }
    
}
