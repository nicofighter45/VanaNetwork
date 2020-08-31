package fr.nicofighter45.vananetwork;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.nicofighter45.vananetwork.commands.CommandMt;
import fr.nicofighter45.vananetwork.commands.CommandWh;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin{
	
	public static Main instance;
	public File file;
	public Configuration config;
	public boolean whitelist;
	public boolean maintenance;
	public List<String> whitelistedplayer = new ArrayList<>();
	public List<String> maintenanceplayer = new ArrayList<>();
	
	@Override
	public void onEnable() {
		instance = this;
		System.out.println("Le plugin VanaNetwork est start");
		getProxy().getPluginManager().registerCommand(this, new CommandWh("wh"));
		getProxy().getPluginManager().registerCommand(this, new CommandMt("mt"));
		getProxy().getPluginManager().registerListener(this,  new Listenner());
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		file = new File(getDataFolder(), "config.yml");
		try {
			if(!file.exists()) {
				file.createNewFile();
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
				config.set("whitelist", true);
				config.set("maintenance", false);
				addPlayer("nicofighter45", true, true);
				addPlayer("ykarulus", true, true);
				addPlayer("iReplace", true, true);
				addPlayer("TheGh0stRider", true, true);
				addPlayer("doosecpa", true, false);
				addPlayer("Martinouille", true, false);
				addPlayer("Krog_", true, false);
				addPlayer("chiveno", true, false);
				addPlayer("Meuhmeuh_Rayano", true, false);
				addPlayer("lukazgames_19", true, false);
				addPlayer("Layoux_", true, false);
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
			}else {
				config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(config.getBoolean("whitelist")) {
			whitelist = true;
			System.out.println("La whitelist est activé sur ce server");
		}else {
			whitelist = false;
			System.out.println("La whitelist n'est pas active sur le server");
		}
		if(config.getBoolean("maintenance")) {
			maintenance = true;
			System.out.println("La maintenance est activé sur ce server");
		}else {
			maintenance = false;
			System.out.println("La maintenance n'est pas active sur le server");
		}
		for(String str : config.getSection("player").getKeys()) {
			 if(config.getBoolean("player." + str + ".wh")) {
				 whitelistedplayer.add(str);
				 System.out.println(str + " dans la whitelist");
			 }
			 if(config.getBoolean("player." + str + ".mt")) {
				 maintenanceplayer.add(str);
				 System.out.println(str + " dans la liste de maintenance");
			 }
		}
	}
	
	public void addPlayer(String name, boolean whitelist, boolean maintenance) {
		config.set("player." + name + ".wh", whitelist);
		config.set("player." + name + ".mt", maintenance);
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin VanaNetwork est down");
	}
	
	public void message(ProxiedPlayer player, String message) {
		player.sendMessage(new TextComponent(message));
	}
	
}