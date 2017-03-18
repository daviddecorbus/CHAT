package com.davidgarrido.console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.davidgarrido.conexion.Conexion;
import com.davidgarrido.conexion.Consulta;
import com.davidgarrido.server.ServidorChat;
import com.davidgarrido.client.ConexionServidor;

public class ConsoleClient {
	
	private Socket socket;
	private  DataOutputStream salidaDatos;
	public String nick = null;
	public ConsoleClient(){
		// Se crea el socket para conectar con el Sevidor del Chat
        try {
            socket = new Socket("127.0.0.1", 1234);
            salidaDatos = new DataOutputStream(socket.getOutputStream());
            
        } catch (UnknownHostException ex) {
            //log.error("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        } catch (IOException ex) {
            //log.error("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }
        new ConexionServidor(socket, null, "Juan");
	}
	
	public void send(String text){
		try {
            salidaDatos.writeUTF(getNick().toUpperCase() +": "+ text);
        } catch (IOException ex) {
            //log.error("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public DataOutputStream getSalidaDatos() {
		return salidaDatos;
	}

	public void setSalidaDatos(DataOutputStream salidaDatos) {
		this.salidaDatos = salidaDatos;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public static void main(String args[]){
		Consulta consulta = new Consulta(new Conexion("chat","root"," "));
		ConsoleClient client = new ConsoleClient();
		Scanner teclado = new Scanner(System.in);
		String texto = null;
		System.out.println("Indica tu nick:");
		texto = teclado.nextLine();
		client.setNick(texto);	
		new ReceiveMessages(client.socket,client.getNick()).start();
		
		
		
		if (consulta.revisarUsuario(texto)) {
			consulta.clienteOnline(texto);
		} else {
			consulta.nuevoUsuario(texto);
		}
		consulta.mostrarUsuariosOnline();
		System.out.println("Chat activo(escribe cuando quieras):");
		boolean chatActivo=true;
		while (chatActivo){
			
			texto = teclado.nextLine();
				switch (texto) {
				case "USUARIOS_R":	
					consulta.mostrarUsuarios();
					break;	
				case "USUARIOS_A":	
					consulta.mostrarUsuariosOnline();
					break;		
				case "CHATPRIVADO":
					System.out.println("Introduce el nombre del usuario al que quieres enviar un mensaje privado");
					texto = teclado.nextLine();
					String c =texto;
					System.out.println("escribe el mensaje");
					texto = teclado.nextLine();
					client.send("mensaje privado para "+c+" de "+client.getNick()+" : "+texto);
					System.out.println(client.getNick()+": "+texto);
					
					break;		
				case "SYSTEM":
	
					break;		
				case "LOGOUT_A":		
					consulta.clienteOffline(client.getNick());
					client.send(client.getNick()+" ha dejado el chat");
					try {
						client.getSocket().close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					chatActivo=false;
					break;		
				case "LOGOUT_R":		
					consulta.clienteOffline(client.getNick());
					consulta.borrarUsuario(client.getNick());
					client.send(client.getNick()+" ha dejado el chat para siempre");
					chatActivo=false;
					break;
		
				default:
					client.send(texto);
					break;
				}
		
	}
}
}