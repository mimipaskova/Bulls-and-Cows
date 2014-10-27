package mimi.game.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	
	private ServerSocket serverSocket;
	private RoomHandler room;
	private boolean needsSecondPlayer = false;
	
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}
	
	public static void main(String[] args) {
		Server server;
		try {
			server = new Server(5001);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Socket clientSocket = serverSocket.accept();
				if(needsSecondPlayer) {
					room.addSecondPlayer(clientSocket);
					needsSecondPlayer = false;
					continue;
				} else {
					room = new RoomHandler(clientSocket);
					needsSecondPlayer = true;
				}
				
				Thread client = new Thread(room);
				client.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
