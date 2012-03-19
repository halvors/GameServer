package org.halvors.Game.Server.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface IPacket {
	public void readData(DataInputStream input) throws IOException;
	
	public void writeData(DataOutputStream output) throws IOException;
}
