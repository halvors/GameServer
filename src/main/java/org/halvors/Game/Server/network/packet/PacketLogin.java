package main.java.org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import main.java.org.halvors.Game.Server.GameServer;

public class PacketLogin extends Packet {
	public String username;
	public String version = GameServer.getInstance().getVersion();
	
	public PacketLogin(String username) {
		this.username = username;
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