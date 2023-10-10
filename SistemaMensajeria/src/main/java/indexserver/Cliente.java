/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package indexserver;

import java.net.Socket;

/**
 *
 * @author mario
 */
public class Cliente {

    private static String serverAddress = "127.0.0.1";
    private static int serverPort = 3999;
    private static Socket socketServidorIndices;
    
    
    public static void main(String[] args) {
        
        try{
        socketServidorIndices = new Socket(serverAddress, serverPort);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
