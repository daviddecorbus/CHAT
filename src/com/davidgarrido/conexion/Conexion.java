package com.davidgarrido.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

public class Conexion {
	private String bd= null;
	private String user= null;
	private String password=null;
	private String server=null;
	private static Connection con=null;
	
	
	public Conexion(String bd,String user,String password){
		this.bd=bd;
		this.user=user;
		this.password="";
		this.server="jdbc:mysql://localhost/" + bd;
		this.con = getConexion();
	}
	public Connection getConnection(){
		return this.con;
	}
	public Connection getConexion(){
		Connection conn = null;
	
		
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					conn = DriverManager.getConnection(server, user, password);
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					
				}
				return conn;
			
		
	}
}
