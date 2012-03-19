package org.halvors.Game.Server;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.ListenThread;

public class GameServer {
	private static GameServer instance;
	
	private final String name = "Game";
	private final String version = "0.0.2";
	
	private final Logger logger = Logger.getLogger("Game");
	private final Configuration config = new Configuration(this, new File("server.properties"));
	private final List<World> worlds = new ArrayList<World>();
	private final List<Player> players = new ArrayList<Player>();
	
	private ListenThread listenThread;
	private boolean isRunning = true;
	
	public GameServer() {
		GameServer.instance = this;
	}
	
	public void main(String[] args) {
		log(Level.INFO, "Starting " + getName() + "Server " + getVersion());
		
		// Initialize.
		addWorld("world");
		
		// Load configuration.
		String host = config.getStringProperty("ip-address", "0.0.0.0");
		int port = config.getIntProperty("port", 22075);
		
		// Check if host is greater than 0.
		if (!host.isEmpty()) {			
			try {
				listenThread = new ListenThread("Listen thread", this, InetAddress.getByName(host), port);
				listenThread.start();
			} catch (UnknownHostException e) {
				e.printStackTrace();
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
	
	public boolean hasWorld(World world) {
		return worlds.contains(world);
	}
	
	public World addWorld(World world) {
		if (world != null && !worlds.contains(world)) {
			worlds.add(world);
			
			return world;
		}
		
		return null;
	}
	
	public World addWorld(String name) {
		return addWorld(new World(name));
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
	
	public Player getPlayer(UUID id) {
		for (Player player : players) {
			if (id.equals(player.getId())) {
				return player;
			}
		}
		
		return null;
	}
	
	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}
	
	public Player addPlayer(Player player) {
		if (player != null && !players.contains(player)) {
			players.add(player);
			
			return player;
		}
		
		return null;
	}
	
	public Player addPlayer(String name) {
		return addPlayer(new Player(this, name));
	}
	
	public void removePlayer(Player player) {
		if (players.contains(player)) {
			players.remove(player);
		}
	}
	
	public void removePlayer(String name) {
		removePlayer(getPlayer(name));
	}
	
	public void broadcast(String message) {
		for (Player p : players) {
			p.sendMessage(message);
		}
	}

	public Configuration getConfiguration() {
		return config;
	}
	
	public ListenThread getNetworkListenThread() {
		return listenThread;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}