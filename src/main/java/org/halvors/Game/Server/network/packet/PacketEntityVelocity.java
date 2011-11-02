package org.halvors.Game.Server.network.packet;

import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.entity.Player;

public class PacketEntityVelocity extends PacketLocation {
	public PacketEntityVelocity() {
		
	}
	
	public PacketEntityVelocity(Location loc) {
		super(loc);
	}
	
	@Override
	public void run(Player player) {
		
	}
}
