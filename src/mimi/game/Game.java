package mimi.game;

import java.util.Scanner;

public class Game {
	
	Scanner scanner;
	
	public String readConsoleLine(){
		if(scanner==null){
		 scanner = new Scanner(System.in);
		}
		 String lqlq= scanner.nextLine();
//		 scanner.close();
		 return lqlq;
	}
	
	Guessable generateGuessable(){
		return new Guessable();
	}

	public static void main(String[] args) {
		
		boolean finish;
		Game myGame = new Game();
		Guessable g = myGame.generateGuessable();
		String s;
		
		do{
			//reading from console
			s =myGame.readConsoleLine();
			System.out.println(s);
			Result r =g.guess(s);
			System.out.println(r.getBulls() + " bulls "+ r.getCows() + " cows");
			finish = (r.getBulls()==4);
		}while(!finish);
		
		System.out.println("Congratulations!!!");
		
		myGame.scanner.close();
	}

}
