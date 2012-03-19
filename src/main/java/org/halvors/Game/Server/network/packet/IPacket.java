package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Represents a base packet.
 * 
 * @author halvors
 */
public interface IPacket {
	/**
	 * Tells how the packet is supposed to be read from the stream.
	 * 
	 * @param input
	 * @throws IOException
	 */
	public void readData(DataInputStream input) throws IOException;
	
	/**
	 * Tells how the packet is supposed to be wrote from the stream.
	 * 
	 * @param output
	 * @throws IOException
	 */
	public void writeData(DataOutputStream output) throws IOException;
	
	/**
	 * Returns the packet's id.
	 * 
	 * @return id
	 */
	public int getId();
	
	/**
	 * Returns the packet type.
	 * 
	 * @return type
	 */
	public PacketType getType();
	
	/**
	 * Returns the packet's size.
	 * 
	 * @return
	 */
	public int getSize();
}
