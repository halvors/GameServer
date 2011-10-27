package org.halvors.Game.Server.network.packet;

import java.util.HashMap;

public enum PacketType {
	PacketLogin(1, PacketLogin.class),
	PacketChat(2, PacketChat.class),
	PacketDisconnect(255, PacketDisconnect.class);

	private final int id;
	private final Class<? extends Packet> packetClass;
	private static final HashMap<Integer, PacketType> lookupId = new HashMap<Integer, PacketType>();
	
	PacketType(final int type, final Class<? extends Packet> packetClass) {
		this.id = type;
		this.packetClass = packetClass;
	}

	public int getId() {
		return id;
	}

	public Class<? extends Packet> getPacketClass() {
		return packetClass;
	}

	public static PacketType getPacketFromId(int id) {
		return lookupId.get(id);
	}

	static {
		for (PacketType packet : values()) {
			lookupId.put(packet.getId(), packet);
		}
	}
}