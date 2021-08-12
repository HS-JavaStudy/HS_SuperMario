package SuperMario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Mario extends Thread{ //스레드 상속

	private int delay = 20;
	private long pretime;
	private int cnt; // 프로그램 상 시간
	private int score; // 점수
	
	
	private boolean up; //키보드의 상태를 나타내는 bloolean 변수
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean isOver;
	private boolean jump = true;
		
	private Image allImage = new ImageIcon("src/images/allMario.png").getImage();

	private int marioX, marioY;
	private int marioWidth = 35;
	private int marioHeight = 50;
	private int marioSpeed = 5;
	private int marioLife;
	private int jumpMax = 170;
	private int jumpWidth;
	private int imageX,imageY;
	private int time;
	private int marioDirection= 1;
	static MarioJump marioJump;

	public Mario() {
		 marioX = 10;
		 marioY= (MarioGame.SCREEN_HEIGHT - marioHeight) / 2;
	}

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
	        		//(MarioGame.SCREEN_HEIGHT - marioHeight) / 2;
	    }
	 
	 private void keyProcess() {
		 // 각 속성의 true, false 상태에 따라 결과 지정
	        if (up && marioY - marioSpeed > 0) {
	        	if(marioDirection == 1 ||marioDirection == -1 && up ) {
	        		marioDirection = 0;

		        	if(jump) {
		        		setJump(false);
		        		marioJump = new MarioJump();
		        		marioJump.start();
		        	}
	        	}
	        	else if (marioDirection == 1 && right && up) {
	        		marioJump = new MarioJump();
		        	marioJump.start();
	        	}
	        		
	        	marioJump = new MarioJump();
	        	marioJump.start();
	        }
	        if (down && marioY + marioHeight + marioSpeed < MarioGame.SCREEN_HEIGHT) marioY += marioSpeed;
	        if (left && marioX - marioSpeed > 0) {
	        	marioX -= marioSpeed;
	        	marioDirection = -1;
	        }
	        if (right && marioX + marioWidth + marioSpeed < MarioGame.SCREEN_WIDTH) {
	        	marioX += marioSpeed;
	        	marioDirection = 1;
	        }
	      
	    }
	 
	  public void gameDraw(Graphics g) {
	        playerDraw(g); // MarioGame 클라스의 paint()함수 안에 있는 gameDraw() 함수
	       
	    }

	class MarioJump extends Thread {
		
		private int basicX, basicY;
		
		MarioJump(){
			getLocation ();
		}
		
		public void getLocation () {
			basicX = marioX;
			basicY = marioY;
		}
		public void run() {
			
			if(marioDirection == 0) {
				while(true) {
					
					if(up && basicY - jumpMax < marioY) 
					{
							 
								 marioY -= 1;
							
						}
					else {
						
					}
					try {
						Thread.sleep(100);
						}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			else if (marioDirection == 1) System.out.println("1");
			else if(marioDirection == -1) System.out.println("-1");
			
		}
	}
	  
	  

		public void playerDraw(Graphics g) { //마리오의 좌표에 따라 그 좌표에 그려지도록 구현
			
			imageX = 176; //서있는 마리오 좌표
			imageY = 32;
			if(up) { // 점프 애니메이션 구현해야함.
				time = cnt;
				 
					if(right) g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 143, 32, 158, 48, null); 
					else if(left) g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 158, 32, 143, 48, null); 
				}
				
			
			else if(left) 
				{
				if(cnt % 12 >=0 && cnt % 12 <3) 
					g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 95, 32, 79, 48, null); 				
				else if(cnt % 12 >=3 && cnt % 12 <6) 
					g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight,110, 32, 95, 48,  null);
				else 
					g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 125, 32,110, 48,  null);
				}
			else if(right)
				 {
				// cnt % 3으로 했더니 이미지 변화가 너무 빨라 적당한 값 12를 기준으로 함. 변경 가능 
				//전체 이미지 파일을 써, 그려야 되는 이미지의 좌표를 바꿔줌 x축으로 약 15, y축으로 약 16 
				// 걷는게 뭔가 이상
					if(cnt % 12 >=0 && cnt % 12 <3) 
						g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 79, 32, 95, 48, null); 
					else if(cnt % 12 >=3 && cnt % 12 <6)
						g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 95, 32, 110, 48, null);
					else 
						g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 110, 32, 125, 48, null);
				 }
			
			if(!right && !left && !up && !down) // 가만히 있을 경우
				g.drawImage(allImage  , marioX, marioY,marioX + marioWidth, marioY + marioHeight, imageX, imageY, imageX + 15, imageY + 16, null); 
			
		        
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
	    
	    public void setJump(boolean jump) {
	        this.jump = jump;
	    }


	   
}
