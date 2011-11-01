package org.halvors.Game.Server.entity;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.Location;
import org.halvors.Game.Server.network.packet.PacketEntityMove;

public class LivingEntity extends Entity {
	private final GameServer server;
	
	private int health;
	
	public LivingEntity(GameServer server, Location location) {
		super(server, location);
		this.server = server;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health <= 20) {
			this.health = health;
		}
	}
	
	public void heal(int amount) {
        if (health > 0 && health + amount <= 20) {
            health += amount;
        }
    }
	
	public void damage(int damage) {
		if (damage <= 20 && health - damage <= 20) {
			setHealth(damage);
		}
	}
	
	public void die() {
		setHealth(0);
		remove();
	}
	
	public void move() {
		// TODO: Implement this.
		server.broadcastPacket(new PacketEntityMove(this));
    }
}
