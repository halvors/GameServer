package org.halvors.Game.Server.network;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;
import org.halvors.Game.Server.entity.Player;
import org.halvors.Game.Server.network.packet.IPacket;
import org.halvors.Game.Server.network.packet.Packet;
import org.halvors.Game.Server.network.packet.PacketDisconnect;

public class NetworkManager {
	private final GameServer server;
	
	private final ReaderThread readerThread;
	private final WriterThread writerThread;
	private final Queue<IPacket> packetQueue = new LinkedList<IPacket>();
    
	private Socket socket;
	private DataInputStream input;
    private DataOutputStream output;
    private ServerHandler serverHandler;
    private Player player;
	
	public NetworkManager(GameServer server, Socket socket) throws IOException {
		this.server = server;
		this.socket = socket;
		
		try {
			socket.setSoTimeout(30000);
			socket.setTrafficClass(24); // TODO: Check this?
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		this.input = new DataInputStream(socket.getInputStream());
		this.output = new DataOutputStream(socket.getOutputStream());
//		this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream(), 5120));
//		this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		
		this.readerThread = new ReaderThread(server, "Reader thread", this);
        this.writerThread = new WriterThread(server, "Writer thread", this);
        readerThread.start();
        writerThread.start();
	}
	
	/**
	 * Sends a single packet.
	 * 
	 * @param packet
	 */
	public void sendPacket(IPacket packet) {
        if (packet != null) {
        	packetQueue.add(packet);
        	
        	server.log(Level.INFO, "Packet with id: " + packet.getId() + " queued.");
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
	
	public void disconnect(String reason) {
		// Send the leave message.
		server.broadcast(player.getName() + " left the game.");
		
		// Tell the client to do the disconnect.
		sendPacket(new PacketDisconnect(reason));
		
		try {
			shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() throws IOException {
		wakeThreads();
		
		readerThread.stop();
		writerThread.stop();
		
    	// Close socket.
		socket.close();
        this.socket = null;
        
        // Close input stream.
        input.close();
        this.input = null;
        
        // Close input stream.
		output.close();
        this.output = null;
    }
	
	public void wakeThreads() {
		readerThread.interrupt();
		writerThread.interrupt();
	}
	
	public GameServer getServer() {
		return server;
	}
	
	public ReaderThread getReaderThread() {
		return readerThread;
	}
	
	public WriterThread getWriterThread() {
		return writerThread;
	}
	
	public Queue<IPacket> getPacketQueue() {
		return packetQueue;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public DataInputStream getDataInputStream() {
		return input;
	}
	
	public DataOutputStream getDataOutputStream() {
		return output;
	}
	
	public ServerHandler getServerHandler() {
		return serverHandler;
	}
	
	public void setServerHandler(ServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
}