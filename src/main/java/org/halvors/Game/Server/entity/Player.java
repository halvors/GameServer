package org.halvors.Game.Server.entity;

import java.net.InetAddress;

import org.halvors.Game.Server.network.NetworkManager;
import org.halvors.Game.Server.network.NetworkServerHandler;
import org.halvors.Game.Server.network.packet.PacketChat;

public class Player {
	private final String name;
	private final NetworkServerHandler networkServerHandler;
	
	public Player(String name, NetworkManager networkManager, NetworkServerHandler networkServerHandler) {
		this.name = name;
		this.networkServerHandler = networkServerHandler;
	}
	
	public String getName() {
		return name;
	}
	
	public InetAddress getInetAddress() {
		return networkServerHandler.getNetworkManager().getSocket().getInetAddress();
	}

	public NetworkServerHandler getNetworkServerHandler() {
		return networkServerHandler;
	}
}
