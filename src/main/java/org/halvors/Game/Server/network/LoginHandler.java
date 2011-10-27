package org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class LoginHandler {
	private final GameServer server;
	private final NetworkManager networkManager;
	
	private Player player;
	private NetworkServerHandler networkServerHandler;
	
	public LoginHandler(GameServer server, Socket socket) throws IOException {
		this.server = server;
		this.networkManager = new NetworkManager(server, socket, this, null); // TODO: Add socket id String argument.
	}
	
	public void login(PacketLogin packet) {
		String name = packet.getUsername();
		String version = packet.getVersion();
		
		if (name != null && version != null) {
			// TODO: Load player here.
			player = server.addPlayer(name);
			
			// Create the NetworkServerHandler.
			networkServerHandler = new NetworkServerHandler(server, networkManager, player);
			
			// Send reply to the client.
			networkManager.sendPacket(new PacketLogin(name, version));
			
			String message = name + " joined the game.";	
			
			// Print the message to the console.
			server.log(Level.INFO, message);
			
			// Send the message to all the other players.
			server.broadcast(message);
		}
	}
	
	public void disconnect(String reason) {
		networkManager.disconnect(reason);
	}
	
	public void handleLogin(PacketLogin packet) {
//		String clientVersion = packet.getVersion();
//		String serverVersion = server.getVersion();
//		
//		// Little version check here.
//		if (clientVersion != serverVersion) {
//            if (clientVersion > serverVersion) {
//                disconnect("Server is outdated!");
//            } else {
//                disconnect("Client is outdated!");
//            }
//            
//            return;
//        }
		
		// Version is ok, then login.
		login(packet);
	}

	public GameServer getServer() {
		return server;
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}
	
	public NetworkServerHandler getNetworkServerHandler() {
		return networkServerHandler;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}
}