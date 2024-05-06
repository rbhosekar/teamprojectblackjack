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
            if (value == 11) {  // Assuming ace is initially 11
                aceCount++;
            }
            totalScore += value;
        }

        while (totalScore > 21 && aceCount > 0) {
            totalScore -= 10;  // Convert an Ace from 11 to 1
            aceCount--;
        }
        return totalScore;
    }

    public boolean containsAce() {
        return cards.stream().anyMatch(card -> card.getValue() == 11);
    }

    public boolean isSoft() {
        int score = calculateScore();
        return containsAce() && score + 10 <= 21;
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
