package org.halvors.Game.Server;

public class Location {
	private World world;
	private float x;
	private float y;
	private float z;
	private float pitch;
    private float yaw;
	
	public Location(World world, float x, float y, float z, float pitch, float yaw) {
		setWorld(world);
		setX(x);
		setY(y);
		setZ(z);
		setPitch(pitch);
		setYaw(yaw);
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
}
