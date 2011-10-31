package org.halvors.Game.Server.entity;

import java.io.IOException;
import java.net.SocketAddress;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.NetworkManager;
import org.halvors.Game.Server.network.ServerHandler;
import org.halvors.Game.Server.network.packet.PacketChat;

public class Player extends LivingEntity {
	private final GameServer server;
	private final String name;
	
	private ServerHandler networkServerHandler;
	
	public Player(GameServer server, String name) {
		super(server);
		this.server = server;
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
		return networkServerHandler.getNetworkManager();
	}
	
	public ServerHandler getNetworkServerHandler() {
		return networkServerHandler;
	}

	public void setNetworkServerHandler(ServerHandler networkServerHandler) {
		this.networkServerHandler = networkServerHandler;
	}
	
	public SocketAddress getSocketAddress() {
		return getNetworkManager().getSocket().getRemoteSocketAddress();
	}

	public GameServer getServer() {
		return server;
	}
}