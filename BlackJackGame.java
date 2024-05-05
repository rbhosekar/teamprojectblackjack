import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;


public class BlackJackGame extends JPanel {
    private Player[] players;
    private Dealer dealer;
    private JTextArea playerHandArea;
    private JTextArea dealerHandArea;
    private JTextArea playerValueArea;
    private JTextArea dealerValueArea;

    private JTextArea playerBalanceArea;
    private JButton hitButton;
    private JButton standButton;
    private JButton doubleDownButton;
    private Deck deck;
    private int betAmount;
    private int currentPlayerIndex = 0;
    private boolean dealerFirstCardHidden = true;

    public BlackJackGame(int numberOfPlayers) {
        players = new Player[numberOfPlayers];

        hitButton = new JButton("Hit");
        hitButton.addActionListener(e -> {
            if (players[currentPlayerIndex].getScore() < 21) {
                players[currentPlayerIndex].hit(deck.dealCard());
                doubleDownButton.setEnabled(false);
                updateHandDisplay();
            }

            if (players[currentPlayerIndex].getScore() > 21) {
                JOptionPane.showMessageDialog(this, "Bust!", "Bust", JOptionPane.INFORMATION_MESSAGE);
                updateHandDisplay();
                nextPlayerTurn();
            }
        });

        standButton = new JButton("Stand");
        standButton.addActionListener(e -> {
            nextPlayerTurn();
        });

        doubleDownButton = new JButton("Double Down");
        doubleDownButton.addActionListener(e -> {
            betAmount *= 2;
            players[currentPlayerIndex].doubleDown(deck.dealCard());
            updateHandDisplay();
            nextPlayerTurn();
        });

        playerHandArea = new JTextArea(10, 15);
        dealerHandArea = new JTextArea(10, 15);
        playerHandArea.setForeground(Color.YELLOW);
        dealerHandArea.setForeground(Color.YELLOW);
        Font font1 = new Font("Arial", Font.PLAIN, 20);
        playerHandArea.setFont(font1);
        dealerHandArea.setFont(font1);
        playerHandArea.setEditable(false);
        dealerHandArea.setEditable(false);

        playerValueArea = new JTextArea(10, 5);
        dealerValueArea = new JTextArea(10, 5);
        playerValueArea.setForeground(Color.YELLOW);
        dealerValueArea.setForeground(Color.YELLOW);
        Font font2 = new Font("Arial", Font.PLAIN, 17);
        playerValueArea.setFont(font2);
        dealerValueArea.setFont(font2);
        playerValueArea.setEditable(false);
        dealerValueArea.setEditable(false);

        playerBalanceArea = new JTextArea(1, 5);
        playerBalanceArea.setForeground(Color.YELLOW);
        playerBalanceArea.setFont(font2);
        playerBalanceArea.setEditable(false);

        setLayout(new BorderLayout());
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.add(playerHandArea, BorderLayout.CENTER);
        playerPanel.add(playerValueArea, BorderLayout.EAST);

        JPanel dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.add(dealerHandArea, BorderLayout.CENTER);
        dealerPanel.add(dealerValueArea, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.add(playerBalanceArea);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(doubleDownButton);

        add(playerPanel, BorderLayout.WEST);
        add(dealerPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBackground(new Color(90, 50, 30));
        playerHandArea.setBackground(new Color(10, 50, 30));
        playerValueArea.setBackground(new Color(10, 50, 30));
        playerBalanceArea.setBackground(new Color(10, 50, 30));
        dealerHandArea.setBackground(new Color(10, 50, 30));
        dealerValueArea.setBackground(new Color(10, 50, 30));

        dealInitialCards();
    }

    private void dealInitialCards() {
        betAmount = 100;
        deck = new Deck();
        deck.shuffle();
        dealer = new Dealer();
        dealer.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());

        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("player", 1000);
            players[i].addCardToHand(deck.dealCard());
            players[i].addCardToHand(deck.dealCard());
        }

        updateHandDisplay();
        currentPlayerIndex = 0;
    }

    private void nextPlayerTurn() {
        doubleDownButton.setEnabled(true);
        if (currentPlayerIndex == players.length - 1) {
            dealerTurn();
        } else {
            currentPlayerIndex++;
            updateHandDisplay();
        }
    }

    private void updateHandDisplay() {
        playerHandArea.setText(String.format("Player %d's Hand:\n%s", currentPlayerIndex + 1, players[currentPlayerIndex].getHand()));
        dealerHandArea.setText("Dealer's Hand:\n" + "[Hidden]\n" + dealer.getHand());
        dealerValueArea.setText("Value: ?");
        playerValueArea.setText("Value: " + players[currentPlayerIndex].getScore());
        playerBalanceArea.setText(String.format("Player %d's Balance:\n%s", currentPlayerIndex + 1, players[currentPlayerIndex].getBalance()));
    }

    private void revealDealer() {
        dealerHandArea.setText("Dealer's Hand:\n" + dealer.revealHand());
        dealerValueArea.setText("Value: " + dealer.getScore());
    }

    private void dealerTurn() {
        revealDealer();
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dealer.getScore() < 17) {
                    dealer.addCardToHand(deck.dealCard());
                    revealDealer();
                } else {
                    ((Timer) e.getSource()).stop();
                    dealerFirstCardHidden = false; // Reveal dealer's first card
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
