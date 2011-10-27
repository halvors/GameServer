package org.halvors.Game.Server.entity;

import java.net.InetAddress;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.network.NetworkManager;
import org.halvors.Game.Server.network.NetworkServerHandler;
import org.halvors.Game.Server.network.packet.PacketChat;

public class Player extends LivingEntity {
	private final GameServer server;
	private final String name;
	
	private NetworkServerHandler networkServerHandler;
	private NetworkManager networkManager;
	
	public Player(GameServer server, String name) {
		super(server);
		this.server = server;
		this.name = name;
		setNetworkServerHandler(networkServerHandler);
		setNetworkManager(networkServerHandler.getNetworkManager());
	}
	
	public String getName() {
		return name;
	}
	
	public void sendMessage(String message) {
		networkManager.sendPacket(new PacketChat(message));
	}
	
	public void kick(String reason) {
		networkManager.disconnect(reason);
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	
	public NetworkServerHandler getNetworkServerHandler() {
		return networkServerHandler;
	}

	public void setNetworkServerHandler(NetworkServerHandler networkServerHandler) {
		this.networkServerHandler = networkServerHandler;
	}
	
	public InetAddress getInetAddress() {
		return networkServerHandler.getNetworkManager().getSocket().getInetAddress();
	}

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	public GameServer getServer() {
		return server;
	}
}