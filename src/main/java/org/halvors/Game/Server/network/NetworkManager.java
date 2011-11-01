package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketDisconnect;

public class NetworkManager {
	private final GameServer server;
	private final LoginHandler loginHandler;
	private final Queue<Packet> packetQueue = new LinkedList<Packet>();
	private final ReaderThread readerThread;
	private final WriterThread writerThread;
	
	private ServerHandler serverHandler;
	private Socket socket;
	private DataInputStream input;
    private DataOutputStream output;
	private boolean isRunning = true;
	
	public NetworkManager(GameServer server, Socket socket, LoginHandler loginHandler, String name) throws IOException {
		this.server = server;
		this.socket = socket;
		socket.setSoTimeout(30000);
        socket.setTrafficClass(24); // TODO: Check this?
		this.loginHandler = loginHandler;
		this.input = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
		this.readerThread = new ReaderThread(name + ": Reader thread", this);
        this.writerThread = new WriterThread(name + ": Writer thread", this);
        readerThread.start();
        writerThread.start();
	}
	
	/**
	 * Simply send a single packet.
	 * 
	 * @param packet
	 */
	public void sendPacket(Packet packet) {
        if (packet != null) {
        	packetQueue.add(packet);
        }
    }
	
	public void disconnect(String reason) throws IOException {
		Player player = loginHandler.getPlayer();
		String message =  player.getName() + " left the game.";
		
		// Send leave message.
		server.broadcast(message);
		
		sendPacket(new PacketDisconnect(reason));
		close();
		wakeThreads();
	}
	
	public void wakeThreads() {
		readerThread.interrupt();
		writerThread.interrupt();
	}
	
	public void close() throws IOException {
        if (isRunning) {
        	setRunning(false);
        	
        	// Close socket.
            socket.close();
            
            // Close input stream.
            input.close();
            
            // Close input stream.
            output.close();
        }
    }

	public Socket getSocket() {
		return socket;
	}

	public GameServer getServer() {
		return server;
	}

	public LoginHandler getLoginHandler() {
		return loginHandler;
	}
	
	public ServerHandler getServerHandler() {
		return serverHandler;
	}
	
	public void setServerHandler(ServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}
	
	public Queue<Packet> getPacketQueue() {
		return packetQueue;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public DataInputStream getInput() {
		return input;
	}
	
	public DataOutputStream getOutput() {
		return output;
	}
	
	public ReaderThread getReaderThread() {
		return readerThread;
	}
	
	public WriterThread getWriterThread() {
		return writerThread;
	}
}