package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.halvors.Game.Server.World;

public class PacketWorld extends Packet {
	private World world;
	
	public PacketWorld() {
		
	}
	
	public PacketWorld(World world) {
		setWorld(world);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		String name = input.readUTF();
		UUID id = UUID.fromString(input.readUTF());
		
		// Recreate the objects based on the read info.
		World world = new World(name, id);
		setWorld(world);
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeUTF(world.getName());
		output.writeUTF(world.getId().toString());
	}
	
	@Override
	public void run() {
		
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
