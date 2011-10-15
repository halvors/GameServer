package main.java.org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import main.java.org.halvors.Game.Server.network.packet.Packet;

public class NetworkReaderThread extends Thread {
	private final NetworkManager networkManager;
	private final Socket socket;
	
	public NetworkReaderThread(NetworkManager networkManager) {
		this.networkManager = networkManager;
		this.socket = networkManager.getSocket();
	}
	
	public void run() {
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			Packet packet = null;
			
			while (socket.isConnected()) {
				packet = Packet.readPacket(input);
			
				if (packet != null && input != null) {
					packet.handlePacket(packet, networkManager);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}