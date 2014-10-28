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
	private static final String HOST = "94.236.211.27";
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws UnknownHostException,
			IOException {
		Socket client = new Socket(HOST, PORT);
		
		DataInputStream inputStream = new DataInputStream(
				client.getInputStream());
		
		DataOutputStream outputStream = new DataOutputStream(
				client.getOutputStream());
		
		while (true) {
			try {
				outputStream.flush();
				
				String message = inputStream.readUTF();
				String command = "";
				if (message.length() > 5) {
					command = message.substring(0, 5);
				}
				if (command.equals("FINP:")) {
					System.out.println(message.substring(5));
					String s = input.nextLine();
					
					outputStream.writeUTF("FINP:" + s);
					outputStream.flush();
				} else if (command.equals("WINP:")) {
					System.out.println(message.substring(5));
					String s = input.nextLine();
					
					outputStream.writeUTF("GINP:" + s);
					outputStream.flush();
				} else if (command.equals("RESU:")) {
					System.out.println(message.substring(5));
				} else if (command.equals("FINA:")) {
					System.out.println(message.substring(5));
					break;
				} else {
					System.out.println("Server: " + message);
				}			
				
			} catch (EOFException eo) {

			}
		}
	}
}