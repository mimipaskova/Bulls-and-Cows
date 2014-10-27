package mimi.game;

public class Guessable {
	
	private GuessableItem[] genItem;
	
	public Guessable() {		
		genItem = generate();
	}
	
	private GuessableItem[] generate(){
		GuessableItem[] lqlq = new GuessableItem[4];
		
		System.out.print("The number is");
		for (int i = 0; i < 4; i++) {
			IntItem item=new IntItem();
			lqlq[i]=item.generate();
		}
		
//		lqlq[0] = new IntItem(4);
//		lqlq[1] = new IntItem(0);
//		lqlq[2] = new IntItem(7);
//		lqlq[3] = new IntItem(6);
		
		
		for (int i = 0; i < 4; i++) {
//			System.out.print(lqlq[i].getItem()+ " ");
		}
		System.out.println();
		return lqlq;
		
	}
	
	public Result guess(String data){
		
		int cows=0;
		int bulls=0;

		String[] parts = data.split("");
//		System.out.println(parts.length+ " qqqq");
		GuessableItem[] items = new GuessableItem[4];
		
		for(int n = 1; n < parts.length; n++) {
		   items[n - 1] = new IntItem(Integer.parseInt(parts[n]));
		}
//		System.out.println(genItem.length+ " genitem "+ items.length + " items"+ parts.length+ " parts length");
		
		
		for (int i = 0; i < 4; i++) {
//			item[i]=new IntItem(Integer.parseInt(date)+ "");
			
		}
//		int input = Integer.parseInt(date);
		for (int i = 0; i < genItem.length; i++) {
			if(genItem[i].equals(items[i])) {
				bulls++;
			}
			
		}
		
		for (int i = 0; i < genItem.length; i++) {
			for (int j = 0; j < items.length; j++) {
				if(i!=j){
					if(genItem[i].equals(items[j])) {
						cows++;
					}
				}
			}
		}
		
		return new Result(cows, bulls);		
	}

}
