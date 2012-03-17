package org.halvors.Game.Server.entity;

import java.util.UUID;

import org.halvors.Game.Server.GameServer;

public class Entity {
	private final GameServer server;
	private final UUID id;
	
	public Entity(GameServer server, UUID id) {
		this.server = server;
		this.id = id;
	}
	
	public Entity(GameServer server) {
		this(server, UUID.randomUUID());
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public UUID getId() {
		return id;
	}
	
	public void remove() {
//		world.removeEntity(this);
	}
}
