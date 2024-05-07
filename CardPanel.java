import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CardPanel extends JPanel {
    private JLabel cardLabel;

    public CardPanel(Card card) {
        setLayout(new BorderLayout());
        updateCard(card);
    }

    public void updateCard(Card card) {
        URL imageUrl = getClass().getResource(card.getImageUrl());
        if (imageUrl == null) {
            System.err.println("Failed to load image. Check if the path is correct: " + card.getImageUrl());
            return; 
        }
        ImageIcon icon = new ImageIcon(imageUrl);


        int newWidth = icon.getIconWidth() / 3;
        int newHeight = icon.getIconHeight() / 3;


        Image image = icon.getImage(); 
        Image newimg = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);  

        if (cardLabel == null) {
            cardLabel = new JLabel(icon);
            add(cardLabel, BorderLayout.CENTER);
        } else {
            cardLabel.setIcon(icon);
        }
        revalidate();
        repaint();
    }
}
