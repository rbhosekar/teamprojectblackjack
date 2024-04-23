import java.util.ArrayList;
import java.util.List;

public class Hand {
	private List<Card> cards;
	
	public Hand() {
		this.cards = new ArrayList<>();
	}
	
	public void addCard(Card card) {
		if (card != null) {
			cards.add(card);
		}
	}
	
	
	public int calculateScore() {
		int totalScore = 0;
		int aceCount = 0;
		
		for (Card card : cards) {
			int value = card.getValue();
			if (value==11) {
				aceCount++;
			}
			totalScore+=value;
		}
		
		while (totalScore> 21 && aceCount>0) {
			totalScore -= 10;
			aceCount--;
		}
		return totalScore;
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Card card : cards) {
			sb.append(card.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
}	
	
	
	
