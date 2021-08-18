package SuperMario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

//import SuperMario.Mario.OperationTread;

public class Mario extends Thread { // 스레드 상속

	private int delay = 20;
	private long pretime;
	private int cnt; // 프로그램 상 시간
	private int score; // 점수

	private boolean up; // 키보드의 상태를 나타내는 bloolean 변수
	private boolean down;
	 boolean left;
	 boolean right;
	private boolean isOver;
	private boolean jump = true;
	private boolean falling;
	private boolean blocking;
	boolean moveS;

	private boolean blocking1 = false;
	private boolean blocking2 = false;
	private boolean blocking3 = false;
	private boolean blocking4 = false;

	private Image allImage = new ImageIcon("src/images/allMario.png").getImage();

	int marioX, marioY;
	int marioWidth = 42;
	 int marioHeight = marioWidth + 5;
	 int marioSpeed = 2;
	private int marioLife;
	private int jumpMax;

	private int imageX, imageY;
	private int marioDirection = 1;

	static MarioJump marioJump;
//	 public OperationTread op = new OperationTread();

	public Mario() {
		marioX = 30;
		marioY = MarioGame.SCREEN_HEIGHT - 118; // 임의값
		moveS = false;
		// op.start();
	}

	// 마리오 스레드
	public void run() { // 스레드를 시작할 떄 실행하는 코드. Mario.start()로 호출 가능

		reset();
		while (true) {
			while (!isOver) {
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime); // 스레드가 잠을 자는 시간. 잠을 자는 동안 catch 블럭
																					// 실행한다
						keyProcess(); // 키 프로세스 실행
						// 이외에 여러가지 프로세스 실행하도록 할 예정
						// playerAttackProcess();

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
		marioX = 30;
		marioY = MarioGame.SCREEN_HEIGHT - 118;
		// (MarioGame.SCREEN_HEIGHT - marioHeight) / 2;
	}

	private void keyProcess() {
		// 각 속성의 true, false 상태에 따라 결과 지정
		if (up && marioY - marioSpeed > 0) {
			if (jump) {
				setJump(false);
				marioJump = new MarioJump();
				marioJump.start();
			}

		}
//		if (blocking1) {
//			System.out.println("Jump : " + jump);
//			if (!jump) // 점프중이다
//				setFalling(true);
//			if (right)
//				MarioGame.realX -= marioSpeed;
//			if (left)
//				MarioGame.realX += marioSpeed;
//			
			// marioX -= 2*marioSpeed;

//		}
//		if (down && marioY + marioHeight + marioSpeed < MarioGame.SCREEN_HEIGHT)
//			marioY += marioSpeed;
		if (left && marioX - marioSpeed > 0) {			
			if(!moveS) 
				marioX -= marioSpeed;
			
			MarioGame.realX -= marioSpeed;
			marioDirection = -1;
			System.out.println( "llllllllllll marioX : " +marioX + " realX : " + MarioGame.realX);

		}
		
		if (right && marioX + marioWidth + marioSpeed < MarioGame.SCREEN_WIDTH) {
			if(!moveS)
				marioX += marioSpeed;
			
			MarioGame.realX += marioSpeed;
			marioDirection = 1;
			System.out.println( "marioX : " +marioX + " realX : " + MarioGame.realX );
		}
		if (marioX + MarioGame.SCREEN_WIDTH / 2 >= MarioGame.SCREEN_WIDTH) { // 중앙에 오도록
			setMoveS(true);
			//System.out.println(marioX + " " + MarioGame.realX);
			marioSpeed = 2;

		}
		if (!moveS) {
			marioSpeed = 4;
		}

	}

	public void gameDraw(Graphics g) {
		playerDraw(g); // MarioGame 클라스의 paint()함수 안에 있는 gameDraw() 함수

	}

	// ************점프 상태 값이 계속 false여서 이 스레드가 호출되지 않고 있음을 알았다가
	// ************* while로 무한 반복을 안해줘서가 문제였음
	// **************하지만 막상 잘 돌아가니깐 스레드 interrupt 오류가 발생
	// **************** 없어도 잘 돌아가서 지우긴 했는데 수정필요
//	//점프스레드를 중단하는 스레드
//		public class OperationTread extends Thread {
//			
//		public void run() {
//			while(true) {
//					System.out.println("점프중단");
//					try {
//						
//						if(!jump) {	//점프가 끝났으면				
//							setJump(true); // 점프 스레드에서 최대 높이에 다다를 때 setJump(true)를 호출해서 여기는 지움
//							marioJump.interrupt(); 	//점프스레드를 종료
//						}
//						
//						Thread.sleep(1000);
//						 
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		
//		}

	class MarioJump extends Thread {

		private int basicX, basicY;

		MarioJump() {
			basicX = marioX;
			basicY = marioY;
		}

		public void setLocation() {
			basicX = marioX;
			basicY = marioY;
		}

		void finishCheck() {
			if (basicY <= marioY) { // 블럭위에 있는 경우 등은 basic 바꾸거나 추가 기능 필요
				setJump(false);
				System.out.println("같다");

				if (blocking4)
					setFalling(false);

			}

		}

		public void run() {

			jumpMax = 100;

			while (true) {

				marioY -= 1;
				if (marioY == basicY - jumpMax && up && jumpMax < 230) // 일정 지점까지 스페이스는 계속 누르고 있으면 추가점프
					jumpMax += 15;
				if (blocking3)
					setFalling(true);
				// setJump(true);
				// System.out.println(" marioX + realX = "+ ((int)marioX + (int)MarioGame.realX)
				// +" marioY = " + marioY + " " + jump );
				if (marioY < basicY - jumpMax || falling) { // 최대높이만큼 점프한다면
					setFalling(true); // 떨어지는 중. 마리오 그리기 위해 추가
					// setJump(true);
					while (marioY < basicY) { // 다시 처음 y로 돌아올 때 까지 떨어지기

						// System.out.println("dddd marioX + realX = "+ ((int)marioX +
						// (int)MarioGame.realX) + "marioY = " + marioY);
						if (marioY == 413 - marioHeight && MarioGame.realX >= 522
								&& MarioGame.realX <= 522 + Blocks.blockXsize) {
							System.out.println("marioY = " + marioY);
							setBlcoking4(true);

						} else
							marioY += 1;
						try {
							finishCheck();
							Thread.sleep(1);

						} catch (InterruptedException e) {
							return;
						}

					}
					setBlcoking4(false);
					setFalling(false);
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

	public void playerDraw(Graphics g) { // 마리오의 좌표에 따라 그 좌표에 그려지도록 구현

		imageX = 176; // 서있는 마리오 좌표
		imageY = 32;

		if (up || falling) {
			if (marioDirection == 1) {
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 143, 32, 158, 48,
						null);

			} else {
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 158, 32, 143, 48,
						null);

			}

		}

		else if (left) {
			if (cnt % 12 >= 0 && cnt % 12 < 3)
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 95, 32, 79, 48, null);
			else if (cnt % 12 >= 3 && cnt % 12 < 6)
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 110, 32, 95, 48, null);
			else
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 125, 32, 110, 48,
						null);
		}

		else if (right) {
			// cnt % 3으로 했더니 이미지 변화가 너무 빨라 적당한 값 12를 기준으로 함. 변경 가능
			// 전체 이미지 파일을 써, 그려야 되는 이미지의 좌표를 바꿔줌 x축으로 약 15, y축으로 약 16
			// 걷는게 뭔가 이상
			if (cnt % 12 >= 0 && cnt % 12 < 3)
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 79, 32, 96, 48, null);
			else if (cnt % 12 >= 3 && cnt % 12 < 6)
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 95, 32, 111, 48, null);
			else
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, 110, 32, 126, 48,
						null);
		} else {
			g.drawRect(marioX, marioY, marioWidth, marioHeight);
		}

		if (!right && !left && !up && !down && !falling) // 가만히 있을 경우
			if (marioDirection == 1)
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, imageX, imageY,
						imageX + 15, imageY + 16, null);
			else
				g.drawImage(allImage, marioX, marioY, marioX + marioWidth, marioY + marioHeight, imageX + 15, imageY,
						imageX, imageY + 16, null);
		else {
			g.drawRect(marioX, marioY, marioWidth, marioHeight);
		}
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

	public void setFalling(boolean falling) {
		this.falling = falling;
	}

	public void setBlcoking(boolean blocking) {
		this.blocking = blocking;
	}

	public void setMoveS(boolean moveS) {
		this.moveS = moveS;
	}

	public void setBlcoking1(boolean blocking1) {
		this.blocking1 = blocking1;
	}

	public void setBlcoking2(boolean blocking2) {
		this.blocking2 = blocking2;
	}

	public void setBlcoking3(boolean blocking3) {
		this.blocking3 = blocking3;
	}

	public void setBlcoking4(boolean blocking4) {
		this.blocking4 = blocking4;
	}
}
