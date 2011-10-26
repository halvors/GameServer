package org.halvors.Game.Server.entity;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.Location;

public class LivingEntity extends Entity {
	private int health;
	
	public LivingEntity(GameServer server, Location location) {
		super(server, location);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health <= 20) {
			this.health = health;
		}
	}
	
	public void damage(int damage) {
		damage = getHealth() - damage;
		
		if (damage < 20) {
			setHealth(damage);
		}
	}
	
	public void die() {
		setHealth(0);
		remove();
	}
}
