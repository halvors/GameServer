package org.halvors.Game.Server.network;

import java.io.DataOutputStream;
import java.io.IOException;

import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketUtil;

public class WriterThread extends Thread {
	private final NetworkManager networkManager;
	
	public WriterThread(String name, NetworkManager networkManager) {
		super(name);
		this.networkManager = networkManager;
	}
	
	@Override
	public void run() {
		DataOutputStream output = networkManager.getOutput();
		Packet packet = null;
		
		while (networkManager.isRunning()) {
			try {
				packet = networkManager.getPacketQueue().poll();
				
				if (packet != null && output != null) {
					PacketUtil.writePacket(packet, output);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
