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
//			Random random = new Random();
//			Location loc = new Location(null, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, 0);
			player = new Player(server, name, null);
			server.addPlayer(player);
			
			// Create the ServerHandler.
			serverHandler = new ServerHandler(server, networkManager, player);
			
			// Send reply to the client.
			networkManager.sendPacket(new PacketLogin(name, version));
//			networkManager.sendPacket(new PacketWorld(server.getWorlds().get(0)));
//			networkManager.sendPacket(new PacketSpawnLocation(player.getLocation()));
//			
//			server.broadcastPacket(new PacketEntity(player));
			
			// Inform server console.
			server.log(Level.INFO, name + " logged in with id: " + player.getId());
			
			// Send login message.
			String message = name + " joined the game.";
			server.broadcast(message);
		} else {
			server.log(Level.WARNING, "--------------------------------------------");
		}
	}
	
	public void handleLogin(PacketLogin packet) {
		// Do login :)
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