package org.halvors.Game.Server.entity;

import java.util.UUID;

import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.World;

public class Entity {
	private final UUID id = UUID.randomUUID();
	private World world;
	private Location location;
	
	public Entity(Location location) {
		setWorld(location.getWorld());
		setLocation(location);
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
}
