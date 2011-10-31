package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.halvors.Game.Server.network.ServerHandler;

public class PacketUtil {
	public static Packet readPacket(DataInputStream input) throws IOException, InstantiationException, IllegalAccessException {
		// Read the id form the DataInputStream.
        int id = input.read();
            
        // Packet's in out system can't be less than 0.
        if (id >= 0) {
            Packet packet = getPacketInstance(id);
            	
            // Check if the packet was found in the HashMap, if not throw an Exception.
            if (packet == null) {
            	throw new IOException("Bad packet id " + id);
            }
            
            // Read the packet data.
            packet.readPacketData(input);
            
            return packet;
        }
		
		return null;
	}
	
    public static void writePacket(Packet packet, DataOutputStream output) throws IOException {
        output.write(packet.getPacketId());
        packet.writePacketData(output);
    }
    
    /**
     * Handle packet's after being read.
     * 
     * @param packet
     * @param handler
     */
	public static void handlePacket(Packet packet, ServerHandler handler) {
		PacketType type = packet.getPacketType();
		
		// Detect PacketType and handle it in the right way :D
		switch (type) {
		case PacketChat:
			handler.handlePacketChat((PacketChat) packet);
			break;

        case PacketDisconnect:
        	handler.handlePacketDisconnect((PacketDisconnect) packet);
        	break;
		}
	}
    
	/**
	 * Get a new packet from a specific id.
	 * 
	 * @param id
	 * @return the Packet.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static Packet getPacketInstance(int id) throws InstantiationException, IllegalAccessException {
		Class<? extends Packet> packet = PacketType.getPacketFromId(id).getPacketClass();

		if (packet != null) {
			return packet.newInstance();
		}
		
		return null;
    }
}