package org.halvors.Game.Server.entity;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.NetworkManager;
import org.halvors.Game.Server.network.ServerHandler;
import org.halvors.Game.Server.network.packet.PacketChat;

public class Player extends LivingEntity {
	private final GameServer server;
	
	private final String name;
	
	private ServerHandler serverHandler;
	private NetworkManager networkManager;
	
	public Player(GameServer server, String name) {
		super(server);
		
		this.server = server;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void kick(String reason) {
		networkManager.disconnect("You was kicked from the server: " + reason);
	}
	
	public void ban(String reason) {
		networkManager.disconnect("You were banned from the server: " + reason);
		server.getConfiguration().banPlayer(getName());
	}
	
	public void sendMessage(String message) {
		networkManager.sendPacket(new PacketChat(message));
	}
	
	public ServerHandler getServerHandler() {
		return serverHandler;
	}
	
	public void setServerHandler(ServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}
	
	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}
}