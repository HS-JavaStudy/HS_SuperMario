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
		super("������"); // ���α׷� ��
		setLayout(null); // ���̾ƿ� �Ŵ��� ��� ����
		setResizable(false); // â ũ�� ����
		setSize(1000, 1000);// â ũ�� ����
		setBackground(Color.WHITE); // â ��� ���� ����
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���α׷� ���� ����

		addKeyListener(this); // frame�� KeyListener ����

		x = (int) getWidth() / 2; // frame�� �ʺ� ���
		y = (int) getHeight() / 2;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_NUMPAD6 || // ����Ű NUMPAD
				key == KeyEvent.VK_KP_RIGHT) {
			sel = (sel == 1) ? 2 : 1; // ���׿�����
			x = (x < getWidth()) ? x + 10 : -image.getWidth(this);
		} else {
			int key1 = e.getKeyCode();
			if (key1 == KeyEvent.VK_LEFT || key1 == KeyEvent.VK_NUMPAD4 || // ����Ű NUMPAD
					key1 == KeyEvent.VK_KP_LEFT) {
				sel = (sel == 1) ? 3 : 1; // ���׿�����
				x = (x > 0) ? x - 10 : getWidth() + image.getWidth(this);
			}
			int key2 = e.getKeyCode();
			if (key2 == KeyEvent.VK_UP || key2 == KeyEvent.VK_NUMPAD8 || // ����Ű NUMPAD
					key2 == KeyEvent.VK_KP_UP) {
				sel = (sel == 1) ? 4 : 1; // ���׿�����
				y = (y > 0) ? y - 10 : getHeight() + image.getHeight(this);

			}
			int key3 = e.getKeyCode();
			if (key3 == KeyEvent.VK_DOWN || key3 == KeyEvent.VK_NUMPAD2 || // ����Ű NUMPAD
					key3 == KeyEvent.VK_KP_DOWN) {
				sel = (sel == 1) ? 4 : 1; // ���׿�����
				y = (y < getHeight()) ? y + 10 : image.getWidth(this);

			}
		}
		repaint(); // �ѹ� �����ϸ� �ٽ� ���� �̹����� ����
	}

	@Override
	public void paint(Graphics g) {
		switch (sel) {
		case 1:
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallMario.png");
			// ������ �̹���1 - ������ ������ �ٽ� �����ؾ� ��
			break;
		case 2:
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallMario.png");
			// ������ �̹���2 - ������ ������ �ٽ� �����ؾ� ��
			break;

		case 3:
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallMario.png");
			// ������ �̹���3 - ������ ������ �ٽ� �����ؾ� ��
			break;

		case 4:
			image = Toolkit.getDefaultToolkit().getImage("src/images/smallMario.png");
			// ������ �̹���4 - ������ ������ �ٽ� �����ؾ� ��
			break;
		}

		g.clearRect(0, 0, getWidth(), getHeight()); // ȭ���� �ܻ� Ŭ�����ϰ� ���α׷� ����

		g.drawImage(image, x - 17, y - 25, x + 17, y + 25, 0, 0, 4096, 4096, this); // img�� (0,0)���� image(4096,4096)�κ���
																					// ��ǥ�� ���� ����
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent arg0) {
	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent arg0) {
	}

	

}
