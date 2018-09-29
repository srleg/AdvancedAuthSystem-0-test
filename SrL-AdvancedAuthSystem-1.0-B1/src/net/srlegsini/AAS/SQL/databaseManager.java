package net.srlegsini.AAS.SQL;

import net.srlegsini.AAS.Bungee.configManager;

public class databaseManager {
	
	//SINTAXIS DE TABLA
	//PLAYERNAME - PASSWORD
	
	public static boolean useMySQL = false;
	
	public static void databaseInitializer() {
		String databaseType = configManager.getConfig().getString("Database.databaseType");
		if (databaseType.equalsIgnoreCase("mysql")) {
			String hostname = configManager.getConfig().getString("Database.mysql.hostname");
			String database = configManager.getConfig().getString("Database.mysql.database");
			String username = configManager.getConfig().getString("Database.mysql.username");
			String password = configManager.getConfig().getString("Database.mysql.password");
			
			MySQL.initialize(hostname, database, username, password);
			useMySQL = true;
		} else if (databaseType.equalsIgnoreCase("sqlite")) {
			String fileName = configManager.getConfig().getString("Database.sqlite.fileName");
			String filePath = configManager.getConfig().getString("Database.sqlite.filePath");
			
			SQLite.initialize(fileName, filePath);
			useMySQL = false;
		}
	}
	
	public static boolean addUser(String username, String password) {
		String cmd = "insert into Users (PlayerName, Password) values ('" + username + "', '" + password + "')";
		
		return databaseManager.sendUpdate(cmd);
	}
	
	public static boolean changePassword(String username, String password) {
		String cmd = "update Users set Password = '" + password + "' where PlayerName = '" + username + "'";
		
		return databaseManager.sendUpdate(cmd);
	}
	
	
	
	public static boolean sendUpdate(String cmd) {
		if (useMySQL) {
			return MySQL.sendUpdate(cmd);
		} else {
			return SQLite.sendUpdate(cmd);
		}
	}
	
	public static String getPasswordOf(String username) {
		if (useMySQL) {
			return MySQL.getPasswordOf(username);
		} else {
			return SQLite.getPasswordOf(username);
		}
	}
	
}
