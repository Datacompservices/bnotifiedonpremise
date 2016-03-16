package com.dcs.bnotidfied.onpservice.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class ConnectionManager {

	public static Connection getConnection(String host)
	{
		Connection con = null;
		DataSource source = null;
		try {
		
		try {
			source = (DataSource)new InitialContext().lookup("java:/comp/env/"+host);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
			con = source.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*try {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		returncon = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bankDB","bankuser", "bankuser");
					
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		*/

		return con;
		
	}
		
	
	

	public static Connection getConnection()
	{
		Connection con = null;
		DataSource source = null;
		try {
		
		try {
			source = (DataSource)new InitialContext().lookup("java:/comp/env/postgres");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
			con = source.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*try {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		returncon = DriverManager.getConnection("jdbc:postgresql://localhost:5432/bankDB","bankuser", "bankuser");
					
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		*/

		return con;
	}
	
}
