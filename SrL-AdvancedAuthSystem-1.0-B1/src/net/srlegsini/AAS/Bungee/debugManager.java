package net.srlegsini.AAS.Bungee;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class debugManager {
	
	private static Configuration debugYML = null;
	
	//0MINIMAL, 1NORMAL, 2HIGH, 3DEVELOPER
	private static int debugLevel = 1;
	
	public static void initialize() {
		debugLevel = configManager.getConfig().getInt("Plugin.debug.debugLevel");
		boolean debugFileEnabled = configManager.getConfig().getBoolean("Plugin.debug.debugFile");
		
		File debugFile = new File(MClass.plugin.getDataFolder(), "debug.yml");
		try {
			debugYML = ConfigurationProvider.getProvider(YamlConfiguration.class).load(debugFile); // Inicializar variable del archivo YAML.
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		if (debugFileEnabled) {
			
			if (!debugFile.exists()) {
				try {
					debugFile.createNewFile();                                    // Crear archivo YAML.
					debugYML.set("debug.list", Utils.fastList("srl rules here")); // Crear lista base para evitar posibles errores.
					saveDebugYML();                                               // Guardar cambios.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else {
			
			if (debugFile.exists()) {
				debugFile.delete();
			}
			
		}
		
	}
	
	public static void debug(String message, int level) {
		if (!(level <= debugLevel)) {
			
			List<String> currentDebugList = debugYML.getStringList("debug.list");
			
			if (currentDebugList.size() > 64) {
				currentDebugList.remove(0);
			}
			
			currentDebugList.add(message);
			debugYML.set("debug.list", currentDebugList);
			saveDebugYML();
			
			return;
		}
		
		Utils.getConsole().sendMessage(Utils.col(message));
	}
	
	private static void saveDebugYML() {
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(debugYML, new File(MClass.plugin.getDataFolder(), "debug.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
