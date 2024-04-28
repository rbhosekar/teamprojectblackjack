import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class BlackJackGame extends JPanel {
    private Player[] players;
    private Dealer dealer;
    private JTextArea playerHandArea;
    private JTextArea dealerHandArea;
    private JTextArea playerValueArea; // New area to display player's hand value
    private JTextArea dealerValueArea; // New area to display dealer's hand value
    private JButton hitButton;
    private JButton standButton;
    private JButton doubleDownButton;
    private Deck deck; // Deck object for dealing cards

    public BlackJackGame(int numberOfPlayers) {

        // Initialize the players array
        players = new Player[numberOfPlayers];


        // Create buttons
        hitButton = new JButton("Hit");
        hitButton.addActionListener(e -> {
            if (players[0].getScore() < 21) {
                players[0].hit(deck.dealCard());
                updateHandDisplay();
            }

            if (players[0].getScore() > 21) {
                JOptionPane.showMessageDialog(this, "Bust!", "Bust", JOptionPane.INFORMATION_MESSAGE);
                updateHandDisplay();
                dealerTurn();
            }
        });

        standButton = new JButton("Stand");
        standButton.addActionListener(e -> {
            dealerTurn();
        });

        doubleDownButton = new JButton("Double Down");
        doubleDownButton.addActionListener(e -> {
            players[0].doubleDown(deck.dealCard());
            updateHandDisplay();
            dealerTurn();
        });


        // Create text areas to display player's and dealer's hands
        playerHandArea = new JTextArea(10, 15);
        dealerHandArea = new JTextArea(10, 15);
        playerHandArea.setForeground(Color.YELLOW);
        dealerHandArea.setForeground(Color.YELLOW);
        playerHandArea.setEditable(false);
        dealerHandArea.setEditable(false);

        // Create text areas to display player's and dealer's hand values
        playerValueArea = new JTextArea(10, 5);
        dealerValueArea = new JTextArea(10, 5);
        playerValueArea.setForeground(Color.YELLOW);
        dealerValueArea.setForeground(Color.YELLOW);
        playerValueArea.setEditable(false);
        dealerValueArea.setEditable(false);


        // Create layout for the game GUI
        setLayout(new BorderLayout());
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.add(playerHandArea, BorderLayout.CENTER);
        playerPanel.add(playerValueArea, BorderLayout.EAST);

        JPanel dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.add(dealerHandArea, BorderLayout.CENTER);
        dealerPanel.add(dealerValueArea, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(doubleDownButton);

        add(playerPanel, BorderLayout.WEST);
        add(dealerPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBackground(new Color(10, 50, 30));
        playerHandArea.setBackground(new Color(10, 50, 30));
        playerValueArea.setBackground(new Color(10, 50, 30));
        dealerHandArea.setBackground(new Color(10, 50, 30));
        dealerValueArea.setBackground(new Color(10, 50, 30));


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

        // Update text areas to display hand values
        playerValueArea.setText("Value: " + players[0].getScore());
        dealerValueArea.setText("Value: " + dealer.getScore());
    }

    private void dealerTurn() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dealer.getScore() < 17) {
                    dealer.addCardToHand(deck.dealCard());
                    updateHandDisplay();
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer when the condition is met
                    determineWinner();
                }
            }
        });
        timer.start();
    }

    private void determineWinner() {
        int playerScore = players[0].getScore();
        int dealerScore = dealer.getScore();

        if (playerScore > 21) {
            JOptionPane.showMessageDialog(this, "Dealer wins! Player busts.", "Winner", JOptionPane.INFORMATION_MESSAGE);
        } else if (dealerScore > 21) {
            JOptionPane.showMessageDialog(this, "Player wins! Dealer busts.", "Winner", JOptionPane.INFORMATION_MESSAGE);
        } else if (playerScore > dealerScore) {
            JOptionPane.showMessageDialog(this, "Player wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
        } else if (playerScore < dealerScore) {
            JOptionPane.showMessageDialog(this, "Dealer wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Draw!", "Winner", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}

