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

public class CommandWh extends Command {

	public CommandWh(String name) {
		super(name, "wh.use");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] arg) {
		if(arg.length == 0) {
			message(sender, "§3/wh addtemp <player> : §7pour ajouter un joueur temporairement");
			message(sender, "§3/wh add <player> : §7pour ajouter un joueur");
			message(sender, "§3/wh remove <player> : §7pour supprimer un joueur");
			message(sender, "§3/wh list : §7pour voir les joueurs whitelist");
			message(sender, "§3/wh on : §7pour activer la whitelist");
			message(sender, "§3/wh off : §7pour désactiver la whitelist");
		}else if(arg.length == 1) {
			if(arg[0].equals("list")) {
				if(!Main.instance.whitelist) {
					message(sender, "§7La whitelist n'est pas active");
				}
				String msg = "§7(§cB§7) §f>> §7Les joueurs dans la whitelist sont :§7";
				for(String name : Main.instance.whitelistedplayer) {
					msg += " " + name;
				}
				message(sender, msg);
			}else if(arg[0].equals("on")) {
				if(Main.instance.whitelist) {
					message(sender, "§7La whitelist est déjà activé");
				}else {
					Main.instance.whitelist = true;
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("whitelist", true);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "§7(§cB§7) §f>> §7La whitelist a été activé");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7La whitelist a bien été activer mais pas enregistré dans la config à cause de:");
						message(sender, e.getMessage());
					}
					System.out.println("Les joueurs dans la whitelist sont:");
					for(String value : Main.instance.whitelistedplayer) {
						System.out.println(value);
					}
					for(ProxiedPlayer pl : Main.instance.getProxy().getPlayers()) {
						if(!Main.instance.whitelistedplayer.contains(pl.getName())) {
							pl.disconnect("§7(§cB§7) §f>> §7Vous avez été déconnecté car la whitelist a été activé");
						}
					}
				}
			}else if(arg[0].equals("off")) {
				if(!Main.instance.whitelist) {
					message(sender, "§7(§cB§7) §f>> §7La whitelist est déjà désactivé");
				}else {
					Main.instance.whitelist = false;
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("whitelist", false);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "§7(§cB§7) §f>> §7La whitelist a été désactivé");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7La whitelist a bien été désactiver mais pas enregistré dans la config à cause de:");
						message(sender, e.getMessage());
					}
				}
			}else {
				message(sender, "§7(§cB§7) §f>> §4Cette commande n'éxiste pas");
			}
		}else if(arg.length == 2) {
			if(arg[0].equals("addtemp")) {
				if(!Main.instance.whitelistedplayer.contains(arg[1])) {
					Main.instance.whitelistedplayer.add(arg[1]);
					message(sender, "§7(§cB§7) §f>> §7Le joueur §4" + arg[1] + " §7a été ajouté §ftemporairement §7à la whitelist");
				}else {
					message(sender, "§7(§cB§7) §f>> §7Ce joueur est déjà whitelist");
				}
			}else if(arg[0].equals("add")) {
				if(!Main.instance.whitelistedplayer.contains(arg[1])) {
					Main.instance.whitelistedplayer.add(arg[1]);
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("player." + arg[1] + ".wt", true);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "§7(§cB§7) §f>> §7Le joueur §4" + arg[1] + " §7a été ajouté à la whitelist");
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7Le joueur n'a pas pu être ajouté à la whitelist définitive à cause de:");
						message(sender, e.getMessage());
					}
				}else {
					message(sender, "§7(§cB§7) §f>> §7Ce joueur est déjà whitelist");
				}
			}else if(arg[0].equals("remove")) {
				if(Main.instance.whitelistedplayer.contains(arg[1])) {
					Main.instance.whitelistedplayer.remove(arg[1]);
					Configuration config = Main.instance.config;
					File file = Main.instance.file;
					try {
						config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
						config.set("player." + arg[1] + ".wh", false);
						config.set("player." + arg[1] + ".mt", false);
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
						message(sender, "§7(§cB§7) §f>> §7Le joueur §4" + arg[1] + " §7a été enlevé à la whitelist");
						
					} catch (IOException e) {
						e.printStackTrace();
						message(sender, "§7(§cB§7) §f>> §7Le joueur n'a pas pu être ajouté à la whitelist définitive à cause de:");
						message(sender, e.getMessage());
					}
				}else {
					message(sender, "§7(§cB§7) §f>> §7Le joueur §4" + arg[1] + " §7n'est pas whitelist");
				}
			}
		}
	}
	
	public void message(CommandSender sender, String message) {
		sender.sendMessage(new TextComponent(message));
	}

}