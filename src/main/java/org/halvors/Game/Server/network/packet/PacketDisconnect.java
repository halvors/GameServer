package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.halvors.Game.Server.entity.Player;

public class PacketDisconnect extends Packet {
	public String reason;
	
	public PacketDisconnect() {
		
	}
	
	public PacketDisconnect(String reason) {
		this.reason = reason;
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		reason = input.readUTF();
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeUTF(reason);
	}
	
	@Override
	public void run(Player player) {
		
	}
	
	public int getPacketSize() {
		return reason.length();
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
