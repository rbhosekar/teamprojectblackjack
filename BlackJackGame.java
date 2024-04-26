import javax.swing.*;
import java.awt.*;

public class BlackJackGame extends JPanel {
    private int numberOfPlayers;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private Player[] players;
    private Dealer dealer;
    private JTextArea playerHandArea;
    private JTextArea dealerHandArea;
    private JButton hitButton;
    private JButton standButton;
    private JButton doubleDownButton;
    private Deck deck; // Deck object for dealing cards

    public BlackJackGame(JPanel parentPanel, CardLayout cardLayout, int numberOfPlayers) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;

        // Initialize the players array
        players = new Player[numberOfPlayers];

        // Create buttons
        hitButton = new JButton("Hit");
        hitButton.addActionListener(e -> {
            players[0].hit(deck.dealCard());
            System.out.println("HIT");
            updateHandDisplay();
        });

        standButton = new JButton("Stand");
        standButton.addActionListener(e -> {
            System.out.println("STAND");
        });

        doubleDownButton = new JButton("Double Down");
        doubleDownButton.addActionListener(e -> {
            System.out.println("DOUBLE DOWN");
        });

        // Create text areas to display player's and dealer's hands
        playerHandArea = new JTextArea(10, 30);
        playerHandArea.setEditable(false);
        playerHandArea.setForeground(Color.YELLOW);
        dealerHandArea = new JTextArea(10, 30);
        dealerHandArea.setEditable(false);
        dealerHandArea.setForeground(Color.YELLOW);
        playerHandArea.setBackground(new Color(0, 50, 0));
        dealerHandArea.setBackground(new Color(0, 50, 0));

        // Create layout for the game GUI
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(doubleDownButton);
        add(playerHandArea, BorderLayout.CENTER);
        add(dealerHandArea, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBackground(new Color(0, 50, 0));

        // Initialize the deck
        deck = new Deck();
        deck.shuffle();

        // Initialize the dealer
        dealer = new Dealer();

        // Deal initial cards to players and dealer
        dealInitialCards();
    }

    private void dealInitialCards() {
        // Deal two cards to each player and the dealer
        for (int i = 0; i < players.length; i++) {
            System.out.println("HERE");
            players[i] = new Player("player", 1000);
            players[i].addCardToHand(deck.dealCard());
            players[i].addCardToHand(deck.dealCard());
        }
        dealer.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());

        // Update GUI to display initial hands
        updateHandDisplay();
    }

    private void updateHandDisplay() {
        // Update text areas to display hands
        playerHandArea.setText("Player's Hand:\n" + players[0].getHand());
        dealerHandArea.setText("Dealer's Hand:\n" + dealer.getHand());
    }
}



