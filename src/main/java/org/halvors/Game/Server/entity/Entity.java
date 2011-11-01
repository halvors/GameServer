package org.halvors.Game.Server.entity;

import java.util.UUID;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.World;

public class Entity {
	private final GameServer server;
	private final UUID id;
	
	private World world;
	private Location location;
	
	public Entity(GameServer server, UUID id, Location loc) {
		this.server = server;
		this.id = id;
		setWorld(loc.getWorld());
		setLocation(loc);
	}
	
	public Entity(GameServer server, Location loc) {
		this(server, UUID.randomUUID(), loc);
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public UUID getId() {
		return id;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void remove() {
		world.removeEntity(this);
	}
}
