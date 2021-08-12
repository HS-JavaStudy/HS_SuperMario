package SuperMario;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class MarioGame extends JFrame{

	private Image bufferImage;	//더블 버퍼링을 위한 전체 이미지를 담는 변수
	private Graphics screenGraphic;
	
	public static final int SCREEN_WIDTH = 816;
    public static final int SCREEN_HEIGHT = 678;
    
    private Mario mario = new Mario();
    
    public MarioGame() {
    	// 게임을 출력할 창 지정
    	setSize(SCREEN_WIDTH, SCREEN_HEIGHT); 
    	setTitle("Super Mario!");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        addKeyListener(new KeyListener()); // 마우스 이벤트 추가
        gameStart(); // 바로 게임 스타트
    }
    
    void init() {
    	
    }
    private void gameStart() {
       
        Timer loadingTimer = new Timer();
        TimerTask loadingTask = new TimerTask() {
            @Override
            public void run() { //스레드 코드로서 JVM에 의해 호출. 반드시 오버라이딩 하여 스레드 코드를 작성하여야 한다
            	 mario.start(); //Mario mario 스레드 실행을 시작하도록 요청
            } 
        };
        loadingTimer.schedule(loadingTask, 3000);
    }
 

    public void paint(Graphics g) { // JVM에 의해 호출되는 그리기 함수. repaint() 요청시 이 함수 실행
    	//더블 버퍼링 : 전체 화면에 맞는 이미지를 매순간 마다 생성해서 원하는 컴포넌트만을 출력하는 방법 
    	// 그냥 이미지 객체로 게임을 만들면 버퍼링이 심해 더블 버퍼링 기법을 쓴다고 한다
    	 bufferImage = createImage(SCREEN_WIDTH,SCREEN_HEIGHT); 
         screenGraphic = bufferImage.getGraphics();
         screenDraw(screenGraphic);
         g.drawImage(bufferImage, 0, 0, null);
    }

    public void screenDraw(Graphics g) {
       
        mario.gameDraw(g); // Mario 클래스의 gameDraw() 함수 호출 - 캐릭터, 몬스터 등 그리기
      
        this.repaint();	// 그래픽의 상태를 바꿨으니 화면에 구현되도록 rapaint 요청. 즉 종료될 때까지 매순간 다시 그린다
    }

    //키 이벤트 Adapter를 상속받아 필요한 것만 구현
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
                    
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }

        public void keyReleased(KeyEvent e) { //키를 뗴었을 때
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                	mario.setUp(false); // Mario.setUp == flase;
                	
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

    
}
