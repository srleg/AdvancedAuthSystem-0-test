package net.srlegsini.AAS.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.srlegsini.AAS.Bungee.configManager;

public class migrationProcess {
	
	// Clase manager del proceso de migración de una base de datos a otra.
	// MySQL > SQLite
	// SQLite > MySQL
	// AuthMe > Actual Database
	// DynamicBungeeAuth > Actual Database
	// BungeePremiumAuth > Actual Database
	
	// USUARIO####CONTRASEÑA
	
	public static void migrate_MySQL_to_SQLite() {
		// No será necesario reiniciar el plugin para pasar de una base de datos a otra.
		
		// Primero, recoger todos los datos de la base de datos principal
		Map<String, String> migration = new HashMap<String, String>();
		
		ResultSet todo = MySQL.executeQuery("select * from Users");
		try {
			while (todo.next()) {
				migration.put(todo.getString("PlayerName"), todo.getString("Password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		databaseManager.useMySQL = false;
		
		String fileName = configManager.getConfig().getString("Database.sqlite.fileName");
		String filePath = configManager.getConfig().getString("Database.sqlite.filePath");
		SQLite.initialize(fileName, filePath);
		
		for (String s : migration.keySet()) {
			SQLite.sendUpdate("insert into Users(PlayerName, Password) values ('" + s + "', '" + migration.get(s) + "')");
		}
		
	}
	
	public static void migrate_SQLite_to_MySQL() {
		// No será necesario reiniciar el plugin para pasar de una base de datos a otra.
		
		// Primero, recoger todos los datos de la base de datos principal
		Map<String, String> migration = new HashMap<String, String>();
		
		ResultSet todo = SQLite.executeQuery("select * from Users");
		try {
			while (todo.next()) {
				migration.put(todo.getString("PlayerName"), todo.getString("Password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		databaseManager.useMySQL = true;
		
		String hostname = configManager.getConfig().getString("Database.mysql.hostname");
		String database = configManager.getConfig().getString("Database.mysql.database");
		String username = configManager.getConfig().getString("Database.mysql.username");
		String password = configManager.getConfig().getString("Database.mysql.password");
		MySQL.initialize(hostname, database, username, password);
		
		for (String s : migration.keySet()) {
			MySQL.sendUpdate("insert into Users(PlayerName, Password) values ('" + s + "', '" + migration.get(s) + "')");
		}
		
	}
	
	
	
	
}
