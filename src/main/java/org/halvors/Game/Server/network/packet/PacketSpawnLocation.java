package org.halvors.Game.Server.network.packet;

import org.halvors.Game.Server.Location;

public class PacketSpawnLocation extends PacketLocation {
	public PacketSpawnLocation() {
		
	}
	
	public PacketSpawnLocation(Location loc) {
		super(loc);
	}
}
