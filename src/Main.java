
public class Main {

	public static void main(String[] args) {
		new Thread(() -> MusicPlayer.getInstance().playMusic("music/snake.wav")).start();
        javax.swing.SwingUtilities.invokeLater(() -> {
            new boardScreen();
        });
    }

}
