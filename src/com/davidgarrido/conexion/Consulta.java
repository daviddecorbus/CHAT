package com.davidgarrido.conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import java.sql.Connection;

public class Consulta {

	Connection con = null;
	public Consulta(Conexion conexion){
		this.con = conexion.getConnection();
	}
	public void nuevoUsuario(String nick){
		try {
			String query = "insert into clientes (nick, online) values (?, 1);";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, nick);
			preparedStmt.execute();
			System.out.println("Usuario "+nick+" registrado");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {} 
	}
public void borrarUsuario(String nick){
		try {
			String query = "delete from clientes where nick = ?;";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, nick);
			preparedStmt.execute();
			System.out.println("Usuario "+nick +" eliminado de la base de datos");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {} 
	}
public void clienteOnline(String nick){
	try {
		String query = "update clientes set online=1 where nick = ? ;";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, nick);
		preparedStmt.executeUpdate();
		System.out.println("El usuario " +nick +" ha entrado al chat");
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {} 
}
public void clienteOffline(String nick){
	try {
		String query = "update clientes set online=0 where nick = ? ;";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		preparedStmt.setString(1, nick);
		preparedStmt.executeUpdate();	
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {} 
}
public boolean revisarUsuario(String nick) {
		boolean registrado = false;
		try {
			String query = "select count(*) from clientes where nick ='" +nick +"';";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()){
				if (rs.getInt(1)==1) {
					registrado = true;};
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return registrado;
	}

public void mostrarUsuariosOnline () {
		try {
			String query = "select nick from clientes where online=1;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Usuarios Online");
			System.out.println("***********************");
			while (rs.next()){
				System.out.println(rs.getString("nick"));
			}	
			System.out.println("");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {} 
	}
	public void mostrarUsuarios () {
		try {
			String query = "select nick from clientes;";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("Usuarios Registrados");
			System.out.println("************************");
			while (rs.next()){
				System.out.println(rs.getString("nick"));
			}	
			System.out.println("");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {} 
	}
}



