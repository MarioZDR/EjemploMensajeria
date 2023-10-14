package mensajeria;

import conexion.Conexion;
import empaquetamiento.Movimiento;
import excepciones.ConexionException;
import java.util.Scanner;

/**
 * Mensajeria.class
 * @author
 */
public class Mensajeria extends Thread {

    private Conexion conexion;
    private String usuario;
    private boolean activo = true;
    private final Scanner tec = new Scanner(System.in);

    public Mensajeria() {
        this.conexion = new Conexion();
        this.conexion.agregarSuscriptor(this);
    }

    @Override
    public void run() {
        this.solicitarNombre();
        this.anunciarEntradaChat();
        this.chatear();
    }
    
    private void chatear(){
        String mensaje;
        System.out.println("Empieza a chatear, cuando quieras salir escribe 'Salir'");
        do {
            mensaje = tec.nextLine();
            try {
                if (!mensaje.equalsIgnoreCase("Salir")) {
                    conexion.enviarDatos(new Movimiento(mensaje, this.usuario));
                } else {
                    activo = false;
                    salirDelChat();
                    break;
                }
            } catch (ConexionException ex) {
                System.out.println(ex.getMessage());
            }
        } while (activo);
    }
    
    private void solicitarNombre(){
        System.out.println("Ingrese su nombre: ");
        this.usuario = tec.nextLine();
    }

    private void anunciarEntradaChat(){
        try {
            conexion.enviarDatos(new Movimiento("se ha conectado", this.usuario));
        } catch (ConexionException ex) {
            System.out.println("Error al avisar a los demas de la entrada al chat");
        }
    }
    
    private void anunciarSalidaChat() {
        try {
            conexion.enviarDatos(new Movimiento("se ha salido", this.usuario));
        } catch (ConexionException ex) {
            System.out.println("Error al avisar a los demas de la salida del chat");
        }
    }
    
    private void salirDelChat() {
        anunciarSalidaChat();
        conexion.cerrarConexiones();
    }
    
    public void recibirMensaje(Movimiento movimiento) {
        System.out.println(movimiento);
    }

    public static void main(String[] args) {
        Mensajeria mensajeria = new Mensajeria();
        mensajeria.run();
    }

}
