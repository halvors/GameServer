package org.halvors.Game.Server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.halvors.Game.Server.network.packet.Packet;

public class NetworkWriterThread extends Thread {
	private final NetworkManager networkManager;
	private final Socket socket;
	
	public NetworkWriterThread(NetworkManager networkManager) {
		this.networkManager = networkManager;
		this.socket = networkManager.getSocket();
	}
	
	public void run() {
		try {
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			Packet packet = null;
			
			while (socket.isConnected()) {
				packet = networkManager.getPacketQueue().poll();
				
				if (packet != null && output != null) {
					packet.writePacket(packet, output);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
