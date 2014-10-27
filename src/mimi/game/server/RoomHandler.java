package mimi.game.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RoomHandler implements Runnable {

	private Socket clientFirst;
	private Socket clientSecond;
	
	private boolean waitingForPlayer = true;
	
	private String wordToGuess;

	public RoomHandler(Socket first) {
		this.clientFirst = first;
	}

	public void addSecondPlayer(Socket second) {
		this.clientSecond = second;
	}

	@Override
	public void run() {
		DataOutputStream outputStreamFirst, outputStreamSecond = null;
		DataInputStream inputStreamFirst, inputStreamSecond = null;
		try {
			outputStreamFirst = new DataOutputStream(
					clientFirst.getOutputStream());
			inputStreamFirst = new DataInputStream(clientFirst.getInputStream());
			
			outputStreamFirst.writeUTF("Wait for other player to connect.");
			
			while (true) {
				
				if (this.clientSecond == null) {
					continue;
				}
				if(waitingForPlayer) {
					outputStreamSecond = new DataOutputStream(clientSecond.getOutputStream());
					inputStreamSecond = new DataInputStream(clientSecond.getInputStream());
					
					outputStreamFirst.writeUTF("Ok, you are here let's start!");
					outputStreamSecond.writeUTF("Ok, you are here let's start!");
					
					outputStreamFirst.writeUTF("FINP:Your turn is to place a number. Do it!");
					waitingForPlayer = false;
				}
				
				String message = inputStreamFirst.readUTF();
				String command = message.substring(0, 5);
				
				if(command.equals("FINP:")) {
					if(this.wordToGuess == null) {
						this.wordToGuess = message.substring(5);
						
						outputStreamSecond.writeUTF("WINP:Guess the number");
					}
				}
				
				message = inputStreamSecond.readUTF();
				command = message.substring(0, 5);
				
				if(command.equals("GINP:")) {
					if(this.wordToGuess == null) {
						continue;
					}
					
					String g = message.substring(5);
					outputStreamFirst.writeUTF(g);
					
					if(g.equals(this.wordToGuess)) {
						String finalMessage = "The word is guessed!";
						
						outputStreamFirst.writeUTF("FINA:" + finalMessage);
						outputStreamSecond.writeUTF("FINA:" + finalMessage);
					} else {
						outputStreamSecond.writeUTF("WINP:Guess the number");
					}
				}
			}
		} catch (IOException e) {
			try {
				this.clientFirst.close();
				this.clientSecond.close();
				
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		

	}

}
