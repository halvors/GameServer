package org.halvors.Game.Server.network.packet;

import java.util.HashMap;

public enum PacketType {
	PacketLogin(1, PacketLogin.class),
	PacketChat(2, PacketChat.class),
	PacketDisconnect(255, PacketDisconnect.class);

	private final int id;
	private Class<? extends IPacket> clazz;
	
	private static final HashMap<Integer, PacketType> lookupId = new HashMap<Integer, PacketType>();
	private static final HashMap<Class<?>, PacketType> lookupClass = new HashMap<Class<?>, PacketType>();
	
	PacketType(int id, Class<? extends IPacket> clazz) {
		this.id = id;
		this.clazz = clazz;
	}

	public int getId() {
		return id;
	}

	public Class<? extends IPacket> getPacketClass() {
		return clazz;
	}
	
	public static PacketType getPacketFromId(int id) {
		return lookupId.get(id);
	}

	public static PacketType getPacketFromClass(Class<? extends IPacket> clazz) {
		return lookupClass.get(clazz);
	}

	static {
		for (PacketType packet : values()) {
			lookupId.put(packet.getId(), packet);
			lookupClass.put(packet.getPacketClass(), packet);
		}
	}
}