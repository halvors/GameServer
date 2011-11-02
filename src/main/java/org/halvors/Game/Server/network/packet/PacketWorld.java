package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.halvors.Game.Server.World;
import org.halvors.Game.Server.WorldType;
import org.halvors.Game.Server.entity.Player;

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
		WorldType type = WorldType.getWorldType(input.readInt());
		
		// Recreate the objects based on the read info.
		World world = new World(name, id, type);
		setWorld(world);
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeUTF(world.getName());
		output.writeUTF(world.getId().toString());
		output.writeInt(world.getType().getId());
	}
	
	@Override
	public void run(Player player) {
		
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
