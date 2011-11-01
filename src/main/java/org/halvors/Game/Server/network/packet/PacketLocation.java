package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.World;

public class PacketLocation extends Packet {
	private final GameServer server = GameServer.getInstance();
	
	private Location loc;
	
	public PacketLocation() {
		
	}
	
	public PacketLocation(Location loc) {
		setLocation(loc);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		UUID id = UUID.fromString(input.readUTF());
		double x = input.readDouble();
		double y = input.readDouble();
		double z = input.readDouble();
		float pitch = input.readFloat();
		float yaw = input.readFloat();
		
		World world = server.getWorld(id);
		Location loc = new Location(world, x, y, z, pitch, yaw);
		setLocation(loc);
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		World world = loc.getWorld();
		
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
	
	public Location getLocation() {
		return loc;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
	}
}
