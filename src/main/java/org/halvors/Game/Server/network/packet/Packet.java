package main.java.org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import main.java.org.halvors.Game.Server.network.NetworkManager;
import main.java.org.halvors.Game.Server.network.NetworkServerHandler;

/**
 * Represents a base packet.
 */
public abstract class Packet {
	private static HashMap<Integer, Class<?>> packetIdToClassMap = new HashMap<Integer, Class<?>>();
    private static HashMap<Class<?>, Integer> packetClassToIdMap = new HashMap<Class<?>, Integer>();
    
	static {
        addIdClassMapping(1, PacketLogin.class);
        addIdClassMapping(2, PacketChat.class);
        addIdClassMapping(255, PacketDisconnect.class);
    }
    
	public static Packet readPacket(DataInputStream input) {
		try {
			// Read the id form the DataInputStream.
            int id = input.read();
            
            // Packet's in out system can't be less than 0.
            if (id >= 0) {
            	Packet packet = getNewPacket(id);
            	
            	// Check if the packet was found in the HashMap, if not throw an Exception.
            	if (packet == null) {
            		throw new IOException("Bad packet id " + id);
            	}
            	
            	// Read the packet data.
            	packet.readPacketData(input);
            
            	return packet;
            }
        } catch(IOException e) {
            System.out.println("Reached end of stream.");
            e.printStackTrace();
        }
		
		return null;
	}
	
    public void writePacket(Packet packet, DataOutputStream output) throws IOException{
        output.write(packet.getPacketId());
        packet.writePacketData(output);
    }
	
    public abstract void readPacketData(DataInputStream input) throws IOException;
	
	public abstract void writePacketData(DataOutputStream output) throws IOException;
	
	/**
	 * Get the id for the current packet.
	 * 
	 * @return the id.
	 */
	public int getPacketId() {
		return packetClassToIdMap.get(getClass());
	}
	
	public void handlePacket(Packet packet, NetworkManager networkManager) {
		// First check if we need to handle the login.
		if (packet instanceof PacketLogin) {
//			networkManager.getLoginHandler().handleLogin((PacketLogin) packet);
		} else {
			// Then if login is done, we have a NetworkServerHandler.
			NetworkServerHandler networkServerHandler = networkManager.getNetworkServerHandler();
			
			if (networkServerHandler != null) {
				if (packet instanceof PacketChat) {
					networkServerHandler.handlePacketChat((PacketChat) packet);
				} else if (packet instanceof PacketDisconnect) {
					networkServerHandler.handlePacketDisconnect((PacketDisconnect) packet);
				}
			}
		}
	}
    
	/**
	 * Get a new packet from a specific id.
	 * 
	 * @param id
	 * @return the Packet.
	 */
	public static Packet getNewPacket(int id) {
		try {
			Class<?> clazz = packetIdToClassMap.get(id);

			if (clazz != null) {
	          	return (Packet) clazz.newInstance();
			}
        } catch(Exception e) {
        	e.printStackTrace();
        }
		
		return null;
    }
	
	/**
	 * Add a new class to the Class/Id map. This should not be done dynamiclly.
	 * 
	 * @param id
	 * @param clazz
	 */
	public static void addIdClassMapping(int id, Class<?> clazz) {
		if (packetIdToClassMap.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate packet id:" + id);
        }
		
        if (packetClassToIdMap.containsKey(clazz)) {
            throw new IllegalArgumentException("Duplicate packet class:" + clazz);
        }
        
        packetIdToClassMap.put(id, clazz);
        packetClassToIdMap.put(clazz, id);
    }
}
