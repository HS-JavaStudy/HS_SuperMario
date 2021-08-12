package SuperMario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import SuperMario.Mario.OperationTread;

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
	private int jumpMax = 150;
	private int jumpWidth;
	private int imageX,imageY;
	private int time;
	private int marioDirection= 1;
	private int jumpFlag = 1;
	
	static MarioJump marioJump;

	public Mario() {
		 marioX = 10;
		 marioY= (MarioGame.SCREEN_HEIGHT - marioHeight) / 2;
		 OperationTread op = new OperationTread();
		 op.start();
	}
	
	//점프스레드를 중단하는 스레드
	public class OperationTread extends Thread {
		
		public void run() {
			
			try {
				
				if(!jump) {	//점프가 끝났으면				
					setJump(true); //다시 점프할 수 있게 하고
					marioJump.interrupt(); 	//점프스레드를 종료
				}
				
				Thread.sleep(1000);
				 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	//마리오 스레드
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
//	        	if(marioDirection == 1 ||marioDirection == -1 || marioDirection == 0 && up ) {
//	        		marioDirection = 0;
//	     
		        	if(jump) {
		        		setJump(false);
		        		marioJump = new MarioJump();
		        		marioJump.start();
	        	}
//	        	
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
		
		void finishCheck() {
			
			if(basicY == marioY) { 
				setJump(false);
				System.out.println("같다");
			}
		}
		
		public void getLocation () {
			basicX = marioX;
			basicY = marioY;
		}
		public void run() {
			
			//if(marioDirection == 0) {
	
			while (true) {

				marioY -= 1;
				System.out.println("marioY = " + marioY);
				if (marioY < basicY - jumpMax) { // 최대높이만큼 점프한다면

					while (marioY <  basicY) { // 다시 처음 y로 돌아올 때 까지 떨어지기

						System.out.println("dddd marioY = " + marioY);
						marioY += 1;
						try {
							finishCheck();
							Thread.sleep(2);

						} catch (InterruptedException e) {
							return;
						}

					}
					setJump(true);
					break;
				}

				try { // 위로 솟구치는 것을 천천히
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}
	  
	  

		public void playerDraw(Graphics g) { //마리오의 좌표에 따라 그 좌표에 그려지도록 구현
			
			imageX = 176; //서있는 마리오 좌표
			imageY = 32;
			if(up) { // 점프 애니메이션 구현해야함.
				
				 
					if(marioDirection == 1) g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 143, 32, 158, 48, null); 
					else  g.drawImage(allImage , marioX, marioY,marioX + marioWidth, marioY + marioHeight, 158, 32, 143, 48, null); 
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
				if(marioDirection == 1) g.drawImage(allImage  , marioX, marioY,marioX + marioWidth, marioY + marioHeight, imageX, imageY, imageX + 15, imageY + 16, null); 
				else g.drawImage(allImage  , marioX, marioY,marioX + marioWidth, marioY + marioHeight, imageX + 15, imageY , imageX, imageY + 16,  null); 
			
		        
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
