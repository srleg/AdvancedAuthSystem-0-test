package net.srlegsini.AAS.Bungee;

import net.md_5.bungee.api.plugin.Plugin;

public class MClass extends Plugin {
	
	// Se declaran variables universales.
	public static MClass plugin;
	
	public void onEnable() {
		plugin = this;
		
		Utils.welcomeMessage();
		debugManager.initialize();
	}
	
}
