package main.java.org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketLogin extends Packet {
	public String username;
	public String version;
	
	public PacketLogin(String username, String version) {
		this.username = username;
		this.version = version;
	}
	
	@Override
	public void readPacketData(DataInputStream input) throws IOException {
		username = input.readUTF();
		version = input.readUTF();
	}

	@Override
	public void writePacketData(DataOutputStream output) throws IOException {
		output.writeUTF(username);
		output.writeUTF(version);
	}
	
	public int getPacketSize() {
		return username.length() + version.length();
	}
}