package main.java.org.halvors.Game.Server.network;

import main.java.org.halvors.Game.Server.GameServer;
import main.java.org.halvors.Game.Server.network.packet.PacketChat;
import main.java.org.halvors.Game.Server.network.packet.PacketDisconnect;
import main.java.org.halvors.Game.Server.network.packet.PacketLogin;

public class NetworkServerHandler {;
	private final GameServer server;
	private final NetworkManager networkManager;
	
	public NetworkServerHandler(GameServer server, NetworkManager networkManager) {
		this.server = server;
		this.networkManager = networkManager;
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
}
