package org.halvors.Game.Server.entity;

import java.net.InetAddress;

import org.halvors.Game.Server.network.NetworkManager;
import org.halvors.Game.Server.network.NetworkServerHandler;

public class Player {
	private final String name;
	
	private NetworkServerHandler networkServerHandler;
	
	public Player(String name) {
		this.name = name;
		this.setNetworkServerHandler(networkServerHandler);
	}

	public String getName() {
		return name;
	}

	public NetworkManager getNetworkManager() {
		return networkServerHandler.getNetworkManager();
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
}