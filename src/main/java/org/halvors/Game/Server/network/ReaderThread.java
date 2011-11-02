package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.IOException;

import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketUtil;

public class ReaderThread extends Thread {
	private final NetworkManager networkManager;
	private final DataInputStream input;
	
	public ReaderThread(String name, NetworkManager networkManager) {
		super(name);
		this.networkManager = networkManager;
		this.input = networkManager.getInput();
		
	}
	
	@Override
	public void run() {
		Packet packet = null;
		
		while (networkManager.isConnected()) {
			try {
				packet = PacketUtil.readPacket(input);
				
				if (packet != null && input != null) {
					PacketUtil.handlePacket(packet, networkManager.getServerHandler());
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}