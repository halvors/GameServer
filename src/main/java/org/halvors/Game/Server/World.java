package org.halvors.Game.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.halvors.Game.Server.entity.Entity;

public class World {
	private final UUID id = UUID.randomUUID();
	private final String name;
	private final List<Entity> entities = new ArrayList<Entity>();
	private final Random random = new Random();
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

	public List<Entity> getEntities() {
		return entities;
	}
	
	public Entity getEntity(UUID id) {
		for (Entity e : entities) {
			if (e.getId().equals(id)) {
				return e;
			}
		}
		
		return null;
	}
	
	public void addEntity(Entity entity) {
		if (!entities.contains(entity)) {
			entities.add(entity);
		}
	}
	
	public void removeEntity(Entity entity) {
		if (entities.contains(entity)) {
			entities.remove(entity);
		}
	}
}
