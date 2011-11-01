package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PacketLogin extends Packet {
	public String username;
	public String version;
	
	public PacketLogin() {
		
	}
	
	public PacketLogin(String username, String version) {
		this.username = username;
		this.version = version;
	}
	
	@Override
	public void readData(DataInputStream input) throws IOException {
		username = input.readUTF();
		version = input.readUTF();
	}

	@Override
	public void writeData(DataOutputStream output) throws IOException {
		output.writeUTF(username);
		output.writeUTF(version);
	}
	
	@Override
	public void run() {
		
	}
	
	public int getPacketSize() {
		return username.length() + version.length();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
}