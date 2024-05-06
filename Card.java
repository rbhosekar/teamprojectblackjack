import java.net.URL;


public class Card {
    private String suit;
    private int value;
    private String face;
    private String imageUrl;

    public Card(String suit, int value, String face) {
        this.suit = suit;
        this.value = value;
        this.face = face;
        assignImage();
    }
    
    public int getValue() {
    	return value;
    }

    private void assignImage() {
        String shortSuit = suit.substring(0, 1);
        String shortFace = null;
        switch (face) {
            case "10":
                shortFace = "10";
                break;
            case "Jack":
                shortFace = "J";
                break;
            case "Queen":
                shortFace = "Q";
                break;
            case "King":
                shortFace = "K";
                break;
            case "Ace":
                shortFace = "A";
                break;
            default:
                shortFace = face; // Assuming number cards are named "2", "3", etc.
                break;
        }
        this.imageUrl = "/cards/" + shortFace + "-" + shortSuit + ".png";
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return face + " of " + suit;
    }
}

