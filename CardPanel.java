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
            return; // Prevent further processing if image isn't found
        }
        ImageIcon icon = new ImageIcon(imageUrl);

        // Calculate the new dimensions as one-third of the original dimensions
        int newWidth = icon.getIconWidth() / 3;
        int newHeight = icon.getIconHeight() / 3;

        // Scale the image to one-third of its original size
        Image image = icon.getImage(); // Transform it
        Image newimg = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH); // Scale it smoothly
        icon = new ImageIcon(newimg);  // Transform it back

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
