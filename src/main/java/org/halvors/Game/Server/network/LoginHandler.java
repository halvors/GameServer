package org.halvors.Game.Server.network;

import java.net.Socket;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class LoginHandler {
	private final GameServer server;
	private final NetworkManager networkManager;
	
	private NetworkServerHandler networkServerHandler;
	
	public LoginHandler(GameServer server, Socket socket) {
		this.server = server;
		this.networkManager = new NetworkManager(server, socket, this, null); // TODO: Add socket id String argument.
	}
	
	public void handleLogin(PacketLogin packet) {
		String name = packet.getUsername();
		String version = packet.getVersion();
		
		if (name != null && version != null) {
			// TODO: Load player here.
			Player player = new Player(name);
			
			// Create the NetworkServerHandler.
			networkServerHandler = new NetworkServerHandler(server, networkManager, player);
			
			// Send reply to the client.
			networkManager.sendPacket(new PacketLogin(name, version));
		}
	}

	public GameServer getServer() {
		return server;
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	
	public NetworkServerHandler getNetworkServerHandler() {
		return networkServerHandler;
	}
}