package org.halvors.Game.Server.network;

import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketChat;
import org.halvors.Game.Server.network.packet.PacketDisconnect;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class NetworkServerHandler {;
	private final GameServer server;
	private final NetworkManager networkManager;
	private final Player player;
	
	public NetworkServerHandler(GameServer server, NetworkManager networkManager, Player player) {
		this.server = server;
		this.networkManager = networkManager;
		this.player = player;
		player.setNetworkServerHandler(this);
	}

	public void sendPacket(Packet packet) {
		networkManager.sendPacket(packet);
	}
	
	public void handlePacketLogin(PacketLogin packet) {
		String message = player.getName() + " joined the game.";
		
		// Print the message to the console.
		server.log(Level.INFO, message);
			
		// Send the message to all the other players.
		networkManager.broadcastPacket(new PacketChat(message));
	}
	
	public void handlePacketChat(PacketChat packet) {
		String message = player.getName() + ": " + packet.getMessage();
		
		// Print the message to the console.
		server.log(Level.INFO, message);
		
		// Send the message to all the other players.
		networkManager.broadcastPacket(new PacketChat(message));
	}
	
	public void handlePacketDisconnect(PacketDisconnect packet) {
		String message = player.getName() + " left the game.";
		
		// Print the message to the console.
		server.log(Level.INFO, message);
			
		// Send the message to all the other players.
		networkManager.broadcastPacket(new PacketChat(message));
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public GameServer getServer() {
		return server;
	}

	public Player getPlayer() {
		return player;
	}
}
