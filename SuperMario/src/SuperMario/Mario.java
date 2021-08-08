package SuperMario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Mario extends Thread{

	private int delay = 20;
	private long pretime;
	private int cnt; // 프로그램 상 시간
	private int score; // 점수
	
	boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean isOver;
	
	private Image smallRightMario = new ImageIcon("src/images/smallRightMario.png").getImage(); // 이미지 객체 생성
	private Image smallLeftMario = new ImageIcon("src/images/smallLeftMario.png").getImage();
	private Image smallJumpRightMario = new ImageIcon("src/images/smallJumpRightMario.png").getImage();
	private Image smallJumpLeftMario = new ImageIcon("src/images/smallJumpLeftMario%20.png").getImage();
	private Image currentImage;

	private int marioX, marioY;
	private int marioWidth = 35;
	private int marioHeight = 50;
	private int marioSpeed = 7;
	private int marioHp = 30;



	public void run() { //스레드를 시작할 떄 실행하는 코드. Mario.start()로 호출 가능

		reset();
		while (true) {
			while (!isOver) {
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime); // 스레드가 잠을 자는 시간. 잠을 자는 동안 catch 블럭 실행한다
						keyProcess(); // 키 프로세스 실행 
						// 이외에 여러가지 프로세스 실행하도록 할 예정
						//playerAttackProcess();
					
						cnt++; // 초 증가
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	 public void reset() {
	        isOver = false;
	        cnt = 0;
	        score = 0;
	        marioX = 10;
	        marioY = (MarioGame.SCREEN_HEIGHT - marioHeight) / 2;
	    }
	 
	 private void keyProcess() {
		 // 각 속성의 true, false 상태에 따라 결과 지정
	        if (up && marioY - marioSpeed > 0) marioY -= marioSpeed;
	        if (down && marioY + marioHeight + marioSpeed < MarioGame.SCREEN_HEIGHT) marioY += marioSpeed;
	        if (left && marioX - marioSpeed > 0) marioX -= marioSpeed;
	        if (right && marioX + marioWidth + marioSpeed < MarioGame.SCREEN_WIDTH) marioX += marioSpeed;
	      
	    }
	 
	  public void gameDraw(Graphics g) {
	        playerDraw(g); // MarioGame 클라스의 paint()함수 안에 있는 gameDraw() 함수
	       
	    }

	  

		public void playerDraw(Graphics g) { //마리오의 좌표에 따라 그 좌표에 그려지도록 구현
			
			// smallJumpRightMario
			//smallLeftMario 
			if(left) 
				currentImage = smallLeftMario;
			if(right)
				currentImage = smallRightMario;
			
			 g.drawImage(currentImage  , marioX, marioY, //  해당 위치부터
					marioX + marioWidth, marioY + marioHeight, // 이 위치 까지
					0, 0, 1280, 1280, null); //마리오 사진의 (0,0) (1280, 1280)까지의 이미지를 그리기
			
		        
		}
	    
	    public boolean isOver() {
	        return isOver;
	    }

	    public void setUp(boolean up) {
	        this.up = up;
	    }

	    public void setDown(boolean down) {
	        this.down = down;
	    }

	    public void setLeft(boolean left) {
	        this.left = left;
	    }

	    public void setRight(boolean right) {
	        this.right = right;
	    }

	   
}
