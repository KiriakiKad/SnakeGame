import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class boardScreen extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private RoundedButton startButton;
    private RoundedButton helpButton;
    private RoundedButton quitButton; // New Quit Button
    private JLabel info;

    public boardScreen() {
        setUndecorated(true); // Remove window decorations
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize to full screen

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0, 102, 0)); // Very dark green background

        info = new JLabel("Welcome to the Snake Game!");
        info.setFont(new Font("Arial", Font.BOLD, 36)); // Larger font
        info.setForeground(Color.WHITE);
        info.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton = new RoundedButton("Start Game", new Color(255, 255, 153)); // Light yellow button
        helpButton = new RoundedButton("Help", new Color(255, 255, 153)); // Light yellow button
        quitButton = new RoundedButton("Quit", new Color(255, 102, 102)); // Light red button

        panel.add(Box.createVerticalGlue());
        panel.add(info);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(startButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(helpButton);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(quitButton); // Add Quit Button
        panel.add(Box.createVerticalGlue());

        add(panel);

        setTitle("Board Screen");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Key listener to handle exit (e.g., Escape key)
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0); // Exit the application
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Hide the current frame
                new startGameScreen(); // Show the start game screen
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Hide the current frame
                new helpScreen(boardScreen.this); // Show the help screen
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
    }

}


