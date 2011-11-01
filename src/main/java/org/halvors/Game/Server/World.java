package org.halvors.Game.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.halvors.Game.Server.entity.Entity;

public class World {
	private final UUID id;
	private final String name;
	private final WorldType type;
	private final List<Entity> entities = new ArrayList<Entity>();
	private final Random random = new Random();
	
	private Location spawnLocation = new Location(this, random.nextDouble(), random.nextDouble(), random.nextDouble(), 0, 0);
	
	public World(String name, UUID id, WorldType type) {
		this.name = name;
		this.id = id;
		this.type = type;
	}
	
	public World(String name, UUID id) {
		this(name, id, WorldType.NORMAL);
	}
	
	
	public World(String name, WorldType type) {
		this(name, UUID.randomUUID(), type);
	}
	
	public World(String name) {
		this(name, WorldType.NORMAL);
	}

	public String getName() {
		return name;
	}

	public UUID getId() {
		return id;
	}
	
	public WorldType getType() {
		return type;
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
	
//	public Entity spawn(Location location, Class<? extends Entity> clazz) throws IllegalArgumentException {
//		if (location == null || clazz == null) {
//			throw new IllegalArgumentException("Location or entity class cannot be null");
//		}
//
//		Entity entity = null;
//
//		double x = location.getX();
//		double y = location.getY();
//		double z = location.getZ();
//		float pitch = location.getPitch();
//		float yaw = location.getYaw();
//		
//		if (entity != null) {
//			addEntity(entity);
//			return entity;
//		}
//		
//		throw new IllegalArgumentException("Cannot spawn an entity for " + clazz.getName());
//	}
//}
