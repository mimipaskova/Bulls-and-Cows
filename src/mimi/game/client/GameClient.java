package mimi.game.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {

	private static final int PORT = 5001;
	private static final String HOST = "localhost";
	
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket client = new Socket(HOST, PORT);

		DataInputStream inputStream = new DataInputStream(
				client.getInputStream());
		
		
		while (true) {
			try {
				DataOutputStream outputStream = new DataOutputStream(client.getOutputStream());
				
				String message = inputStream.readUTF();
				String command = "";
				if(message.length() > 5) {
					command = message.substring(0, 5);
				}
				
				
				if(command.equals("FINP:")) {
					System.out.println(message.substring(5));
					String s = input.nextLine();
					
					outputStream.writeUTF("FINP:" + s);
				} else if(command.equals("WINP:")) {
					System.out.println(message.substring(5));
					String s = input.nextLine();
					
					outputStream.writeUTF("GINP:" + s);
				} else if(command.equals("RESU:")) {
					System.out.println(message.substring(5));
				} else if(command.equals("FINA:")) {
					System.out.println(message.substring(5));
					break;
				} else {
					System.out.println("Server: " + message);
				}
				
				outputStream.flush();
				
			} catch (EOFException eo) {
				System.out.println("greshka");
			}

		}

	}
}
