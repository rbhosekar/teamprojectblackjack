import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.time.Year;

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

    public BlackJackGame(int numberOfPlayers) {

        // initialize the players array
        players = new Player[numberOfPlayers];


        // create buttons
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
            betAmount *= 2;
            players[0].doubleDown(deck.dealCard());
            updateHandDisplay();
            dealerTurn();
        });


        // create text areas for player's and dealer's hands
        playerHandArea = new JTextArea(10, 15);
        dealerHandArea = new JTextArea(10, 15);
        playerHandArea.setForeground(Color.YELLOW);
        dealerHandArea.setForeground(Color.YELLOW);
        Font font1 = new Font("Arial", Font.PLAIN, 20);
        playerHandArea.setFont(font1);
        dealerHandArea.setFont(font1);
        playerHandArea.setEditable(false);
        dealerHandArea.setEditable(false);

        // create text areas for player's and dealer's hand values
        playerValueArea = new JTextArea(10, 5);
        dealerValueArea = new JTextArea(10, 5);
        playerValueArea.setForeground(Color.YELLOW);
        dealerValueArea.setForeground(Color.YELLOW);
        Font font2 = new Font("Arial", Font.PLAIN, 17);
        playerValueArea.setFont(font2);
        dealerValueArea.setFont(font2);
        playerValueArea.setEditable(false);
        dealerValueArea.setEditable(false);

        // create text areas for player's balance
        playerBalanceArea = new JTextArea(1, 5);
        playerBalanceArea.setForeground(Color.YELLOW);
        playerBalanceArea.setFont(font2);
        playerBalanceArea.setEditable(false);



        // create layout for the game GUI
        setLayout(new BorderLayout());
        JPanel playerPanel = new JPanel(new BorderLayout());
        playerPanel.add(playerHandArea, BorderLayout.CENTER);
        playerPanel.add(playerValueArea, BorderLayout.EAST);

        JPanel dealerPanel = new JPanel(new BorderLayout());
        dealerPanel.add(dealerHandArea, BorderLayout.CENTER);
        dealerPanel.add(dealerValueArea, BorderLayout.EAST);

        // create bottom panel (buttons and player's balance)
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

        // deal initial cards to players and dealer
        dealInitialCards();
    }


    private void dealInitialCards() {
        betAmount = 100;
        deck = new Deck();
        deck.shuffle();
        dealer = new Dealer();
        dealer.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());

        // deal two cards to each player
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player("player", 1000);
            players[i].addCardToHand(deck.dealCard());
            players[i].addCardToHand(deck.dealCard());
        }

        // update GUI to display initial hands
        updateHandDisplay();
    }

    private void newRound() {
        betAmount = 100;
        deck = new Deck();
        deck.shuffle();
        dealer.clearHand();
        dealer.addCardToHand(deck.dealCard());
        dealer.addCardToHand(deck.dealCard());
        for (int i = 0; i < players.length; i++) {
            players[i].clearHand();
            players[i].addCardToHand(deck.dealCard());
            players[i].addCardToHand(deck.dealCard());
        }
        updateHandDisplay();
    }

    private void updateHandDisplay() {
        // update text for player and dealer
        playerHandArea.setText("Player's Hand:\n" + players[0].getHand());
        dealerHandArea.setText("Dealer's Hand:\n" + dealer.getHand());
        playerValueArea.setText("Value: " + players[0].getScore());
        dealerValueArea.setText("Value: " + dealer.getScore());
        playerBalanceArea.setText("Player's Balance: " + players[0].getBalance());
    }

    private void dealerTurn() {
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dealer.getScore() < 17) {
                    dealer.addCardToHand(deck.dealCard());
                    updateHandDisplay();
                } else {
                    ((Timer) e.getSource()).stop();
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
            players[0].collectWinnings(-betAmount);
        } else if (dealerScore > 21) {
            JOptionPane.showMessageDialog(this, "Player wins! Dealer busts.", "Winner", JOptionPane.INFORMATION_MESSAGE);
            players[0].collectWinnings(betAmount);
        } else if (playerScore > dealerScore) {
            JOptionPane.showMessageDialog(this, "Player wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
            players[0].collectWinnings(betAmount);
        } else if (playerScore < dealerScore) {
            JOptionPane.showMessageDialog(this, "Dealer wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
            players[0].collectWinnings(-betAmount);
        } else {
            JOptionPane.showMessageDialog(this, "Draw!", "Winner", JOptionPane.INFORMATION_MESSAGE);
        }
        newRound();
    }


}

