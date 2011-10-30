package org.halvors.Game.Server.entity;

import java.util.UUID;

import org.halvors.Game.Server.GameServer;

public class Entity {
	private final GameServer server;
	private final UUID id = UUID.randomUUID();
//	private World world;
//	private Location location;
	
	public Entity(GameServer server) {
		this.server = server;
//		this.world = location.getWorld();
//		this.location = location;
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public UUID getId() {
		return id;
	}
	
//	public World getWorld() {
//		return world;
//	}
//	
//	public Location getLocation() {
//		return location;
//	}
//
//	public void setLocation(Location location) {
//		this.location = location;
//	}
//	
//	public void remove() {
//		world.removeEntity(this);
//	}
}
