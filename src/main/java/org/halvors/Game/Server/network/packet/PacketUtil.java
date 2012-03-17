package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.halvors.Game.Server.network.ServerHandler;

public class PacketUtil {
	public static Packet readPacket(DataInputStream input) throws IOException {
		if (input != null) {
			// Read the id form the DataInputStream.
			int id = input.read();
            
			// Packet's id can't be than 0.
			if (id >= 0) {
				Packet packet = getPacketFromId(id);
            	
	            // Check if the packet was found in the HashMap, if not throw an Exception.
	            if (packet != null) {
	            	// Read the packet data.
	            	packet.readData(input);
	            
	            	return packet;
	            } else {
	            	throw new IOException("Bad packet id: " + id);
	            }
	        }
		}
		
		return null;
	}
	
    public static void writePacket(DataOutputStream output, Packet packet) throws IOException {
    	if (output != null && packet != null) {
    		// Write the packet id to the stream.
    		output.write(packet.getPacketId());
    		
    		// Then write the packet data.
        	packet.writeData(output);
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
	public static Packet getPacketFromId(int id) {
		Class<? extends Packet> packet = PacketType.getPacketFromId(id).getPacketClass();
		
		if (packet != null) {
			try {
				return packet.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		return null;
    }
	
    /**
     * Handle packet's after being read.
     * 
     * @param packet
     * @param handler
     */
	public static void handlePacket(Packet packet, ServerHandler serverHandler) {
		if (packet != null && serverHandler != null) {
			PacketType type = packet.getPacketType();
			
			// Detect PacketType and handle it in the right way.
			switch (type) {
			case PacketChat:
				serverHandler.handlePacketChat((PacketChat) packet);
				break;
				
	        case PacketDisconnect:
	        	serverHandler.handlePacketDisconnect((PacketDisconnect) packet);
	        	break;
			}
		}
	}
}