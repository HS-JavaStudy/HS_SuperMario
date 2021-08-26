package SuperMario;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class item extends Thread {

	private int X, Y;
	item currentItem = this;
	ArrayList<Block> currentBlocks = Blocks.currentBlocks;
	// private Block block = new Block();
	Image mushroomItem = new ImageIcon("src/images/버섯아이템.png").getImage();
	
	// g.drawImage(mushroomItem, 100,100,130,130,0,0,25,25, null);
	Block currentBlock = new Block();
	private static int height = 45, width = 45;
	private boolean isEvent;
	private boolean isMove;
	private boolean isFalling;
	itemFalling falling;

	int speed = 1;
	int screenSpeed = 3;
	int direction = 1;
	int screenX = 408;
	int screenY = 413;
	Mario mario = MarioGame.mario;

	public item() {
		setIsEvent(false);
		setIsFalling(true);
	}

	public void mushroomEvent(Block block) {

		currentItem = new item();
		currentItem.X = block.x;
		currentItem.Y = block.y;
		screenX = 408;
		screenY = 413;
		setIsEvent(true);
		setIsMove(true);
		System.out.println("머쉬룸 이벤트함수 발생");
		System.out.println("currentItemX : " + currentItem.X);

	}
	
	public void reset() {
		this.setIsEvent(false);
		this.setIsMove(false);
	}

	public void run() { // 스레드를 시작할 떄 실행하는 코드. Mario.start()로 호출 가능

		while (true) {
		//	System.out.println("isEvent : " + isEvent);
			
			
				
				try {
					move();
					itemProcess();
					// screenProcess();
					Thread.sleep(2);

				} catch (InterruptedException e) {
					return;
				}
				
				try { // 위로 솟구치는 것을 천천히
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
						
		}
	}

	public void itemDraw(Graphics g) {
		// System.out.println("isEvent : " + isEvent);
		int a = 120;
		if (isEvent) {
			
//			g.drawImage(mushroomItem, screenX, screenY - height, screenX + width,screenY, 0, 0, 25,
//					25, null);
			g.drawImage(mushroomItem,screenX , screenY - height, screenX + width, screenY, 0, 0, 25,
					25, null);
			System.out.println("realX : " + MarioGame.realX + " this.currentItem.x : " + this.currentItem.X 
					+ " screemX : " + screenX+ " screemY : " + screenY + " isMove : " + isMove);
		}

	}


	public void move() {
		int i;
		
		if (isMove) {
			if (direction == 1) {
				
				currentItem.X += speed;
				screenX += screenSpeed;
			}

			else {
				
				currentItem.X -= speed;
				screenX -= screenSpeed;;
			}

			if (MarioGame.mario.left ) {
				//currentItem.X += speed;
				screenX += screenSpeed;;
			}

			if (MarioGame.mario.right ) {
				//currentItem.X -= speed;
				screenX -= screenSpeed;;
			}
		}
		
		//일정 범위 밖으로 가면 버섯 삭제
		if(currentItem.X >= MarioGame.realX +500 || currentItem.X <= MarioGame.realX - 500)
			reset();

	}

	public void itemProcess() {

		int i;
		for (i = 0; i < Blocks.currentBlocks.size(); i++) {

			currentBlock = Blocks.currentBlocks.get(i); // 하나씩 블록 꺼낸다
			if (currentBlock.exist) {
				
				if (this.currentItem.X >= currentBlock.x && this.currentItem.X <= currentBlock.x + 20) { // 우선 현재 블럭의
																											// 영역에 있을 때
					if (this.currentItem.X == currentBlock.x + 20) {

						if (i + 1 != Blocks.currentBlocks.size()) {

							currentBlock = Blocks.currentBlocks.get(i + 1);

							if (this.currentItem.X + 1 >= currentBlock.x && this.currentItem.X <= currentBlock.x + 20) // 다음블럭이
																														// //
																														// 있다면
							{
								System.out.println("111111 " + this.currentItem.X + " >= " + currentBlock.x + " && "
										+ this.currentItem.X + " <= " + (currentBlock.x + 20));

							} else {
								// 하나씩
								//System.out.println("dddddd currentItemX : " + this.currentItem.X + " currentItemY : "+ currentItem.Y);
								if (isFalling) {
									setIsFalling(false);
									falling = new itemFalling();
									falling.start();
								}

							}
						}
					}

				}
				// 부딪히면 반대 방향으로
				if (this.currentItem.X + width >= currentBlock.x ) { //방향이 1일 때만 가능
		
					if (currentBlock.y  == this.currentItem.Y+ 5) { // 블록의 Y는 613일 때 가능
						// 50이고 버섯의
						//System.out.println("반대로반대로-----------------"); // Y는 45라서// +5

						if (direction == 1)
							direction = -1;
						else
							direction = 1;
					}
				}

			}

		}
		//먹히기
		if(isMove) {
			if( currentItem.X <=MarioGame.realX  && currentItem.X + 8<= MarioGame.realX && currentItem.Y >= MarioGame.mario.marioY ) {
				System.out.println("realX : " + MarioGame.realX + " this.currentItem (" + this.currentItem.X + ", " + this.currentItem.Y
						+ ") screemX : " + screenX+ " marioY : " + MarioGame.mario.marioY + " isMove : " + isMove);
				MarioGame.mario.setbigMario(true); // 먹었다
				reset();
				
			}
				
		}

	}

	class itemFalling extends Thread {

		private int basicX, basicY;

		itemFalling() {
			basicX = currentItem.X;
			basicY = currentItem.Y;
		}

		public void run() {

			// System.out.println("폴링 실행");
			while (true) {

				setIsMove(false);
				while (currentItem.Y < MarioGame.SCREEN_HEIGHT - 70) {
					currentItem.Y += 1;
					screenY += 1;

					try { // 위로 솟구치는 것을 천천히
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				 //System.out.println("폴링 실행");
				setIsMove(true);
				setIsFalling(true);
				break;

			}
		}

	}

	public void setIsEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}

	public void setIsMove(boolean isMove) {
		this.isMove = isMove;
	}

	public void setIsFalling(boolean isFalling) {
		this.isFalling = isFalling;
	}
}
