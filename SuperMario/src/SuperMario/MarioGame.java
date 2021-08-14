package SuperMario;

import java.awt.*;
import javax.swing.*;

import SuperMario.Mario.MarioJump;
//import SuperMario.Mario.OperationTread;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class MarioGame extends JFrame {

	// 초기 이미지
	private Image introBackground = new ImageIcon("src/images/introBackground.jpg").getImage();
	private Image titleImage = new ImageIcon("src/images/title.png").getImage();
	static public Image mapImage = new ImageIcon("src/images/map.png").getImage();
	// private Image resizeMap = mapImage.getScaledInstance(MarioGame.SCREEN_WIDTH
	// *10, mapImage.getHeight(rootPane), Image.SCALE_SMOOTH);
	// 사이즈 설정 이미지
	private Image resizeIntroBackground = introBackground.getScaledInstance(MarioGame.SCREEN_WIDTH,
			MarioGame.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
	private Image resizeTitleImage = titleImage.getScaledInstance(MarioGame.SCREEN_WIDTH / 2,
			MarioGame.SCREEN_HEIGHT / 4, Image.SCALE_SMOOTH);

	// Section2: 게임 배경음악 설정
	Music backgroundMusic = new Music("backgroundMusic.mp3", true); // 배경음악 객체 생성

	private Font font;

	private Image bufferImage; // 더블 버퍼링을 위한 전체 이미지를 담는 변수
	private Graphics screenGraphic;

	static boolean isMainScreen;
	static boolean isLoadingScreen;
	static boolean isGameScreen;

	static public int realX;
	public static final int SCREEN_WIDTH = 816;
	public static final int SCREEN_HEIGHT = 678;

	private Blocks blocks = new Blocks();
	public Mario mario = new Mario();

	public MarioGame() {
		// 게임을 출력할 창 지정
		init();
		realX = 30;
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setTitle("Super Mario!");
		setLocation(200, 0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		addKeyListener(new KeyListener()); // 마우스 이벤트 추가
		addMouseListener(new MouseAdapter() { // 마우스 이벤트
			@Override
			public void mouseClicked(MouseEvent e) { // 마우스 움직일때.
				System.out.println("스크린상 좌표 : " + e.getX() + " ," + e.getY());// x좌표,y좌표 출력
				System.out.println("marioX : " + mario.marioX + " realX : " + realX);
			}
		});
		// gameStart(); // 바로 게임 스타트
		backgroundMusic.start(); // 배경음악 시작
	}

	public void mgps() {

	}

	void init() {
		isMainScreen = true;
		isLoadingScreen = false;
		isGameScreen = false;
	}

	private void gameStart() {

		Timer loadingTimer = new Timer();
		TimerTask loadingTask = new TimerTask() {

			@Override
			public void run() { // 스레드 코드로서 JVM에 의해 호출. 반드시 오버라이딩 하여 스레드 코드를 작성하여야 한다
				mario.start(); // Mario mario 스레드 실행을 시작하도록 요청
				blocks.start();

			}
		};
		loadingTimer.schedule(loadingTask, 1000);
	}

	public void paint(Graphics g) { // JVM에 의해 호출되는 그리기 함수. repaint() 요청시 이 함수 실행
		// 더블 버퍼링 : 전체 화면에 맞는 이미지를 매순간 마다 생성해서 원하는 컴포넌트만을 출력하는 방법
		// 그냥 이미지 객체로 게임을 만들면 버퍼링이 심해 더블 버퍼링 기법을 쓴다고 한다
		bufferImage = createImage(SCREEN_WIDTH, SCREEN_HEIGHT);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(bufferImage, 0, 0, null);

	}

	public void screenDraw(Graphics g) {

		if (isMainScreen) {

			g.drawImage(resizeIntroBackground, 0, 0, null);
			g.drawImage(resizeTitleImage, 210, 175, null);

			font = new Font("Monospaced", Font.BOLD, 22); // 폰트 설정
			g.setFont(font);
			g.setColor(Color.WHITE); // 글씨 색 설정

			g.drawString("< Press Enter to Start Game >", 242, 382);

		}

		else if (isGameScreen) {
			// 224 : 맵 높이
			super.paint(g);

			// 값 임의로 넣은거라서 바꿔도 됨
			// 그릴 사이즈는 SCREEN_WIDTH X SCREEN_HEIGHT 사이즈
			// realX 는 실제 이동거리를 나타냄
			// 마리오가 일정 x좌표에 도달하면(정중앙) 마리오의 x좌표는 스피드만큼 감소시키고, realX는 스피드만큼 계속 증가
			// 그림이 끝나는 지점이 문제..

			g.drawImage(mapImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, // 맵
					realX, 0, realX + SCREEN_WIDTH / 3, mapImage.getHeight(rootPane), null);
			//
			mario.gameDraw(g); // Mario 클래스의 gameDraw() 함수 호출 - 캐릭터, 몬스터 등 그리기
			blocks.blockDraw(g);
			// isGameScreen = false;

		}

		this.repaint(); // 그래픽의 상태를 바꿨으니 화면에 구현되도록 rapaint 요청. 즉 종료될 때까지 매순간 다시 그린다
	}

	// 키 이벤트 Adapter를 상속받아 필요한 것만 구현
	class KeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) { // 키를 눌렀을 때
			switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				mario.setUp(true); // Mario.setUp == true;
				break;
			case KeyEvent.VK_DOWN:
				mario.setDown(true);
				break;
			case KeyEvent.VK_LEFT:
				mario.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
				mario.setRight(true);
				break;
			case KeyEvent.VK_R:
				if (mario.isOver())
					break;
			case KeyEvent.VK_ENTER:
				if (isMainScreen) {
					isMainScreen = false;
					isGameScreen = true;
					gameStart();
				}
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			}
		}

		public void keyReleased(KeyEvent e) { // 키를 뗴었을 때
			switch (e.getKeyCode()) {

			case KeyEvent.VK_SPACE:
				mario.setUp(false); // Mario.setUp == flase;
				// MarioJump.interrupted();
				break;
			case KeyEvent.VK_DOWN:
				mario.setDown(false);
				break;
			case KeyEvent.VK_LEFT:
				mario.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
				mario.setRight(false);
				break;

			}
		}
	}

	// 블록들의 상태, 그리기 등 블록의 모든것에 관한 클래스 // 이 클래스 위치 애매, 처음에 따로 클래스 생성했다가 마리오 좌표를 얻을 수
	// 없어 여기에 만듦.
	class Blocks extends Thread {

		private ArrayList<Block> blocks; // 미리 블럭의 좌표와 상태를 저장해둔 block 리스트
		private ArrayList<Block> currentBlocks; // 스크린 상에서 일정 범위안에 들어 실시간으로 그려져야 할 블록 리스트

		private static int blockSize = 50;
		private Block currenBlock = new Block(); // 현재 블록

		public Blocks() {
			blocks = new ArrayList<Block>();
			blocks.add(new Block(190, 413, 3)); // 테스트용 블록 좌표
			// blocks.add( new Block(210, 500, 3));
			currentBlocks = new ArrayList<Block>();
		}

		public void run() {

			while (true) {
				blockProcess();
				try {
					Thread.sleep(50); // 스레드가 잠을 자는 시간. 잠을 자는 동안 catch 블럭 실행한다

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void blockDraw(Graphics g) {
			// 살아있는 블럭대로 사각형 그려보면 수월할 것 같아 그려봤는데 좌표를 마리오 따라 그리는게 너무 어려워서 포기
			/// 스크린상의 좌표와 마리오 좌표가 아예 틀려서 큰일 ...
			int i;
			Block b = new Block();
			for (i = 0; i < currentBlocks.size(); i++) {
				b = currentBlocks.get(i);

				g.drawRect((b.x - (realX * 2)) + 400, b.y, blockSize, blockSize);

			}

		}

		public void blockProcess() {
			int i;
			for (i = 0; i < blocks.size(); i++) {
				currenBlock = blocks.get(i);
				if (currenBlock.x > realX - MarioGame.SCREEN_WIDTH / 5
						&& currenBlock.x < realX + MarioGame.SCREEN_WIDTH / 5) // 미리 구현해둔 블럭배열에서 일정 범위 안에 있는 블럭을 꺼내기
				{
//    					System.out.println((MarioGame.realX + mario.marioX) - MarioGame.SCREEN_WIDTH/2 +"  " +
//    					(int)( MarioGame.realX + mario.marioX) +(int)( MarioGame.SCREEN_WIDTH/2));

					if (!currentBlocks.contains(currenBlock)) // 현재블럭들 리스트에 포함되지 않았다면
					{
						blockActive(currenBlock); // 현재 블럭을 활성화
						currentBlocks.add(currenBlock); // 현재블럭 리스트에 추가
						System.out.println(">>>>>>>>>1111");
						System.out.println("marioX + realX = " + ((int) mario.marioX + (int) MarioGame.realX)
								+ "marioY = " + mario.marioY);
					}

				} else {
					if (!currentBlocks.isEmpty()) // 범위 안에 포함된게 아니라면
						if (currentBlocks.contains(currenBlock)) // 만약 현재 블럭들 리스트에 있다
						{
							currentBlocks.remove(currenBlock); // 그 블럭 지운다
							System.out.println("dddd  realX = " + MarioGame.realX + "marioY = " + mario.marioY);
						}

				}
			}

			// 게임 상에서 살아있는 블럭과 마리오의 행동 처리
			for (i = 0; i < currentBlocks.size(); i++) {

				currenBlock = currentBlocks.get(i); // 하나씩 블록 꺼낸다

				if (currenBlock.exist) {// 존재한다면 (깨진 블럭과 구별위해)
					// currenBlock의 공간을 침범할시 ++++ 그래픽좌표와 마리오 좌표가 틀려 수정필요... 점프못하게는 가능( 첫 이벤트 블록 주변)
					if (realX >= currenBlock.x && realX <= currenBlock.x + blockSize &&
							 currenBlock.y + blockSize > mario.marioY && currenBlock.y <= mario.marioY)
							{

						mario.setBlcoking(true);
						System.out.println(">>>>>>>>>33333");
					} else {
						mario.setBlcoking(false);
						//System.out.println(">>>>>>>>>4444");
					}
				}
			}
		}

		public void blockActive(Block block) {

			switch (block.state) {
			case 1:
				block.setBroken(false);
				block.setItem(false);
				block.setExist(true);
				break;
			case 2:
				block.setBroken(true);
				block.setItem(false);
				block.setExist(true);
				break;
			case 3:
				block.setBroken(false);
				block.setItem(true);
				block.setExist(true);
				break;
			default:
				break;
			}

		}

	}

}