package net.srlegsini.AAS.Bungee;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.srlegsini.AAS.Bungee.extraUtils.ResourceManager;

public class configManager {
	
	private static Configuration config = null;
	
	public static Configuration getConfig() { // Cargar/crear archivo de configuración.
		
		if (config != null) { // Si el archivo ya fue cargado,
			return config;    // devolverlo.
		}
		
		if (!MClass.plugin.getDataFolder().exists()) { // Si la carpeta del plugin no existe,
			MClass.plugin.getDataFolder().mkdir();     // crearla.
		}
		
		File configFile = new File(MClass.plugin.getDataFolder(), "config.yml"); // Cargar archivo.
		
		if (!configFile.exists()) {         // Si el archivo no existe,
			try {
				ResourceManager.extract("/net/srlegsini/AAS/Bungee/files/config.yml");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		try {
			config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile); // Inicializar variable del archivo YAML.
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return config; // Fin.
	}
	
//	public static void saveConfig() { // Guardar achivo de configuración.
//		if (config == null) {         // Si el archivo no existe,
//			return;                   // abortar operación.
//		}
//		
//		try {
//			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(MClass.plugin.getDataFolder(), "config.yml")); // Obtener y guardar el archivo.
//		} catch (IOException e) {
//			e.printStackTrace();
//			return;
//		}
//		
//		config = null; // Recargar la configuración.
//		getConfig();
//	}
//	
//	// Utilidades extra...
//	
//	public static void SAS(String path, String val) { // Set and Save. Establecer y guardar automáticamente para facilitar el trabajo.
//		 getConfig().set(path, val);
//		 saveConfig();
//	}
//	
//	public static void SAS(String path, int val) {
//		getConfig().set(path, val);
//		 saveConfig();
//	}
//	
//	public static void SAS(String path, boolean val) {
//		getConfig().set(path, val);
//		 saveConfig();
//	}
//	
//	public static void SAS(String path, List<String> val) {
//		getConfig().set(path, val);
//		 saveConfig();
//	}
//	
//	public static void sasINE(String path, String val) { //Set and Save IF NOT EXISTS. Establecer y guardar automáticamente SOLO si lo establecido no existe.
//		if (!getConfig().contains(path)) {
//			SAS(path, val);
//		}
//	}
//	
//	public static void sasINE(String path, int val) {
//		if (!getConfig().contains(path)) {
//			SAS(path, val);
//		}
//	}
//	
//	public static void sasINE(String path, boolean val) {
//		if (!getConfig().contains(path)) {
//			SAS(path, val);
//		}
//	}
//	
//	public static void sasINE(String path, List<String> val) {
//		if (!getConfig().contains(path)) {
//			SAS(path, val);
//		}
//	}
	
}
