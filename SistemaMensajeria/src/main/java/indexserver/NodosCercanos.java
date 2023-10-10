/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package indexserver;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mario
 */
public class NodosCercanos implements Serializable{
    
    private List<String> nodosCercanos;

    public NodosCercanos(List<String> nodosCercanos) {
        this.nodosCercanos = nodosCercanos;
    }

    public List<String> getNodosCercanos() {
        return nodosCercanos;
    }

    public void setNodosCercanos(List<String> nodosCercanos) {
        this.nodosCercanos = nodosCercanos;
    }
    
    
}
