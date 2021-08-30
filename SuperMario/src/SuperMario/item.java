package SuperMario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class item extends Thread {

	int i;
	private int X, Y;
	item currentItem = this;
	ArrayList<Block> currentBlocks = Blocks.currentBlocks;
	ArrayList<Block> paintBlocks = new ArrayList<Block>();
	// private Block block = new Block();
	Image mushroomItem = new ImageIcon("src/images/버섯아이템.png").getImage();
	Image eventedBlock = new ImageIcon("src/images/blockEvented.png").getImage(); // 284
	Image coin = new ImageIcon("src/images/coinItem.gif").getImage(); //100X140
	// g.drawImage(mushroomItem, 100,100,130,130,0,0,25,25, null);
	Block currentBlock = new Block();
	private static int height = 45, width = 45;
	private boolean isEvent;
	private boolean isMove;
	private boolean isFalling;
	private boolean isPaint;
	private boolean isCoin;
	itemFalling falling;
	private int delay = 20;
	private long pretime;
	Block coinBlock = new Block();
	int blockX = 408;
	int speed = 4;
	// int screenSpeed = 2;
	int direction = 1;
	int screenX = 408;
	int screenY = 413;

	Mario mario = MarioGame.mario;

	public item() {
		setIsEvent(false);
		setIsFalling(true);
		screenX = 408;
		screenY = 413;
	}

	public void mushroomEvent(Block block) {
		currentBlock = new Block();
		currentItem = new item();
		currentItem.X = block.x;
		currentItem.Y = block.y;
		screenX = 408;
		screenY = 413;
		blockX =  408 - (MarioGame.realX - block.x);
		Block paintBlock = new Block(blockX, block.y);
		
		paintBlocks.add(paintBlock);
		setIsEvent(true);
		setIsMove(true);
		setIsPaint(true);
		System.out.println("머쉬룸 이벤트함수 발생");
		// System.out.println("currentItemX : " + currentItem.X);
		System.out.println(
				"realX : " + MarioGame.realX + " this.currentItem (" + this.currentItem.X + ", " + this.currentItem.Y
						+ ") screemX : " + screenX + " marioY : " + MarioGame.mario.marioY + " isMove : " + isMove);

	}
	public void coinEvent(Block block) {
		MarioGame.mario.coin++;
		coinBlock = new Block( 408 - (MarioGame.realX - block.x), block.y - 70);
		setIsCoin(true);
		System.out.println("코인 이벤트함수 발생");
		if (isCoin) {		
			System.out.println("11111 coin : " + isCoin);
			coinUp coinEvent = new coinUp();
			coinEvent.start();
		}
	}
	
	class coinUp extends Thread {

		int basicY;

		coinUp() {
			basicY = coinBlock.y;
		}

		public void run() {

			
			while (true) {

				
				while (coinBlock.y > basicY - 70) {
					coinBlock.y -= 1;
					

					try { // 위로 솟구치는 것을 천천히
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}		
				setIsCoin(false);
				break;

			}
		}

	}

	public void reset() {
		currentBlock = new Block();
		this.setIsEvent(false);
		this.setIsMove(false);
//		currentItem.X = block.x;
//		currentItem.Y = block.y;
		screenX = 408;
		screenY = 413;
		i = 0;
	}


	public void run() { // 스레드를 시작할 떄 실행하는 코드. Mario.start()로 호출 가능

		
		while (true) {
			
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime + 15 < delay) {

					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime); // 스레드가 잠을 자는 시간. 잠을 자는 동안 catch 블럭
																				// 실행한다
						move();
						itemProcess();
						// 이외에 여러가지 프로세스 실행하도록 할 예정
						// playerAttackProcess();

						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
		}
	}

	public void itemDraw(Graphics g) {
		// System.out.println("isEvent : " + isEvent);
		int a = 120;
		//System.out.println("2222 coin : " + isCoin);
		if(isPaint) {
			for(int i=0; i< paintBlocks.size(); i++) {
				
				// paintBlocks.get(i).x = blockX;
				 g.setColor(new Color(92,148,252));
				 //g.fillRect( paintBlocks.get(i).x - 20, paintBlocks.get(i).y+5, 100,  60);		
				//System.out.println("그려지는 블록" + i+ " x좌표 = "+ paintBlocks.get(i).x);
				 g.drawImage(eventedBlock, paintBlocks.get(i).x , paintBlocks.get(i).y -5, paintBlocks.get(i).x +60 , paintBlocks.get(i).y + 50, 2, 0, 284, 284, null);
			}			
		}
		if (isEvent) { // 움직이는 버섯 그림
			g.drawImage(mushroomItem, screenX, screenY - height, screenX + width, screenY, 0, 0, 25, 25, null);
		}
		if(isCoin) {			
			g.drawImage(coin, coinBlock.x +20, coinBlock.y, coinBlock.x +50, coinBlock.y + 30,0,0,140,140, null);					
		}
		
	}

	public void move() {

		if (isMove) {
			if (direction == 1) {
				screenX += speed;
				currentItem.X += speed;
				
			} else {
				screenX -= speed;
				currentItem.X -= speed;	
			}
			
		}
		if (MarioGame.mario.left && !MarioGame.mario.blocking1) {
			screenX += 6;
			blockX +=6;
			for(int i=0; i< paintBlocks.size(); i++) 
				 paintBlocks.get(i).x += 6;
			// currentItem.X += speed;
		}
		if (MarioGame.mario.right&& !MarioGame.mario.blocking1) {
			screenX -= 6;
			blockX -=6;
			for(int i=0; i< paintBlocks.size(); i++) 
				 paintBlocks.get(i).x -= 6;
			// currentItem.X += 3;
		}

		// 일정 범위 밖으로 가면 버섯 삭제
		if (currentItem.X >= MarioGame.realX + 500 || currentItem.X <= MarioGame.realX - 500)
			reset();

	}

	public void itemProcess() {

		for (i = 0; i < Blocks.currentBlocks.size(); i++) {

			currentBlock = Blocks.currentBlocks.get(i); // 하나씩 블록 꺼낸다
			if (currentBlock.exist) {

				if (this.currentItem.X >= currentBlock.x && this.currentItem.X <= currentBlock.x + 50) { // 우선 현재 블럭의
																											// 영역에 있을 때
					if (this.currentItem.X >= currentBlock.x + 48 && direction == 1) {
						
						if (i + 1 != Blocks.currentBlocks.size()) { // 그래서 마지막 블록은 작동 안된다

							if(direction == 1)
								currentBlock = Blocks.currentBlocks.get(i + 1); // 다음 블록을 가져온다
							

							if (this.currentItem.X + 3 >= currentBlock.x && this.currentItem.X + 3<= currentBlock.x + 47 ) // 다음블럭이 있다면																								// 있다면
							{
								System.out.println("다음 블럭이 존재 " + this.currentItem.X + " >= " + currentBlock.x + " && "
										+ this.currentItem.X + " <= " + (currentBlock.x + 50));

							} 
						
							else {
								// 하나씩
//								for(int j = 0; j< Blocks.currentBlocks.size(); j++)
//									System.out.print(" " +Blocks.currentBlocks.get(j).x + " " ); // 블록 배열에 들어있는 x좌표들 출력
//								System.out.println();								
//								System.out.println("dddddd currentItemX : " + this.currentItem.X + " currentItemY : "
//										+ currentItem.Y);
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
				if (this.currentItem.X + width >= currentBlock.x && direction ==1 || this.currentItem.X  <= currentBlock.x && direction ==-1) { 

					if (currentBlock.y == this.currentItem.Y + 5) { // 블록의 Y는 613일 때 가능
						// 50이고 버섯의
						// System.out.println("반대로반대로-----------------"); // Y는 45라서// +5

						if (direction == 1)
							direction = -1;
						else
							direction = 1;
					}
				}

			}

		}
		// 먹히기
		if (isMove) {
			if (currentItem.X <= MarioGame.realX + MarioGame.mario.marioWidth - 5 && currentItem.X + 45 >= MarioGame.realX
					&& currentItem.Y + 30 >= MarioGame.mario.marioY) {
				System.out.println("먹히기 realX : " + MarioGame.realX + " this.currentItem (" + this.currentItem.X + ", "
						+ this.currentItem.Y + ") screemX : " + screenX + " marioY : " + MarioGame.mario.marioY
						+ " isMove : " + isMove);
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
				// System.out.println("폴링 실행");
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
	public void setIsPaint(boolean isPaint) {
		this.isPaint = isPaint;
	}
	public void setIsCoin(boolean isCoin) {
		this.isCoin = isCoin;
	}
}
