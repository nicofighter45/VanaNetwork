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
			message(sender, "§3/mt add <player> : §7pour ajouter un joueur");
			message(sender, "§3/mt remove <player> : §7pour supprimer un joueur");
			message(sender, "§3/mt list : §7pour voir les joueurs whitelist");
			message(sender, "§3/mt on : §7pour activer la whitelist");
			message(sender, "§3/mt off : §7pour désactiver la whitelist");
		}else if(arg.length == 1) {
			if(arg[0].equals("list")) {
				if(!Main.instance.maintenance) {
					message(sender, "§7La maintenance n'est pas active");
				}
				String msg = "§7(§cB§7) §f>> §7Les joueurs dans la liste de maintenance sont :";
				for(String name : Main.instance.maintenanceplayer) {
					msg += " " + name;
				}
				message(sender, msg);
			}else if(arg[0].equals("on")) {
				if(Main.instance.maintenance) {
					message(sender, "§7(§cB§7) §f>> §7La maintenance est déjà activé");
				}else {
					Main.instance.maintenance = true;
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("maintenance", true);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "§7(§cB§7) §f>> §7La maintenance a été activé");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7La maintenance a bien été activer mais pas enregistré dans la config à cause de:");
						message(sender, e.getMessage());
					}
					System.out.println("Les joueurs accepté en mainteance sont:");
					for(String value : Main.instance.maintenanceplayer) {
						System.out.println(value);
					}
					for(ProxiedPlayer pl : Main.instance.getProxy().getPlayers()) {
						if(!Main.instance.maintenanceplayer.contains(pl.getName())) {
							pl.disconnect("§7Vous avez été déconnecté car la maintenance a été activé");
						}
					}
				}
			}else if(arg[0].equals("off")) {
				if(!Main.instance.maintenance) {
					message(sender, "§7(§cB§7) §f>> §7La maintenance est déjà désactivé");
				}else {
					Main.instance.maintenance = false;
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("maintenance", false);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "§7(§cB§7) §f>> §7La maintenance a été désactivé");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7La maintenance a bien été désactiver mais pas enregistré dans la config à cause de:");
						message(sender, e.getMessage());
					}
				}
			}else {
				message(sender, "§7(§cB§7) §f>> §cCette commande n'éxiste pas");
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
						message(sender, "§7(§cB§7) §f>> §7Le joueur §4" + arg[1] + " §7a été ajouté à la maintenance");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7Le joueur n'a pas pu être ajouté à la maintenance à cause de:");
						message(sender, e.getMessage());
					}
				}else {
					message(sender, "§7(§cB§7) §f>> §7Ce joueur est déjà whitelist");
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
						message(sender, "§7(§cB§7) §f>> §7Le joueur " + arg[1] + " a été enlevé de la maintenance");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7Le joueur n'a pas pu être enlevé de la maintenance à cause de:");
						message(sender, e.getMessage());
					}
				}else {
					message(sender, "§7(§cB§7) §f>> §7Le joueur §4" + arg[1] + " §7n'est pas dans la list de la maintenance");
				}
			}
		}
	}

	public void message(CommandSender sender, String message) {
		sender.sendMessage(new TextComponent(message));
	}

}