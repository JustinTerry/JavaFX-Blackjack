
/* This class creates an ArrayList of cards in the "hand" of the player or dealer.
 * There is also a method inside that allows for scoring the hand easily. 
 */

import java.util.ArrayList;

public class Hand {
	protected int score;
	protected ArrayList<Card> cards = new ArrayList<Card>(2);

	// Adds a card to the hand
	public void addCard(Card addThis) {
		cards.add(addThis);
	}
	
	// Scores the hand and returns the score
	public int getScore() {
		score = 0;
		for (int i = 0; i < cards.size(); i++) {
			score = score + cards.get(i).getValue();
		}
		// Changes the value of an ace if the hand's score is over 21
		if (score > 21) {
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).getValue() == 11) {
					cards.get(i).value = 1;
					getScore();
					break;
				}
			}
		}

		return score;
	}
}
