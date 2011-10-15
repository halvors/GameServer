package main.java.org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketDisconnect extends Packet {
	private String reason;
	
	public PacketDisconnect() {
		
	}
	
	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		reason = input.readUTF();
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeUTF(reason);
	}
	
	public int getPacketSize() {
		return reason.length();
	}
}
