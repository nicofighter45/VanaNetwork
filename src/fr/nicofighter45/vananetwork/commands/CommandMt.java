package fr.nicofighter45.vananetwork.commands;

import java.io.File;
import java.io.IOException;

import fr.nicofighter45.vananetwork.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CommandMt extends Command {

	public CommandMt(String name) {
		super(name, "mt.use");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] arg) {
		if(arg.length == 0) {
			message(sender, "�3/mt add <player> : �7pour ajouter un joueur");
			message(sender, "�3/mt remove <player> : �7pour supprimer un joueur");
			message(sender, "�3/mt list : �7pour voir les joueurs whitelist");
			message(sender, "�3/mt on : �7pour activer la whitelist");
			message(sender, "�3/mt off : �7pour d�sactiver la whitelist");
		}else if(arg.length == 1) {
			if(arg[0].equals("list")) {
				if(!Main.instance.maintenance) {
					message(sender, "�7La maintenance n'est pas active");
				}
				String msg = "�7(�cB�7) �f>> �7Les joueurs dans la liste de maintenance sont :";
				for(String name : Main.instance.maintenanceplayer) {
					msg += " " + name;
				}
				message(sender, msg);
			}else if(arg[0].equals("on")) {
				if(Main.instance.maintenance) {
					message(sender, "�7(�cB�7) �f>> �7La maintenance est d�j� activ�");
				}else {
					Main.instance.maintenance = true;
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("maintenance", true);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "�7(�cB�7) �f>> �7La maintenance a �t� activ�");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "�7(�cB�7) �f>> �7La maintenance a bien �t� activer mais pas enregistr� dans la config � cause de:");
						message(sender, e.getMessage());
					}
					System.out.println("Les joueurs accept� en mainteance sont:");
					for(String value : Main.instance.maintenanceplayer) {
						System.out.println(value);
					}
					for(ProxiedPlayer pl : Main.instance.getProxy().getPlayers()) {
						if(!Main.instance.maintenanceplayer.contains(pl.getName())) {
							pl.disconnect("�7Vous avez �t� d�connect� car la maintenance a �t� activ�");
						}
					}
				}
			}else if(arg[0].equals("off")) {
				if(!Main.instance.maintenance) {
					message(sender, "�7(�cB�7) �f>> �7La maintenance est d�j� d�sactiv�");
				}else {
					Main.instance.maintenance = false;
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("maintenance", false);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "�7(�cB�7) �f>> �7La maintenance a �t� d�sactiv�");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "�7(�cB�7) �f>> �7La maintenance a bien �t� d�sactiver mais pas enregistr� dans la config � cause de:");
						message(sender, e.getMessage());
					}
				}
			}else {
				message(sender, "�7(�cB�7) �f>> �cCette commande n'�xiste pas");
			}
		}else if(arg.length == 2) {
			if(arg[0].equals("add")) {
				if(!Main.instance.maintenanceplayer.contains(arg[1])) {
					Main.instance.maintenanceplayer.add(arg[1]);
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("player." + arg[1] + ".wt", true);
						config.set("player." + arg[1] + ".mt", true);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "�7(�cB�7) �f>> �7Le joueur �4" + arg[1] + " �7a �t� ajout� � la maintenance");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "�7(�cB�7) �f>> �7Le joueur n'a pas pu �tre ajout� � la maintenance � cause de:");
						message(sender, e.getMessage());
					}
				}else {
					message(sender, "�7(�cB�7) �f>> �7Ce joueur est d�j� whitelist");
				}
			}else if(arg[0].equals("remove")) {
				if(Main.instance.maintenanceplayer.contains(arg[1])) {
					Main.instance.maintenanceplayer.remove(arg[1]);
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("player." + arg[1] + ".mt", false);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "�7(�cB�7) �f>> �7Le joueur " + arg[1] + " a �t� enlev� de la maintenance");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "�7(�cB�7) �f>> �7Le joueur n'a pas pu �tre enlev� de la maintenance � cause de:");
						message(sender, e.getMessage());
					}
				}else {
					message(sender, "�7(�cB�7) �f>> �7Le joueur �4" + arg[1] + " �7n'est pas dans la list de la maintenance");
				}
			}
		}
	}

	public void message(CommandSender sender, String message) {
		sender.sendMessage(new TextComponent(message));
	}

}