import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class helpScreen extends JFrame {

    private JPanel panel;
    private RoundedButton backButton; // Use RoundedButton
    private JLabel info;
    private JFrame parentFrame;

    public helpScreen(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        setUndecorated(true); // Remove window decorations
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize to full screen

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 98, 0)); // Very dark green background

        // Help information with HTML formatting
        info = new JLabel();
        info.setFont(new Font("Arial", Font.PLAIN, 24)); // Larger font
        info.setForeground(Color.WHITE);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        info.setText("<html><h1>Game Rules:</h1>" +
                "<ul>" +
                "<li><b>Objective:</b> Control the snake to eat food and grow longer.</li><br>" +
                "<li><b>Movement:</b> Use the arrow keys to change direction. The snake moves continuously.</li><br>" +
                "<li><b>Food:</b> Eating food makes the snake grow and increases your score.</li><br>" +
                "<li><b>Collision Detection:</b> The game ends if the snake hits the walls or itself.</li><br>" +
                "<li><b>Score:</b> Points are earned by consuming food items.</li><br>" +
                "<li><b>Game Over:</b> When the snake collides with itself or the walls, the game ends.</li><br>" +
                "</ul>" +
                "</html>");

        // Back button
        backButton = new RoundedButton("Back", new Color(255, 255, 153)); // Light yellow color

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide this help screen and show the main board screen
                setVisible(false);
                parentFrame.setVisible(true);
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 100))); // Larger spacing
        panel.add(info);
        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Larger spacing
        panel.add(backButton);
        panel.add(Box.createVerticalGlue());

        add(panel);

        setTitle("Help Screen");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Disposes when closing
        setVisible(true);

        // Key listener to handle exit (e.g., Escape key)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false); // Hide this frame
                    parentFrame.setVisible(true); // Show the parent frame
                }
            }
        });
    }
}
