package net.srlegsini.AAS.SQL;

import java.sql.*;

import net.srlegsini.AAS.Bungee.MClass;
import net.srlegsini.AAS.Bungee.configManager;

public class MySQL {
	
	private static String hostname, database, username, password;
	private static Connection backupConnection;
	private static boolean backup = false;
	
	public static void initialize(String host, String db, String user, String pass) {
		hostname = host;
		database = db;
		username = user;
		password = pass;
		
		if (configManager.getConfig().getBoolean("Database.mysql.backupFile")) {
			backup = true;
			
		    try {
			       Class.forName("org.sqlite.JDBC");
			       backupConnection = DriverManager.getConnection("jdbc:sqlite:/" + MClass.plugin.getDataFolder().getAbsolutePath() + "/" + "MySQLBackup" + ".db");
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			
		}
		
	}
	
	private static void backup_sendUpdate(String cmd) {
		if (!backup) {
			return;
		}
		
		Connection tempCon = backupConnection;
		
		try {
			Statement stmt = tempCon.createStatement();
			stmt.executeUpdate(cmd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static Connection connection;
	
	private static Connection getCon() {
		if (connection != null) {
			return connection;
		}
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + database, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static boolean sendUpdate(String cmd) {
		Connection tempCon = getCon();
		
		try {
			Statement stmt = tempCon.createStatement();
			stmt.executeUpdate(cmd);
			backup_sendUpdate(cmd);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static ResultSet executeQuery(String cmd) {
		Connection tempCon = getCon();
		ResultSet rs = null;
		
		try {
			Statement stmt = tempCon.createStatement();
			rs = stmt.executeQuery(cmd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public static String getPasswordOf(String username) {
		ResultSet rs = MySQL.executeQuery("select * from Users where PlayerName = '" + username + "'");
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
