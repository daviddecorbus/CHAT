package com.davidgarrido.console;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import com.davidgarrido.console.ConsoleClient;

import com.mysql.fabric.xmlrpc.Client;

public class ReceiveMessages extends Thread {
	public String sala;
	public String nickPrivado;
	private Socket socket;

	public ReceiveMessages(Socket socket,String nickPrivado,String sala){
		this.socket = socket;
		this.nickPrivado=nickPrivado;
		this.sala=sala;
	}
	
	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
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
     			
     			//System.out.println("este es el mensaje :"+mensajeFinal);
     			//System.out.println("esta es la sala :"+salaMensaje);
     			
            	if(mensaje.contains("mensaje privado para "+nickPrivado)){
            		System.out.println(mensaje);
            		}
            	
            	else{
            		
            		if (!mensaje.contains("mensaje privado para ")){
            			String salaMensaje=mensaje.substring(0, mensaje.indexOf('/'));
             			String mensajeFinal=mensaje.substring (mensaje.indexOf('/')+1,mensaje.length());
            			if(salaMensaje.equalsIgnoreCase(sala)){
            				System.out.println(mensajeFinal);
            			}
            			
            			
            			
                    	//sala = sala.substring(sala.indexOf(':')+2, sala.length());

                    	//String texto = mensaje.substring(mensaje.indexOf('|'), mensaje.length());

                    	//String nick = texto.substring(1, texto.indexOf(':'));

                    	//texto = texto.substring(texto.indexOf(':') + 2, texto.length());
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
