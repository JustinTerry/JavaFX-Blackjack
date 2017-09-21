import java.util.ArrayList;

public class Hand{
	int score;
	
	ArrayList<Card> cards = new ArrayList<Card>();
	
	public void addCard(Card addThis) {
		cards.add(addThis);
	}
	
	public int getScore(){
		score = 0;
		for(int i = 0; i < cards.size(); i++) {
			score = score + cards.get(i).getValue();
		}
		if(score > 21) {
			for(int i = 0; i < cards.size(); i++) {
				if(cards.get(i).getValue()==11) {
					cards.get(i).value = 1;
					getScore();
					break;
				}
			}
		}
		
		return score;
	}
}
