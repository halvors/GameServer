package org.halvors.Game.Server.entity;

import org.halvors.Game.Server.Location;

public class LivingEntity extends Entity {
	private int health;
	
	public LivingEntity(Location location) {
		super(location);
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
}
