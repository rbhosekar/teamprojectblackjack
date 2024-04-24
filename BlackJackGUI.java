import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackJackGUI extends JFrame {
    private JComboBox<Integer> playersComboBox;

    public BlackJackGUI() {
        setTitle("Blackjack - Number of Players");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);

        // create the title label
        JLabel titleLabel = new JLabel("BlackJack");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        playersComboBox = new JComboBox<>();
        playersComboBox.addItem(2);
        playersComboBox.addItem(3);
        playersComboBox.addItem(4);
        playersComboBox.addItem(5);
        playersComboBox.addItem(6);
        playersComboBox.addItem(7);
        playersComboBox.setSelectedItem(2); // default 2 players

        // create panel to hold components
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        panel.add(titleLabel);
        panel.add(playersComboBox);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        BlackJackGUI gui = new BlackJackGUI();
    }
}
