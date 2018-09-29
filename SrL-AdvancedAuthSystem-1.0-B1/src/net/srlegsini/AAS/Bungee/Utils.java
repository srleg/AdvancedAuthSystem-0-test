package net.srlegsini.AAS.Bungee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class Utils {
	
    public static List<String> fastList(String value) {
        String string[] = value.split("@");
        List<String> listOfString = Stream.of(string).collect(Collectors.toList());
        return listOfString;
    }
	
	public static CommandSender getConsole() {
		return BungeeCord.getInstance().getConsole();
	}
	
	public static BaseComponent[] col(String text) {
		return TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', text));
	}
	
	public static void welcomeMessage() {
		Utils.getConsole().sendMessage(Utils.col(" "));
		Utils.getConsole().sendMessage(Utils.col("&6AdvancedAuthSystem v" + MClass.plugin.getDescription().getVersion() + " &eby SrLegsini"));
		Utils.getConsole().sendMessage(Utils.col("&f========================"));
		
		Map<String, String> list = new HashMap<String, String>();
		list.put("Database", configManager.getConfig().getString("Database.databaseType"));
		
		for (String s : list.keySet()) {
			int spacesCount = 24;
			String spaces = "";
			spacesCount = spacesCount - (s.length() + list.get(s).length());
			
			while (spacesCount > 0) {
				spaces = spaces + " ";
				spacesCount--;
			}
			
			Utils.getConsole().sendMessage(Utils.col("&c" + s + "&f" + spaces + "&4" + list.get(s)));
		}
		
		Utils.getConsole().sendMessage(Utils.col("&f========================"));
		Utils.getConsole().sendMessage(Utils.col(" "));
	}
	
	
}
