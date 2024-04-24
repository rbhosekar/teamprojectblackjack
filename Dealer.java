public class Dealer {
    private Hand hand;
    
    public Dealer() {
        this.hand = new Hand();
    }
    
    public void addCardToHand(Card card) {
        hand.addCard(card);
    }
    
    public Hand getHand() {
        return hand;
    }
    
    public void clearHand() {
        hand.getCards().clear();
    }
    
    public boolean wantsToHit() {
        return hand.calculateScore() <= 16;
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
