package main.java.org.halvors.Game.Server.entity;

import main.java.org.halvors.Game.Server.network.NetworkManager;

public class Player {
	private final String name;
	private final NetworkManager networkManager;
	
	public Player(String name, NetworkManager networkManager) {
		this.name = name;
		this.networkManager = networkManager;
	}
	
	public String getName() {
		return name;
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}
}
