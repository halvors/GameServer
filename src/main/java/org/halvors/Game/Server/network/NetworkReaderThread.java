package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import org.halvors.Game.Server.network.packet.Packet;

public class NetworkReaderThread extends Thread {
	private final NetworkManager networkManager;
	private final Socket socket;
	
	public NetworkReaderThread(NetworkManager networkManager) {
		this.networkManager = networkManager;
		this.socket = networkManager.getSocket();
	}
	
	@Override
	public void run() {
		DataInputStream input = null;
		Packet packet = null;
		
		while (socket.isConnected()) {
			try {
				input = new DataInputStream(socket.getInputStream());
				packet = Packet.readPacket(input);
				
				if (packet != null && input != null) {
					packet.handlePacket(packet, networkManager); // TODO: Fix this little issue.
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}