import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	private List<Card> cards;
	
	public Deck() {
		this.cards=new ArrayList<>();
		initializeDeck();
	}
	
	
	private void initializeDeck() {
		String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
		String[] faces = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
		
		for (String suit : suits) {
			for (int i=0; i<faces.length; i++) {
				cards.add(new Card(suit, i +2 , faces[i]));
			}
		}
	}
	
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Card dealCard() {
		return cards.isEmpty() ? null : cards.remove(0);
	}
}