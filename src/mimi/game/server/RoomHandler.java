package mimi.game.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import mimi.game.Guessable;
import mimi.game.Result;

public class RoomHandler implements Runnable {
	private volatile Socket clientFirst;
	private volatile Socket clientSecond;
	
	private boolean waitingForPlayer = true;
	private boolean waitingForFirstInput = true;
	
	private int stepToGuess = 0;
	private Guessable g;
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
				if (waitingForPlayer) {
					outputStreamSecond = new DataOutputStream(
							clientSecond.getOutputStream());
					inputStreamSecond = new DataInputStream(
							clientSecond.getInputStream());
					outputStreamFirst.writeUTF("Ok, you are here let's start!");
					outputStreamSecond
							.writeUTF("Ok, you are here let's start!");
					outputStreamFirst
							.writeUTF("FINP:Your turn is to place a number. Do it!");
					waitingForPlayer = false;
				}
				
				String message, command;
				
				if(waitingForFirstInput) {
					message = inputStreamFirst.readUTF();
					command = message.substring(0, 5);
					if (command.equals("FINP:")) {
						if (this.wordToGuess == null) {
							this.wordToGuess = message.substring(5);
							g = new Guessable(this.wordToGuess);
							outputStreamSecond.writeUTF("WINP:Guess the number");
							System.out.println("The number is " + this.wordToGuess);
						}
					}
					
					waitingForFirstInput = false;					
				} else {
				
					message = inputStreamSecond.readUTF();
					command = message.substring(0, 5);
					
					if (command.equals("GINP:")) {
						if (this.wordToGuess == null) {
							continue;
						}
						stepToGuess++;
						
						String guessTry = message.substring(5);
						outputStreamFirst.writeUTF(guessTry);
						System.out.println(guessTry);
						Result result = g.guess(guessTry);
						if (result.getBulls() == 4) {
							String finalMessage = "The word is guessed!\n It took it " + stepToGuess + " steps to guess the number.";
							outputStreamFirst.writeUTF("FINA:" + finalMessage);
							outputStreamSecond.writeUTF("FINA:" + finalMessage);
						} else {
							outputStreamFirst.writeUTF(result.toString());
							outputStreamSecond.writeUTF(result.toString());
							outputStreamSecond.writeUTF("WINP:Guess the number");
						}
					}
				}
			}
		} catch (IOException e) {
			try {
				this.clientFirst.close();
				this.clientSecond.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}