package org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class LoginHandler {
	private final GameServer server;
	private final Socket socket;
	
	private NetworkManager networkManager; // TODO: Where initialize this?
	private Player player;
	private ServerHandler serverHandler;
	
	public LoginHandler(GameServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}
	
	public void handleLogin(PacketLogin packet) throws IOException {
		String name = packet.getUsername();
		String version = packet.getVersion();
		
		if (name != null && version != null) {			
			// Create Player and ServerHandler.
			networkManager = new NetworkManager(server, socket);
			player = new Player(server, name);
			serverHandler = new ServerHandler(server, networkManager, player);
			
			if (server.getVersion() != version) {
				networkManager.disconnect("You are using an old version: " + version);
			}
			
			// Send reply to the client.
			networkManager.sendPacket(new PacketLogin(name, version));
			
			// Inform server console.
			server.log(Level.INFO, name + " logged in with id: " + player.getId());
			
			// Send login message.
			server.broadcast(name + " joined the game.");
		}
	}
	
	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ServerHandler getServerHandler() {
		return serverHandler;
	}
}
