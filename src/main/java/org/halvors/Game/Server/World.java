package org.halvors.Game.Server;

import java.util.Random;
import java.util.UUID;

public class World {
	private final Random random = new Random();
	private final String name;
	private final UUID id = UUID.randomUUID();
	private Location spawnLocation = new Location(this, random.nextInt(), random.nextInt(), random.nextInt());
	
	public World(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public UUID getId() {
		return id;
	}

	public Location getSpawnLocation() {
		return spawnLocation;
	}

	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation;
	}
}
