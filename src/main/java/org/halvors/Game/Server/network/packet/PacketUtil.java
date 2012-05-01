package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.halvors.Game.Server.network.ServerHandler;

public class PacketUtil {
	public static IPacket readPacket(DataInputStream input) throws IOException {
		if (input != null) {
			// Read the id form the DataInputStream.
			int id = input.read();
			
			// Packet's id can't be lesser than 0.
			if (id > 0) {
				IPacket packet = getPacketFromId(id);
            	
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
	
    public static void writePacket(DataOutputStream output, IPacket packet) throws IOException {
    	if (output != null && packet != null) {
    		// Write the packet id to the stream.
    		output.write(packet.getId());
    		
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
	public static IPacket getPacketFromId(int id) {
		Class<? extends IPacket> packet = PacketType.getPacketFromId(id).getPacketClass();
		
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
     * @param serverHandler
     */
	public static void handlePacket(IPacket packet, ServerHandler handler) {
		if (packet != null && handler != null) {
			PacketType type = packet.getType();
			
			// Detect PacketType and handle it in the right way.
			switch (type) {
			case PacketChat:
				handler.handlePacketChat((PacketChat) packet);
				break;
				
	        case PacketDisconnect:
	        	handler.handlePacketDisconnect((PacketDisconnect) packet);
	        	break;
			}
		}
	}
}