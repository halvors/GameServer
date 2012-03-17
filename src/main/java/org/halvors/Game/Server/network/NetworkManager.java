package org.halvors.Game.Server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketDisconnect;
import org.halvors.Game.Server.network.packet.PacketLogin;

public class NetworkManager {
	private final GameServer server;
	private final Queue<Packet> packetQueue = new LinkedList<Packet>();
	private final ReaderThread readerThread;
	private final WriterThread writerThread;
	
	private ServerHandler serverHandler;
	private Socket socket;
	private DataInputStream input;
    private DataOutputStream output;
    
    private Player player;
	
	public NetworkManager(GameServer server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		socket.setSoTimeout(30000);
//      socket.setTrafficClass(24); // TODO: Check this?
		this.input = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
		this.readerThread = new ReaderThread("Reader thread", this);
        this.writerThread = new WriterThread("Writer thread", this);
        readerThread.start();
        writerThread.start();
	}
	
	/**
	 * Sends a single packet.
	 * 
	 * @param packet
	 */
	public void sendPacket(Packet packet) {
        if (packet != null) {
        	packetQueue.add(packet);
        	
        	server.log(Level.INFO, "Packet with id: " + packet.getPacketId() + " queued.");
        }
    }
	
	/**
	 * Broadcast a packet, also sends it to all connected peers.
	 * 
	 * @param packet
	 */
	public void broadcastPacket(Packet packet) {
		for (Player p : server.getPlayers()) {
			p.getNetworkManager().sendPacket(packet);
		}
	}
	
	public void login(PacketLogin packet) {
		String name = packet.getUsername();
		String version = packet.getVersion();
		
		if (name != null && version != null) {
			if (version != server.getVersion()) {
				disconnect("You are using an old version: " + version);
			}
			
			// Create Player and ServerHandler.
			player = new Player(server, name);
			serverHandler = new ServerHandler(server, this, player);
			
			// Send reply to the client.
			sendPacket(new PacketLogin(name, version));
			
			// Inform server console.
			server.log(Level.INFO, name + " logged in with id: " + player.getId());
			
			// Send login message.
			String message = name + " joined the game.";
			server.broadcast(message);
		}
	}
	
	public void disconnect(String reason) {
		// Send the leave message.
		server.broadcast(player.getName() + " left the game.");
		
		// Tell the client to do the disconnect.
		sendPacket(new PacketDisconnect(reason));
		
		try {
			close();
			wakeThreads();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() throws IOException {
    	// Close socket.
        socket.close();
        socket = null;
        
        // Close input stream.
        input.close();
        input = null;
        
        // Close input stream.
        output.close();
        output = null;
    }
	
	public void wakeThreads() {
		readerThread.interrupt();
		writerThread.interrupt();
	}

	public Socket getSocket() {
		return socket;
	}

	public GameServer getServer() {
		return server;
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

	public DataInputStream getDataInputStream() {
		return input;
	}
	
	public DataOutputStream getDataOutputStream() {
		return output;
	}
	
	public ReaderThread getReaderThread() {
		return readerThread;
	}
	
	public WriterThread getWriterThread() {
		return writerThread;
	}
}