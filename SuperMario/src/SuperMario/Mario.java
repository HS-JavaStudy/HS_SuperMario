package SuperMario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Mario extends JFrame implements KeyListener {

	Image image;
	int x = 100, y = 100, sel = 1;

	public Mario() {
		super("마리오"); // 프로그램 명
		setLayout(null); // 레이아웃 매니저 사용 안함
		setResizable(false); // 창 크기 고정
		setSize(816, 678);// 창 크기 지정
		setBackground(Color.WHITE); // 창 배경 색상 지정
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 닫힘 설정

		addKeyListener(this); // frame에 KeyListener 장착

		x = (int) getWidth() / 2; // frame의 너비 얻기
		y = (int) getHeight() / 2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_NUMPAD6 || // 숫자키 NUMPAD
				key == KeyEvent.VK_KP_RIGHT) {
			sel =  1; // 오른쪽
			x = (x < getWidth()) ? x + 10 : -image.getWidth(this);
		} else {
			int key1 = e.getKeyCode();
			if (key1 == KeyEvent.VK_LEFT || key1 == KeyEvent.VK_NUMPAD4 || // 숫자키 NUMPAD
					key1 == KeyEvent.VK_KP_LEFT) {
				//sel = (sel == 1) ? 3 : 1; // 삼항연산자
				sel =  2 ; //왼쪽
				x = (x > 0) ? x - 10 : getWidth() + image.getWidth(this);
			}
			int key2 = e.getKeyCode();
			if (key2 == KeyEvent.VK_UP || key2 == KeyEvent.VK_NUMPAD8 || // 숫자키 NUMPAD
					key2 == KeyEvent.VK_KP_UP) {
				sel = (sel == 1) ? 3 : 4; // 삼항연산자
				//sel = 3;
				y = (y > 0) ? y - 10 : getHeight() + image.getHeight(this);

			}
			int key3 = e.getKeyCode();
			if (key3 == KeyEvent.VK_DOWN || key3 == KeyEvent.VK_NUMPAD2 || // 숫자키 NUMPAD
					key3 == KeyEvent.VK_KP_DOWN) {
				sel = (sel == 1) ? 4 : 1; // 삼항연산자
				y = (y < getHeight()) ? y + 10 : image.getWidth(this);

			}
		}
		repaint(); // 한번 실행하면 다시 원래 이미지로 복귀
	}

	@Override
	public void paint(Graphics g) {
		switch (sel) {
		case 1: // 오른쪽
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallRightMario.jpg"); // 툴킷 클래스의 도움을 받아 이미지 클래스 생성
			// 서있는 오른쪽 마리오
			break;
		case 2:  //왼쪽
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallLeftMario.jpg");
			// 서있는 왼쪽 마리오
			break;

		case 3: //위 오른
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallJumpRightMario.jpg");
			// 점프하는 마리오
			break;

		case 4: //위 왼쪽
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallJumpLeftMario.jpg");
			// 고양이 이미지4 - 폴더는 개별로 다시 지정해야 됨
			break;
		}

		g.clearRect(0, 0, getWidth(), getHeight()); // 화면의 잔상 클리어하고 프로그램 시작

		g.drawImage(image, x - 17, y - 25, x + 17, y + 25, 0, 0, 1280, 1280, this); // img의 (0,0)에서 image(4096,4096)부분을
																					// 좌표에 따라 구현
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent arg0) {
	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent arg0) {
	}

	

}

