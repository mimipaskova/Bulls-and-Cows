package mimi.game;

public class Result {
	private int cows;
	private int bulls;
	
	public Result(int cows, int bulls) {
		this.cows = cows;
		this.bulls=bulls;
		
	}
	
	public int getCows() {
		return cows;
	}
	
	public int getBulls() {
		return bulls;
	}
	
	@Override
	public String toString() {
		return "bulls " + this.bulls + " cows " + this.cows;
	}

}
