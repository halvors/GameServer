package org.halvors.Game.Server.entity;

import java.io.IOException;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.network.NetworkManager;
import org.halvors.Game.Server.network.ServerHandler;
import org.halvors.Game.Server.network.packet.PacketChat;

public class Player extends LivingEntity {
	private final String name;
	
	private ServerHandler serverHandler;
	
	public Player(GameServer server, String name, Location location) {
		super(server, location);
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void sendMessage(String message) {
		getNetworkManager().sendPacket(new PacketChat(message));
	}
	
	public void kick(String reason) throws IOException {
		getNetworkManager().disconnect("You was kicked from the server: " + reason);
	}

	public NetworkManager getNetworkManager() {
		return serverHandler.getNetworkManager();
	}
	
	public ServerHandler getServerHandler() {
		return serverHandler;
	}

	public void setServerHandler(ServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}
}