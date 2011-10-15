package main.java.org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketChat extends Packet {
	public String message;
	
	public PacketChat() {
		
	}
	
	public PacketChat(String message) {	
		this.message = message;
	}
	
	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		message = input.readUTF();
	}
	
	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeUTF(message);
	}
	
	public int getPacketSize() {
		return message.length();
	}
}