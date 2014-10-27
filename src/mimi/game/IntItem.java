package mimi.game;

import java.util.Random;

public class IntItem extends GuessableItem{
	
	private int item;
	
	public IntItem() {
	}
	public IntItem(int item) {
		this.item=item;
		
	}
	
	public int getItem() {
		return item;
	}

	@Override
	public GuessableItem generate() {
		Random random = new Random();
		int answer = random.nextInt(10);
		
		return new IntItem(answer);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + item;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntItem other = (IntItem) obj;
		if (item != other.item)
			return false;
		return true;
	}
	
	

}
