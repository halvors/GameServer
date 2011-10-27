package org.halvors.Game.Server.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.halvors.Game.Server.network.packet.Packet;

public class WriterThread extends Thread {
	private final NetworkManager networkManager;
	private final Socket socket;
	
	public WriterThread(String name, NetworkManager networkManager) {
		super(name);
		this.networkManager = networkManager;
		this.socket = networkManager.getSocket();
	}
	
	@Override
	public void run() {
		DataOutputStream output = null;
		Packet packet = null;
		
		while (socket.isConnected()) {
			try {
				output = new DataOutputStream(socket.getOutputStream());
				packet = networkManager.getPacketQueue().poll();
				
				if (packet != null && output != null) {
					packet.writePacket(packet, output);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
