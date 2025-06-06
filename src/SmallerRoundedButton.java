import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

class SmallerRoundedButton extends RoundedButton {

    public SmallerRoundedButton(String label, Color bgColor) {
        super(label, bgColor);
        setFont(new Font("Arial", Font.PLAIN, 12)); // Smaller font
        setBorder(new EmptyBorder(5, 10, 5, 10)); // Smaller padding
        setPreferredSize(new Dimension(80, 30)); // Smaller button size
    }
}
