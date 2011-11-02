package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.halvors.Game.Server.entity.Player;

public class PacketChat extends Packet {
	public String message;
	
	public PacketChat() {
		
	}
	
	public PacketChat(String message) {	
		setMessage(message);
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		message = input.readUTF();
	}
	
	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeUTF(message);
	}
	
	@Override
	public void run(Player player) {
		
	}
	
	public int getPacketSize() {
		return message.length();
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
}