/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package empaquetamiento;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author mario
 */
public class Movimiento{
    
    private String mensaje;
    private String persona;

    public Movimiento(String mensaje, String persona) {
        this.mensaje = mensaje;
        this.persona = persona;
    }

    public Movimiento() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    @Override
    public String toString() {
        return persona + ": " + mensaje;
    }
    
    public String convertirAJSON() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
    
}
