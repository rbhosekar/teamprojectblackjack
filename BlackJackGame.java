import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class BlackJackGame extends JPanel {
    private Player[] players;
    private Dealer dealer;
    private List<List<CardPanel>> playerCardPanels;
    private List<CardPanel> dealerCardPanels;
    private JButton hitButton, standButton, doubleDownButton;
    private JLabel dealerLabel, playerLabel;
    private Deck deck;
    private int betAmount;
    private int currentPlayerIndex = 0;
    private int dealerTotalValue;
    private boolean dealerFirstCardHidden = true;

    public BlackJackGame(int numberOfPlayers) {
        players = new Player[numberOfPlayers];
        playerCardPanels = new ArrayList<>();
        dealerCardPanels = new ArrayList<>();

        initUI();
        dealInitialCards();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(10, 50, 30));

        // Dealer panel setup
        JPanel dealerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dealerPanel.setBackground(new Color(10, 50, 30));
        dealerLabel = new JLabel("Dealer: ");
        dealerLabel.setForeground(Color.YELLOW);
        dealerPanel.add(dealerLabel);
        for (int i = 0; i < 2; i++) {
            CardPanel panel = new CardPanel(new Card("Spades", 10, "King"));
            panel.setBackground(new Color(10, 50, 30));
            dealerCardPanels.add(panel);
            dealerPanel.add(panel);
        }
        add(dealerPanel, BorderLayout.NORTH);

        // Player panel setup
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(new Color(10, 50, 30));
        JScrollPane scrollPane = new JScrollPane(playerPanel);
        scrollPane.setBackground(new Color(10, 50, 30));
        for (int i = 0; i < players.length; i++) {
            JPanel pPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pPanel.setBackground(new Color(10, 50, 30));
            playerLabel = new JLabel("Player " + (i + 1) + ": ");
            playerLabel.setForeground(Color.YELLOW);
            pPanel.add(playerLabel);
            List<CardPanel> cardPanels = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                CardPanel cPanel = new CardPanel(new Card("Hearts", 10, "Ace"));
                cPanel.setBackground(new Color(10, 50, 30));
                cardPanels.add(cPanel);
                pPanel.add(cPanel);
            }
            playerCardPanels.add(cardPanels);
            playerPanel.add(pPanel);
        }
        add(scrollPane, BorderLayout.CENTER);

        // Button panel setup
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        doubleDownButton = new JButton("Double Down");
        hitButton.addActionListener(this::handleHit);
        standButton.addActionListener(this::handleStand);
        doubleDownButton.addActionListener(this::handleDoubleDown);

        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(doubleDownButton);
        buttonPanel.setBackground(new Color(10, 50, 30));
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void handleHit(ActionEvent e) {
        // Handling hit button action
        if (players[currentPlayerIndex].getScore() < 21) {
            players[currentPlayerIndex].hit(deck.dealCard());
            doubleDownButton.setEnabled(false);
            updateHandDisplay();
            checkForBust();
        }
    }

    private void handleStand(ActionEvent e) {
        // Proceed to the next player or dealer turn
        nextPlayerTurn();
    }

    private void handleDoubleDown(ActionEvent e) {
        // Player doubles the bet and takes one final card
        betAmount *= 2;
        players[currentPlayerIndex].doubleDown(deck.dealCard());
        updateHandDisplay();
        nextPlayerTurn();
    }

    private void dealInitialCards() {
        betAmount = 100;
        deck = new Deck();
        deck.shuffle();
        dealer = new Dealer();
        dealer.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("Player " + (i + 1), 1000);
            players[i].addCardToHand(deck.dealCard());
            players[i].addCardToHand(deck.dealCard());
        }
        updateHandDisplay();
        currentPlayerIndex = 0;
    }

    private void nextPlayerTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
        if (currentPlayerIndex == 0) {
            dealerTurn();
        } else {
            updateHandDisplay();
        }
        JOptionPane.showMessageDialog(this, "Player " + (currentPlayerIndex + 1) + "'s Turn", "Next Player", JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkForBust() {
        if (players[currentPlayerIndex].getScore() > 21) {
            JOptionPane.showMessageDialog(this, "Bust!", "Player " + (currentPlayerIndex + 1) + " Busts", JOptionPane.INFORMATION_MESSAGE);
            nextPlayerTurn();
        }
    }






    private void updateHandDisplay() {
        // Clear existing cards from UI and update with current cards
        for (int i = 0; i < playerCardPanels.size(); i++) {
            JPanel playerPanel = (JPanel) playerCardPanels.get(i).get(0).getParent();
            playerPanel.removeAll(); // Clear the panel for new cards
            playerPanel.add(new JLabel("Player " + (i + 1) + ": ", SwingConstants.LEFT)).setForeground(Color.YELLOW); // Re-add the player label

            JLabel balanceLabel = new JLabel("Balance: $" + players[i].getBalance());
            balanceLabel.setForeground(Color.YELLOW); // Set foreground color to yellow
            playerPanel.add(balanceLabel);

            List<CardPanel> panels = playerCardPanels.get(i);
            List<Card> cards = players[i].getHand().getCards();
            panels.clear(); // Clear the old card panels list

            int totalValue = 0; // track total value
            for (Card card : cards) {
                totalValue += card.getValue(); // calculate total value
                CardPanel newPanel = new CardPanel(card);
                panels.add(newPanel);
                playerPanel.add(newPanel);
            }

            // Display total value
            JLabel totalLabel = new JLabel("Total: " + totalValue);
            totalLabel.setForeground(Color.YELLOW); // Set foreground color to yellow
            playerPanel.add(totalLabel);
        }

        // Similar updates needed for the dealer
        JPanel dealerPanel = (JPanel) dealerCardPanels.get(0).getParent();
        dealerPanel.removeAll(); // Clear the panel for new cards
        dealerPanel.add(dealerLabel); // Re-add the dealer label

        dealerCardPanels.clear(); // Clear old dealer card panels
        List<Card> dealerCards = dealer.getHand().getCards();
        int dealerTotalValue = 0; // track dealer's total value
        for (Card card : dealerCards) {
            dealerTotalValue += card.getValue(); // calculate total dealer value
            CardPanel newPanel = new CardPanel(card);
            dealerCardPanels.add(newPanel);
            dealerPanel.add(newPanel);
        }

        // Display total value of dealer's cards
        JLabel dealerTotalLabel = new JLabel("Total: " + dealerTotalValue);
        dealerTotalLabel.setForeground(Color.YELLOW); // Set foreground color to yellow
        dealerPanel.add(dealerTotalLabel);

        revalidate();
        repaint();
    }


    private void revealDealer() {
        JPanel dealerPanel = (JPanel) dealerCardPanels.get(0).getParent();
        dealerPanel.removeAll();  // Clear the panel for new cards
        dealerLabel = new JLabel("Dealer: ");
        dealerPanel.add(dealerLabel);  // Re-add the dealer label

        dealerCardPanels.clear();  // Clear old dealer card panels
        List<Card> dealerCards = dealer.getHand().getCards();
        for (Card card : dealerCards) {
            CardPanel newPanel = new CardPanel(card);
            dealerCardPanels.add(newPanel);
            dealerPanel.add(newPanel);
        }

        dealerPanel.revalidate();
        dealerPanel.repaint();
    }


    private void dealerTurn() {
    	dealerTotalValue = 0;
        revealDealer();  // Initially reveal the dealer's starting hand
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Continue hitting if the dealer's score is less than 17 or it's a soft 17
                if (dealer.getScore() < 17 || (dealer.getScore() == 17 && dealer.hasSoftSeventeen())) {
                    dealer.addCardToHand(deck.dealCard());
                    revealDealer();  // Update display after each hit
                } else {
                    ((Timer) e.getSource()).stop();
                    dealerFirstCardHidden = false; // No longer relevant
                    revealDealer();  // Ensure final state of dealer's hand is shown
                    determineWinner();
                }
            }
        });
        timer.start();
    }




    private void determineWinner() {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            int playerScore = player.getScore();
            int dealerScore = dealer.getScore();
            String playerName = "Player " + (i + 1);

            if (playerScore > 21) {
                JOptionPane.showMessageDialog(this, "Dealer wins! " + playerName + " busts.", "Results: " + playerName, JOptionPane.INFORMATION_MESSAGE);
                player.collectWinnings(-betAmount);
            } else if (dealerScore > 21) {
                JOptionPane.showMessageDialog(this, playerName + " wins! Dealer busts.", "Results: " + playerName, JOptionPane.INFORMATION_MESSAGE);
                player.collectWinnings(betAmount);
            } else if (playerScore > dealerScore) {
                JOptionPane.showMessageDialog(this, playerName + " wins!", "Results: " + playerName, JOptionPane.INFORMATION_MESSAGE);
                player.collectWinnings(betAmount);
            } else if (playerScore < dealerScore) {
                JOptionPane.showMessageDialog(this, "Dealer wins! " + playerName, "Results: " + playerName, JOptionPane.INFORMATION_MESSAGE);
                player.collectWinnings(-betAmount);
            } else {
                JOptionPane.showMessageDialog(this, "Draw!", "Results: " + playerName, JOptionPane.INFORMATION_MESSAGE);
            }
        }
        newRound();
        updateHandDisplay();
    }

    private void newRound() {
        dealerFirstCardHidden = true; // Hide dealer's first card again
        betAmount = 100;
        deck = new Deck();
        deck.shuffle();
        dealer.clearHand();
        dealer.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());
        for (Player player : players) {
            player.clearHand();
            player.addCardToHand(deck.dealCard());
            player.addCardToHand(deck.dealCard());
        }
    }
}
