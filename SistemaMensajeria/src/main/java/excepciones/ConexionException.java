/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 * ConexionException.class
 * @author
 */
public class ConexionException extends Exception{

    public ConexionException() {
    }

    public ConexionException(String message) {
        super(message);
    }

    public ConexionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConexionException(Throwable cause) {
        super(cause);
    }
    
}
