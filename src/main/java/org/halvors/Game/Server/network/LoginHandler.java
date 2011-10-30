package org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.PacketDisconnect;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class LoginHandler {
	private final GameServer server;
	private final NetworkManager networkManager;
	
	private Player player;
	private ServerHandler serverHandler;
	
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
			
			// Create the ServerHandler.
			serverHandler = new ServerHandler(server, networkManager, player);
			
			// Send reply to the client.
			networkManager.sendPacket(new PacketLogin(name, version));
			
			// Inform server console.
			server.log(Level.INFO, name + " logged in with id: " + player.getId());
			
			// Send login message.
			String message = name + " joined the game.";
			server.broadcast(message);
		}
	}
	
	public void disconnect(String reason) {
		String message =  player.getName() + " left the game.";
		server.broadcast(message);
		
		networkManager.sendPacket(new PacketDisconnect(reason));
		networkManager.wakeThreads();
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
	
	public ServerHandler getServerHandler() {
		return serverHandler;
	}

	public Player getPlayer() {
		return player;
	}
}