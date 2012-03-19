package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.PacketChat;
import org.halvors.Game.Server.network.packet.PacketDisconnect;

public class ServerHandler {
	private final GameServer server;
	private final NetworkManager networkManager;
	private final Player player;
	
	public ServerHandler(GameServer server, NetworkManager networkManager, Player player) {
		this.server = server;
		this.networkManager = networkManager;
		this.player = player;
		
		// Set this as ServerHandler for NetworkManager and Player.
		networkManager.setServerHandler(this);
		player.setServerHandler(this);
		player.setNetworkManager(networkManager);
	}
	
	public void handleErrorMessage(String reason, Object aobj[]) {
		server.log(Level.INFO, player.getName() + " lost connection: " + reason);
        
        server.broadcast(player.getName() + " left the game.");
        
        // Logout player here.
	}
	
	public void handlePacketChat(PacketChat packet) {
		String message = player.getName() + ": " + packet.getMessage();
		
		// Send the message to all the connected players.
		server.broadcast(message);
		
		server.log(Level.INFO, message);
	}
	
	public void handlePacketDisconnect(PacketDisconnect packet) {
		networkManager.shutdown();
		server.broadcast(player.getName() + " left the game.");
	}

	public GameServer getServer() {
		return server;
	}
	
	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public Player getPlayer() {
		return player;
	}
}
