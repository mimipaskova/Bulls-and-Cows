package mimi.game;

public class Guessable {

	private GuessableItem[] genItem;

	public Guessable() {
		genItem = generate();
	}

	public Guessable(String number) {
		genItem = generateNumber(number);
	}

	private GuessableItem[] generate() {
		GuessableItem[] lqlq = new GuessableItem[4];

		System.out.print("The number is");
		for (int i = 0; i < 4; i++) {
			IntItem item = new IntItem();
			lqlq[i] = item.generate();
		}

		System.out.println();
		return lqlq;

	}

	public GuessableItem[] generateNumber(String data) {
		String[] parts = data.split("");
		GuessableItem[] items = new GuessableItem[4];

		for (int n = 1; n < parts.length; n++) {
			items[n - 1] = new IntItem(Integer.parseInt(parts[n]));
		}
		return items;
	}

	public Result guess(String data) {

		int cows = 0;
		int bulls = 0;

		String[] parts = data.split("");
		GuessableItem[] items = new GuessableItem[4];

		for (int n = 1; n < parts.length; n++) {
			items[n - 1] = new IntItem(Integer.parseInt(parts[n]));
		}

		for (int i = 0; i < genItem.length; i++) {
			if (genItem[i].equals(items[i])) {
				bulls++;
			}
		}

		for (int i = 0; i < genItem.length; i++) {
			for (int j = 0; j < items.length; j++) {
				if (i != j) {
					if (genItem[i].equals(items[j])) {
						cows++;
					}
				}
			}
		}

		return new Result(cows, bulls);
	}

}
