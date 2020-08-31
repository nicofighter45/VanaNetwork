package fr.nicofighter45.vananetwork;

import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Listenner implements Listener {
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void login(ServerConnectEvent event) {
		if (event.isCancelled())return;
		ProxiedPlayer player = event.getPlayer();
		if(Main.instance.maintenance) {
			if(Main.instance.maintenanceplayer.contains(player.getName())) {
				if(Main.instance.whitelist == true) {
					if(Main.instance.whitelistedplayer.contains(player.getName())) {
						checkVersion(player);
					}else {
						player.disconnect("Désolé, vous n'êtes pas whitelist sur ce server");
					}
				}else {
					checkVersion(player);
				}
			}else {
				player.disconnect("Désolé, vous n'êtes pas accepté sur la maintenance du server");
			}
		}else {
			if(Main.instance.whitelist == true) {
				if(Main.instance.whitelistedplayer.contains(player.getName())) {
					checkVersion(player);
				}else {
					player.disconnect("Désolé, vous n'êtes pas whitelist sur ce server");
				}
			}else {
				checkVersion(player);
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void checkVersion(ProxiedPlayer player) {
		int version = player.getPendingConnection().getVersion();
		if(version == 736) {
			player.disconnect("La mise à jour du server survie n'est pas encore terminé ! \nUtilise la 1.15.1 pour le server LG  et la 1.16.2 pour la survie !");
		}else if(version == 575 || version == 751) {
		}else {
			player.disconnect("Utilise la 1.15.1 pour le server LG !");
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void pinging(ProxyPingEvent event) {
		ServerPing ping = event.getResponse();
		ServerPing.Players player = ping.getPlayers();
		ServerPing.Protocol version = ping.getVersion();
		if(Main.instance.maintenance) {
			version.setName("mainteance activé");
		}else {
			if(Main.instance.whitelist) {
				version.setName("whitelist activé");
			}else {
				version.setName("Server public");
			}
		}
		version.setProtocol(1);
		ping.setVersion(version);
		player.setSample(new ServerPing.PlayerInfo[] {new ServerPing.PlayerInfo("§7Server Entre Pote", UUID.randomUUID())});
		ping.setDescription("§6Vanadium Network\n§31.15.2");
		event.setResponse(ping);
	}
	
	@EventHandler
	public void join(LoginEvent event) {
		ProxyServer.getInstance().broadcast(new TextComponent("§7(§4B§7) §f>> §7Le joueur §4" + event.getConnection().getName() + " §7vient de se connecter au server !"));
		event.getConnection().getName();
	}
} 
