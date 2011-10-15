package main.java.org.halvors.Game.Server;

public class Location {
	private final World world;
	private int x;
	private int y;
	private int z;
	
	public Location(World world, int x, int y, int z) {
		this.world = world;
		setX(x);
		setY(y);
		setZ(z);
	}

	public World getWorld() {
		return world;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
