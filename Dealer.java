import java.util.List;

public class Dealer {
    private Hand hand;

    public Dealer() {
        this.hand = new Hand();
    }

    public void addCardToHand(Card card) {
        hand.addCard(card);
    }

    public Hand getHand() {
        // this method will hide the dealer's first card
        Hand handWithoutFirst = new Hand();
        List<Card> allCards = hand.getCards();

        // add all the cards except the first one to the new hand object
        for (int i = 1; i < allCards.size(); i++) {
            handWithoutFirst.addCard(allCards.get(i));
        }
        return handWithoutFirst;
    }
    
    public boolean hasSoftSeventeen() {
        return hand.calculateScore() == 17 && hand.containsAce() && hand.isSoft();
    }

    public Hand revealHand() {
        return hand;
    }

    public void clearHand() {
        hand.getCards().clear();
    }

    public int getScore() {
        return hand.calculateScore();
    }

    public void displayPartialHand() {
        System.out.println("Dealer's hand:");
        System.out.println("First card: [Hidden]");
        System.out.println("Second card: " + hand.getCards().get(1));
    }

    public void displayFullHand() {
        System.out.println("Dealer's hand:");
        System.out.println(hand);
    }

}
