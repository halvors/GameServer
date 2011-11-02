package org.halvors.Game.Server.network.packet;

import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.entity.Player;

public class PacketVelocity extends PacketLocation {
	public PacketVelocity() {
		
	}
	
	public PacketVelocity(Location loc) {
		super(loc);
	}
	
	@Override
	public void run(Player player) {
		
	}
}
