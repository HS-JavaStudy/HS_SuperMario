package SuperMario;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class IntroScreen extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;
	
	// 초기 이미지
	private Image introBackground = new ImageIcon("src/images/introBackground.jpg").getImage();;
	private Image titleImage = new ImageIcon("src/images/title.png").getImage();
	
	// 사이즈 설정 이미지
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
		
		// Section1: 이미지 사이즈 조절
		resizeIntroBackground = introBackground.getScaledInstance(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
		resizeTitleImage = titleImage.getScaledInstance(MarioGame.SCREEN_WIDTH/2, MarioGame.SCREEN_HEIGHT/4, Image.SCALE_SMOOTH);
	
		// Section2: 게임 배경음악 설정
		Music backgroundMusic = new Music("backgroundMusic.mp3",true); // 배경음악 객체 생성
		backgroundMusic.start(); // 배경음악 시작
	}
	
	public void paint(Graphics g) {  //Frame에 포함된 메소드 재정의 (public void update 함수에서 디폴트로 실행 됨)
		
		screenImage = createImage(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage,0,0,null);
		
	}
	
	public void screenDraw(Graphics g) {
		
		g.drawImage(resizeIntroBackground,0,0,null);
		g.drawImage(resizeTitleImage,210,175,null);
		
		
		font = new Font("Monospaced",Font.BOLD,22); // 폰트 설정
		g.setFont(font); 
		g.setColor(Color.WHITE); // 글씨 색 설정

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
