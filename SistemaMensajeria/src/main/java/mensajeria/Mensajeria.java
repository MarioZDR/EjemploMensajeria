package mensajeria;

import conexion.Conexion;
import empaquetamiento.Evento;
import empaquetamiento.Mensaje;
import empaquetamiento.MoverFicha;
import empaquetamiento.SacarFichaPozo;
import empaquetamiento.TipoEvento;
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
        Evento evento = null;
        System.out.println("Mover ficha ingresa 'Mover' seguido de la ficha");
        System.out.println("Sacar ficha ingresa 'Pozo' seguido de la ficha");
        System.out.println("Para salir ingresa 'Salir'");
        do {
            mensaje = tec.nextLine();
            try {
                if (!mensaje.equalsIgnoreCase("Salir")) {
                    String ficha = tec.nextLine();
                    if(mensaje.equalsIgnoreCase("Mover")){
                        this.conexion.enviarDatos(new MoverFicha(this.usuario,ficha, TipoEvento.MOVER_FICHA));
                    }else if(mensaje.equalsIgnoreCase("Pozo")){
                        this.conexion.enviarDatos(new SacarFichaPozo(this.usuario,ficha, TipoEvento.SACAR_FICHA));
                    }else{
                        System.out.println("Ingresaste un movimiento invalido");
                    }
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
            conexion.enviarDatos(new Mensaje(this.usuario,"se ha conectado", TipoEvento.MENSAJE));
        } catch (ConexionException ex) {
            System.out.println("Error al avisar a los demas de la entrada al chat");
        }
    }
    
    private void anunciarSalidaChat() {
        try {
            conexion.enviarDatos(new Mensaje( this.usuario, "ha salido",TipoEvento.MENSAJE));
        } catch (ConexionException ex) {
            System.out.println("Error al avisar a los demas de la salida del chat");
        }
    }
    
    private void salirDelChat() {
        anunciarSalidaChat();
        conexion.cerrarConexiones();
    }
    
    public void recibirMensaje(Evento datos) {
        System.out.println(datos);
    }

    public static void main(String[] args) {
        Mensajeria mensajeria = new Mensajeria();
        mensajeria.run();
    }

}
