package SuperMario;
import java.awt.*;
import javax.swing.*;

public class IntroScreen extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image introBackground;
	private Image resizeIntroBackground;
	
	private Image titleImage;
	private Image resizeTitleImage;
	
	public IntroScreen() {
		
		setTitle("Mario Game");
		setSize(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		introBackground = new ImageIcon("src/images/introBackground.jpg").getImage();
		resizeIntroBackground = introBackground.getScaledInstance(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
	
		titleImage = new ImageIcon("src/images/title.png").getImage();
		resizeTitleImage = titleImage.getScaledInstance(MarioGame.SCREEN_WIDTH/2, MarioGame.SCREEN_HEIGHT/4, Image.SCALE_SMOOTH);
	
		Music backgroundMusic = new Music("backgroundMusic.mp3",true); // πË∞Ê¿Ωæ« ∞¥√º ª˝º∫
		backgroundMusic.start(); // πË∞Ê¿Ωæ« Ω√¿€
	}
	
	public void paint(Graphics g) {
		
		screenImage = createImage(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage,0,0,null);
		
	}
	
	public void screenDraw(Graphics g) {
		
		g.drawImage(resizeIntroBackground,0,0,null);
		g.drawImage(resizeTitleImage,210,175,null);
		this.repaint();
		
		
	}
	
}
