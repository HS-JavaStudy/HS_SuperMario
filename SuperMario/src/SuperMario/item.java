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

		this.setIsEvent(true);
		this.setIsMove(true);
		System.out.println("머쉬룸 이벤트함수 발생");
		System.out.println("currentItemX : " + currentItem.X);

	}

	public void run() { // 스레드를 시작할 떄 실행하는 코드. Mario.start()로 호출 가능

		while (true) {

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
			// g.drawImage(mushroomItem, currentItem.X - a, currentItem.Y - height,
			// currentItem.X + width - a,
			// currentItem.Y, 0, 0, 25, 25, null);
			g.drawImage(mushroomItem, this.screenX, this.screenY - height, this.screenX + width, this.screenY, 0, 0, 25,
					25, null);
//			System.out.println("Draw  screenX : " + this.screenX + " currentItemX : " + currentItem.X
//					+ " currentItemY : " + currentItem.Y);
		}

	}

//	public void screenProcess() {
//		if (MarioGame.mario.left)
//			screenX += 2; /// 2,3
//		if (MarioGame.mario.right)
//			

//	}

	public void move() {
		int i;

		if (isMove) {
			if (this.direction == 1) {
				this.currentItem.X += speed;
				this.screenX += 2;
			}

			else {
				this.currentItem.X -= speed;
				this.screenX -= 2;
			}

		}

		if (MarioGame.mario.left) {
			this.currentItem.X += speed;
			this.screenX += 2;
		}

		if (MarioGame.mario.right) {
			this.currentItem.X -= speed;
			this.screenX -= 2;
		}

	}

	public void itemProcess() {

		int i;
		for (i = 0; i < Blocks.currentBlocks.size(); i++) {

			currentBlock = Blocks.currentBlocks.get(i); // 하나씩 블록 꺼낸다
			if (currentBlock.exist) {
				// System.out.println("blockX : " + currentBlock.x + " blockY : " +
				// currentBlock.y);
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
								System.out.println("dddddd currentItemX : " + this.currentItem.X + " currentItemY : "
										+ currentItem.Y);
								if (isFalling) {
									setIsFalling(false);
									falling = new itemFalling();
									falling.start();
								}

							}
						}
					}

				}

				if (this.currentItem.X + width == currentBlock.x && currentBlock.y + 5 == this.currentItem.Y) { // 블록의 Y는
																											// 50이고 버섯의
					System.out.println("반대로반대로-----------------");																					// Y는 45라서
																											// +5
					if (direction == 1)
						direction = -1;
					else
						direction = 1;
				}
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
