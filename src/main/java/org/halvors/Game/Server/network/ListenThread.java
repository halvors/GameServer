package org.halvors.Game.Server.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import org.halvors.Game.Server.GameServer;

public class ListenThread extends Thread {
	private final GameServer server;
	private final ServerSocket serverSocket;
	private final AcceptThread acceptThread;
	
	public ListenThread(String name, GameServer server, InetAddress address, int port) throws IOException {
		super(name);
		this.server = server;
		this.serverSocket = new ServerSocket(port, 0, address);
		
		// Accept connections and logins here before we register a new NetworkManager.
		this.acceptThread = new AcceptThread("Accept thread", server, this);
		acceptThread.start();
	}
	
	@Override
	public void run() {
		Socket socket = null;
		
		while (!serverSocket.isClosed()) {
			try {
				socket = serverSocket.accept();
				
				if (socket != null && socket.isBound()) {
					// Add the socket to the pending connections list.
	    			acceptThread.addToPendigConnections(socket);
	    			server.log(Level.INFO, "Connection accepted from: " + socket.getRemoteSocketAddress().toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
}

//synchronized (delay) {
//	InetAddress address = socket.getInetAddress();
//	
//	// Limit connection attempts.
//    if (delay.containsKey(address) && System.currentTimeMillis() - ((Long) delay.get(address)).longValue() < 5000L) {
//        delay.put(address, Long.valueOf(System.currentTimeMillis()));
//        socket.close();
//    } else {
//    	// Add the socket to the pending connections list.
//		acceptThread.addToPendigConnections(socket);
//		server.log(Level.INFO, "Connection accepted from: " + socket.getRemoteSocketAddress().toString());
//		
//		// Put connection in HashMap.
//    	delay.put(address, Long.valueOf(System.currentTimeMillis()));
//    }
//}