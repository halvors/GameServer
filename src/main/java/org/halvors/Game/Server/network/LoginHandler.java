package org.halvors.Game.Server.network;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class LoginHandler {
	private final GameServer server;
	
	public LoginHandler(GameServer server) {
		this.server = server;
	}
	
	public void handleLogin(PacketLogin packet) {
		String name = packet.username;
		String version = packet.version;
		
		if (name != null && version != null) {
			Player player = new Player(name, null); // TODO: Figure out how to do this with login and creation of player.
		}
	}
}
