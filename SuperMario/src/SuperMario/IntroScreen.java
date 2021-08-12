package SuperMario;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class IntroScreen extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;
	
	// �ʱ� �̹���
	private Image introBackground = new ImageIcon("src/images/introBackground.jpg").getImage();;
	private Image titleImage = new ImageIcon("src/images/title.png").getImage();
	
	// ������ ���� �̹���
	private Image resizeIntroBackground;
	private Image resizeTitleImage;
	
	private Font font;

	public IntroScreen() {
		
		// Basic
		setTitle("Mario Game");
		setSize(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		addKeyListener(new keyListener());
		
		// Section1: �̹��� ������ ����
		resizeIntroBackground = introBackground.getScaledInstance(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
		resizeTitleImage = titleImage.getScaledInstance(MarioGame.SCREEN_WIDTH/2, MarioGame.SCREEN_HEIGHT/4, Image.SCALE_SMOOTH);
	
		// Section2: ���� ������� ����
		Music backgroundMusic = new Music("backgroundMusic.mp3",true); // ������� ��ü ����
		backgroundMusic.start(); // ������� ����
	}
	
	public void paint(Graphics g) {  //Frame�� ���Ե� �޼ҵ� ������ (public void update �Լ����� ����Ʈ�� ���� ��)
		
		screenImage = createImage(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage,0,0,null);
		
	}
	
	public void screenDraw(Graphics g) {
		
		g.drawImage(resizeIntroBackground,0,0,null);
		g.drawImage(resizeTitleImage,210,175,null);
		
		
		font = new Font("Monospaced",Font.BOLD,22); // ��Ʈ ����
		g.setFont(font); 
		g.setColor(Color.WHITE); // �۾� �� ����

		g.drawString("< Press Enter to Start Game >",242 ,382);
		
		this.repaint();	
	}
	
	class keyListener extends KeyAdapter {
		
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
		
			if(key == KeyEvent.VK_ENTER) {
				
				setVisible(false);
				
				new MarioGame();
				
			}
		}
	}
}