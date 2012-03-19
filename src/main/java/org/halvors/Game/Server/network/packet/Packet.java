package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a base packet.
 */
public class Packet implements IPacket {
	public Packet() {
		
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		
	}
	
	@Override
	public int getId() {
		return getType().getId();
	}
	
	@Override
	public PacketType getType() {
		return PacketType.getPacketFromClass(getClass());
	}

	@Override
	public int getSize() {
		return 0;
	}
}
