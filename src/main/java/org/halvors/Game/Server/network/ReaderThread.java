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
		this.input = networkManager.getDataInputStream();
	}
	
	@Override
	public void run() {
		Packet packet = null;
		
		while (true) {
			try {
				packet = PacketUtil.readPacket(input);
				
				if (packet != null) {
					PacketUtil.handlePacket(packet, networkManager.getServerHandler());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}