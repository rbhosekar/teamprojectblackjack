public class Card {
	private String suit;
	private int value;
	private String face;
	
	public Card(String suit, int value, String face) {
		this.suit=suit;
		this.value=value;
		this.face=face;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getFace() {
		return face;
	}
	
	@Override
	public String toString() {
		return face+ " of "+suit;
	}
}