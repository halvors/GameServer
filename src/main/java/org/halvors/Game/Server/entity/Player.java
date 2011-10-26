package org.halvors.Game.Server.entity;

import org.halvors.Game.Server.network.NetworkManager;
import org.halvors.Game.Server.network.packet.PacketChat;

public class Player {
	private final String name;
	private final NetworkManager networkManager;
	
	public Player(String name, NetworkManager networkManager) {
		this.name = name;
		this.networkManager = networkManager;
	}
	
	public void sendChatMessage(String message) {
		if (message != null) {
			networkManager.broadcastPacket(new PacketChat(message));
		}
	}
	
	public String getName() {
		return name;
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}
}
