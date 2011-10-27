package org.halvors.Game.Server.network;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
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
	}

	public void handlePacketLogin(PacketLogin packet) {
		System.out.println("Player joined the game.");
	}
	
	public void handlePacketChat(PacketChat packet) {
		System.out.println("Player: " + packet.message);
	}
	
	public void handlePacketDisconnect(PacketDisconnect packet) {
		System.out.println("Player left the game.");
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
