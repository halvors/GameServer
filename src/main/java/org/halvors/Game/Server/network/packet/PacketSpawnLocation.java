package org.halvors.Game.Server.network.packet;

import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.entity.Player;

public class PacketSpawnLocation extends PacketLocation {
	public PacketSpawnLocation() {
		
	}
	
	public PacketSpawnLocation(Location loc) {
		super(loc);
	}
	
	@Override
	public void run(Player player) {
		
	}
}
