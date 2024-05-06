import javax.swing.*;
import java.awt.*;

public class BlackJackGUI extends JPanel {
    private JPanel parentPanel;
    private CardLayout cardLayout;

    public BlackJackGUI(JPanel parentPanel, CardLayout cardLayout) {
        this.parentPanel = parentPanel;
        this.cardLayout = cardLayout;

        // initialize title screen
        BlackJackTitle titleScreen = new BlackJackTitle(this);
        parentPanel.add(titleScreen, "titleScreen");

        // switch to title screen
        cardLayout.show(parentPanel, "titleScreen");
    }

    public void switchToGame(int numberOfPlayers) {
        BlackJackGame gamePanel = new BlackJackGame(numberOfPlayers);
        parentPanel.add(gamePanel, "gamePanel");
        cardLayout.show(parentPanel, "gamePanel");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Blackjack Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel mainPanel = new JPanel(new CardLayout());
            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
            BlackJackGUI gameGUI = new BlackJackGUI(mainPanel, cardLayout);

            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}


