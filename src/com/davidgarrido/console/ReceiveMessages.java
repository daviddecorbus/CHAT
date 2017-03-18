package com.davidgarrido.console;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import com.davidgarrido.console.ConsoleClient;

import com.mysql.fabric.xmlrpc.Client;

public class ReceiveMessages extends Thread {
	public String nickPrivado;
	private Socket socket;

	public ReceiveMessages(Socket socket,String nickPrivado){
		this.socket = socket;
		this.nickPrivado=nickPrivado;
	}
	
	public void run(){
        // Obtiene el flujo de entrada del socket
        DataInputStream entradaDatos = null;
        String mensaje;
        try {
            entradaDatos = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            //log.error("Error al crear el stream de entrada: " + ex.getMessage());
        } catch (NullPointerException ex) {
            //log.error("El socket no se creo correctamente. ");
        }
        
        // Bucle infinito que recibe mensajes del servidor
        boolean conectado = true;
        while (conectado) {
            try {
            	 mensaje = entradaDatos.readUTF();
            	
            	if(mensaje.contains("mensaje privado para "+nickPrivado)){
            		System.out.println(mensaje);
            		}
            	
            	else{
            		if (!mensaje.contains("mensaje privado para ")){
            			System.out.println(mensaje);
            		}
            	}
        
                
            } catch (IOException ex) {
                //log.error("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } catch (NullPointerException ex) {
                //log.error("El socket no se creo correctamente. ");
                conectado = false;
            }
        }
    }
}
