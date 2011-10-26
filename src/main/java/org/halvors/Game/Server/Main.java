package org.halvors.Game.Server;

public class Main {
	public static void main(String[] args) {
		try {
			// Start the server instance.
			GameServer server = new GameServer();
			server.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
