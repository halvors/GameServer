package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a base packet.
 */
public abstract class Packet {
	public Packet() {
		
	}
	
    public abstract void readPacketData(DataInputStream input) throws IOException;
	
	public abstract void writePacketData(DataOutputStream output) throws IOException;
	
	/**
	 * Get the PacketType.
	 * 
	 * @return the PacketType.
	 */
	public PacketType getPacketType() {
		return PacketType.getPacketFromClass(getClass());
	}
	
	/**
	 * Get the packet id.
	 * 
	 * @return the packet id.
	 */
	public int getPacketId() {
		return getPacketType().getPacketId();
	}
}
