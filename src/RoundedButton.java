import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

class RoundedButton extends JButton {

    private Color backgroundColor;

    public RoundedButton(String label, Color bgColor) {
        super(label);
        this.backgroundColor = bgColor;
        setFont(new Font("Arial", Font.PLAIN, 24)); // Larger font
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setBorder(new EmptyBorder(15, 30, 15, 30)); // Larger padding
        setPreferredSize(new Dimension(300, 80)); // Larger button size
        setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment for the button
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(backgroundColor.darker());
        } else {
            g2.setColor(backgroundColor);
        }

        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40)); // Larger corners
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40)); // Larger corners
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 40, 40).contains(x, y); // Larger corners
    }
}