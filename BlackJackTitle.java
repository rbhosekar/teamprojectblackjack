import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackJackTitle extends JPanel {

    public BlackJackTitle(BlackJackGUI blackJackGUI, JPanel parentPanel, CardLayout cardLayout) {

        JLabel titleLabel = new JLabel("BlackJack");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.YELLOW);

        JComboBox<Integer> playersComboBox = new JComboBox<>();
        for (int i = 2; i <= 7; i++) {
            playersComboBox.addItem(i);
        }

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numberOfPlayers = (int) playersComboBox.getSelectedItem();
                blackJackGUI.switchToGame(numberOfPlayers);
            }
        });

        JLabel selectPlayersLabel = new JLabel("Select Number of Players:");
        selectPlayersLabel.setForeground(Color.YELLOW);

        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.add(titleLabel);
        centerPanel.add(selectPlayersLabel);
        centerPanel.add(playersComboBox);
        add(centerPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
        centerPanel.setBackground(new Color(0, 50, 0)); // set the background to dark green
    }
}


