package net.srlegsini.AAS.SQL;

import java.sql.*;

import net.srlegsini.AAS.Bungee.MClass;

public class SQLite {
	
	private static Connection connection = null;
	
	public static void initialize(String fileName, String filePath) {
	    String finalPath = MClass.plugin.getDataFolder().getAbsolutePath();
		
	    if (!filePath.equalsIgnoreCase("DEFAULT")) {
	    	finalPath = filePath;
	    }
	    
	    try {
	       Class.forName("org.sqlite.JDBC");
	       connection = DriverManager.getConnection("jdbc:sqlite:/" + finalPath + "/" + fileName + ".db");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
	}
	
	public static boolean sendUpdate(String cmd) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ResultSet executeQuery(String cmd) {
		ResultSet rs = null;
		
		try {
			Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(cmd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static String getPasswordOf(String username) {
		ResultSet rs = SQLite.executeQuery("select * from Users where PlayerName = '" + username + "'");
		try {
			while (rs.next()) {
				if (rs.getString("PlayerName").equals(username)) {
					return rs.getString("Password");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
