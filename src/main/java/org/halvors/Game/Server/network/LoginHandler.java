package org.halvors.Game.Server.network;

import java.net.Socket;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class LoginHandler {
	private final GameServer server;
	private final NetworkManager networkManager;
	
	private NetworkServerHandler networkServerHandler;
	
	public LoginHandler(GameServer server, NetworkManager networkManager) {
		this.server = server;
		this.networkManager = networkManager;
	}
	
	public void handleLogin(PacketLogin packet) {
		String name = packet.username;
		String version = packet.version;
		
		if (name != null && version != null) {
			// TODO: Load player here.
			networkServerHandler = new NetworkServerHandler(server, networkManager);
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