package main.java.org.halvors.Game.Server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.org.halvors.Game.Server.entity.Player;
import main.java.org.halvors.Game.Server.network.NetworkListenThread;

public class GameServer {
	private static GameServer instance;
	
	private final String name = "Game";
	private final String version = "0.0.1";
	
	private final Logger logger = Logger.getLogger("Game");
	private final List<World> worlds = new ArrayList<World>();
	private final List<Player> players = new ArrayList<Player>();
	
	private Configuration configuration;
	private Thread thread;
	
	public GameServer() {
		GameServer.instance = this;
	}
	
	public void main(String[] args) {
		log(Level.INFO, "Starting " + getName() + "Server " + getVersion());
		
		// Loading configuration.
		configuration = new Configuration(this, new File("server.properties"));
		String host = configuration.getStringProperty("host", "0.0.0.0");
		int port = configuration.getIntProperty("port", 7846);
		
		// Check if host is greater than 0.
		if (host.length() > 0 && port > 0) {
			try {
				thread = new NetworkListenThread(this, InetAddress.getByName(host), port);
				thread.start();
				log(Level.INFO, "Server is running on: " + host + ":" + Integer.toString(port));
			} catch (IOException e) {
				log(Level.WARNING, "Failed to bind to port: " + Integer.toString(port));
				e.printStackTrace();
				// TODO: Maybe shut down server here. No need for a server that isn't listening for clients :P
			}
		}
	}

	public static GameServer getInstance() {
		return instance;
	}
	
	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public void log(Level level, String message) {
		logger.log(level, message);
	}
	
	public List<World> getWorlds() {
		return worlds;
	}
	
	public World getWorld(UUID id) {
		for (World world : worlds) {
			if (id.equals(world.getId())) {
				return world;
			}
		}
		
		return null;
	}
	
	public World getWorld(String name) {
		for (World world : worlds) {
			if (name.equals(world.getName())) {
				return world;
			}
		}
		
		return null;
	}
	
	public World createWorld(String name) {
		World world = new World(name);
		
		return world;
	}
	
	public void removeWorld(UUID id) {
		World world = getWorld(id);
		
		if (world != null) {
			worlds.remove(world);
		}
	}
	
	public void removeWorld(String name) {
		World world = getWorld(name);
		
		if (world != null) {
			worlds.remove(world);
		}
	} 
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer(String name) {
		for (Player player : players) {
			if (name.equalsIgnoreCase(player.getName())) {
				return player;
			}
		}
		
		return null;
	}
	
	public void addPlayer(Player player) {
		if (player != null && !players.contains(player)) {
			players.add(player);
		}
	}
	
	public void removePlayer(Player player) {
		if (players.contains(player)) {
			players.remove(player);
		}
	}

	public Configuration getConfiguration() {
		return configuration;
	}
	
	public NetworkListenThread getNetworkListenThread() {
		if (thread instanceof NetworkListenThread) {
			return (NetworkListenThread) thread;
		}
		
		return null;
	}
}