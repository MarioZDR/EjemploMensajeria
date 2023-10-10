package mensajeria;

import java.util.Scanner;

public class Mensajeria implements Runnable {

    private Conexion conexion;
    private String usuario;
    private boolean activo = true;

    public Mensajeria() {
        this.conexion = new Conexion();
    }

    @Override
    public void run() {
        Scanner tec = new Scanner(System.in);

        System.out.println("Ingrese su nombre: ");
        this.usuario = tec.nextLine();

        String mensaje;
        do {
            System.out.println("Escribe un mensaje (o 'salir' para salir):");
            mensaje = tec.nextLine();
            try {
                if (!mensaje.equalsIgnoreCase("Salir")) {
                    conexion.enviarDatos(new Movimiento(mensaje, this.usuario));
                } else {
                    activo = false;
                }
            } catch (Exception ex) {
                System.out.println("Ocurrio un error en el envio del mensaje");
                ex.printStackTrace();
            }
        } while (activo);
    }

    public void recibirMensaje(Movimiento movimiento) {
        System.out.println("Mensaje recibido de: " + movimiento);
    }

    public static void main(String[] args) {
        Mensajeria mensajeria = new Mensajeria();
        mensajeria.run();
    }

}
