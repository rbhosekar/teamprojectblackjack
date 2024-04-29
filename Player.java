public class Player {
    private String name;
    private int balance;
    private int bet;
    private Hand hand;
    
    public Player(String name, int balance) {
        this.name = name;
        this.balance = balance;
        this.hand = new Hand();
    }
    
    public void placeBet(int bet) {
        if (bet <= balance) {
            this.bet = bet;
            balance -= bet;
        } else {
            System.out.println("Insufficient balance. Please place a valid bet.");
        }
    }

    public int getScore() {
        return hand.calculateScore();
    }
    
    public int getBet() {
        return bet;
    }
    
    public void addCardToHand(Card card) {
        hand.addCard(card);
    }
    
    public Hand getHand() {
        return hand;
    }
    
    public int getBalance() {
        return balance;
    }

    
    public void collectWinnings(int winnings) {
        balance += winnings;
    }
    
    public boolean isBusted() {
        return hand.calculateScore() > 21;
    }
    
    public void clearHand() {
        hand.getCards().clear();
    }
    
    // Methods to handle player actions
    
    public void hit(Card card) {
        addCardToHand(card);
        System.out.println(name + " hits.");
        displayHand(); // Implement this method or remove the call if not needed
    }
    public void displayHand() {
        System.out.println(name + "'s hand:");
        System.out.println(hand);
    }
    
    public void stand() {
    	System.out.println(name + " stands.");
    }
    
    public void doubleDown(Card card) {
        hit(card); // Add one more card to the hand
        // Double the bet
        bet *= 2;
        System.out.println(name + " doubles down.");
    }
}
