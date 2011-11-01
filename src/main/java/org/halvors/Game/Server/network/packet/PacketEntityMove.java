package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.World;
import org.halvors.Game.Server.entity.Entity;

public class PacketEntityMove extends Packet {
	private final GameServer server = GameServer.getInstance();
	
	private Entity entity;
	
	public PacketEntityMove() {
		
	}
	
	public PacketEntityMove(Entity entity) {
		setEntity(entity);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		UUID id = UUID.fromString(input.readUTF());
		UUID worldId = UUID.fromString(input.readUTF());
		double x = input.readDouble();
		double y = input.readDouble();
		double z = input.readDouble();
		float pitch = input.readFloat();
		float yaw = input.readFloat();
		
		World world = server.getWorld(worldId);
		Location loc = new Location(world, x, y, z, pitch, yaw);
		Entity entity = new Entity(server, id, loc);
		setEntity(entity);
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		Location loc = entity.getLocation();
		World world = loc.getWorld();
		
		output.writeUTF(entity.getId().toString());
		output.writeUTF(world.getId().toString());
		output.writeDouble(loc.getX());
		output.writeDouble(loc.getY());
		output.writeDouble(loc.getZ());
		output.writeFloat(loc.getPitch());
		output.writeFloat(loc.getYaw());
	}
	
	@Override
	public void run() {
		
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
}
