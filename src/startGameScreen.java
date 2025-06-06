import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class startGameScreen extends JFrame implements KeyListener {
    private JPanel mainPanel;
    private JPanel gridPanel;
    private JPanel controlPanel;
    private JButton[][] buttons;
    private final int GRID_SIZE = 15; // Grid size
    private final int CELL_SIZE = 30; // Smaller cell size

    private ArrayList<Point> snake; // Snake body
    private Point food; // Food position
    private Direction currentDirection; // Current direction of movement
    private boolean running; // Game running state
    private boolean moveAllowed; // Movement control state
    private Timer gameTimer; // Timer to handle movement updates

    private JLabel messageLabel; // Label to show the start message

    private enum Direction {UP, DOWN, LEFT, RIGHT}

    public startGameScreen() {
        setTitle("Snake Game");
        setUndecorated(true); // Remove window decorations
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize to full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(80,220,80));

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        gridPanel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        gridPanel.setBackground(Color.GREEN); // Set background 

        buttons = new JButton[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                buttons[i][j].setBackground(new Color(102,255,102)); // Set initial cell background to green
                buttons[i][j].setBorder(BorderFactory.createLineBorder(new Color(0,22,0)));
                gridPanel.add(buttons[i][j]);
            }
        }

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());


        SmallerRoundedButton quitButton = new SmallerRoundedButton("Quit", new Color(255, 102, 102)); // Light red button
        quitButton.setPreferredSize(new Dimension(100, 30)); // Set button size
        quitButton.addActionListener(e -> quitGame());
        controlPanel.add(quitButton);
        controlPanel.setBackground(new Color(80,220,80));

        // Add the gridPanel and controlPanel to the mainPanel
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Create and add the message label
        messageLabel = new JLabel("Press ENTER to Start", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, 50));
        mainPanel.add(messageLabel, BorderLayout.NORTH);

        add(mainPanel);

        initializeGame();

        // Add key listener to the frame
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow(); // Request focus for the frame

        setVisible(true);
    }

    private void initializeGame() {
        snake = new ArrayList<>();
        snake.add(new Point(GRID_SIZE / 2, GRID_SIZE / 2)); // Start position
        currentDirection = Direction.RIGHT;
        placeFood();
        running = false; // Initially stopped
        moveAllowed = false; // Initially movement is not allowed

        // Timer for game loop with slower speed
        gameTimer = new Timer(300, e -> { // Increase delay to slow down the game
            if (running && moveAllowed) {
                moveSnake();
                checkCollisions();
                updateUI();
            }
        });
    }

    private void startGame() {
        if (!running) {
            running = true; // Start the game
            moveAllowed = true; // Allow movement
            gameTimer.start(); // Start the timer
            messageLabel.setVisible(false); // Hide the start message
            System.out.println("Game Started"); // Debug message
        }
    }

    private void quitGame() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit Game",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void moveSnake() {
        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (currentDirection) {
            case UP:
                newHead.translate(0, -1);
                break;
            case DOWN:
                newHead.translate(0, 1);
                break;
            case LEFT:
                newHead.translate(-1, 0);
                break;
            case RIGHT:
                newHead.translate(1, 0);
                break;
        }

        // Check if snake has eaten the food
        if (newHead.equals(food)) {
        	EatMusic player = EatMusic.getInstance2();
            player.playMusic("music/eat.wav");
            snake.add(0, food); // Add food to the snake
            placeFood(); // Place new food
        } else {
            // Move snake
            snake.add(0, newHead);
            snake.remove(snake.size() - 1);
        }
    }

    private void checkCollisions() {
        Point head = snake.get(0);

        // Check collision with walls
        if (head.x < 0 || head.x >= GRID_SIZE || head.y < 0 || head.y >= GRID_SIZE) {
            gameOver("Game Over! You hit the wall.");
        }

        // Check collision with itself
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameOver("Game Over! You ran into yourself.");
            }
        }
    }

    private void placeFood() {
        Random rand = new Random();
        do {
            food = new Point(rand.nextInt(GRID_SIZE), rand.nextInt(GRID_SIZE));
        } while (snake.contains(food)); // Ensure food is not placed on the snake
    }

    private void updateUI() {
        // Clear the board
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setBackground(new Color(102,255,102)); // Set cell background to green
                buttons[i][j].setIcon(null);
            }
        }

        // Draw the snake
        for (int i = 0; i < snake.size(); i++) {
            Point p = snake.get(i);
            if (i == 0) {
                // Head of the snake
            	ImageIcon imageIcon = new ImageIcon("images/head.jpg"); // load the image to a imageIcon
            	Image image = imageIcon.getImage(); // transform it 
            	Image newimg = image.getScaledInstance(220, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            	imageIcon = new ImageIcon(newimg);  // transform it back
                buttons[p.x][p.y].setIcon(imageIcon); // Very dark green for head
            } else {
            	ImageIcon imageIcon2 = new ImageIcon("images/skin.jpg"); // load the image to a imageIcon
            	Image image2 = imageIcon2.getImage(); // transform it 
            	Image newimg2 = image2.getScaledInstance(180, 150,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
            	imageIcon2 = new ImageIcon(newimg2);  // transform it back
                buttons[p.x][p.y].setIcon(imageIcon2); // Very dark green for head
            }
        }

        // Draw the food
        buttons[food.x][food.y].setIcon(new ImageIcon("images/apple1.jpg"));
       
    }

    private void gameOver(String message) {
        gameTimer.stop();
        int option = JOptionPane.showOptionDialog(this, message + " Would you like to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (option == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0); // Exit the application if the user chooses "No"
        }
    }

    private void resetGame() {
        initializeGame(); // Reinitialize game state
        messageLabel.setVisible(true); // Show the start message
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Start game when ENTER key is pressed
            startGame();
        }
        if (!running) {
            return; // Do nothing if the game is not running
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W: // Move up
                if (currentDirection != Direction.RIGHT) {
                    currentDirection = Direction.LEFT;
                    System.out.println("Direction: UP"); // Debug message
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S: // Move down
                if (currentDirection != Direction.LEFT) {
                    currentDirection = Direction.RIGHT;
                    System.out.println("Direction: DOWN"); // Debug message
                }
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A: // Move left
                if (currentDirection != Direction.DOWN) {
                    currentDirection = Direction.UP;
                    System.out.println("Direction: LEFT"); // Debug message
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D: // Move right
                if (currentDirection != Direction.UP) {
                    currentDirection = Direction.DOWN;
                    System.out.println("Direction: RIGHT"); // Debug message
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}