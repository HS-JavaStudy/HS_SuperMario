package SuperMario;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class item extends Thread {

	private int X,Y;
	item currentItem = this;
	ArrayList<Block> currentBlocks = Blocks.currentBlocks;
	//private Block block = new Block();
	Image mushroomItem = new ImageIcon("src/images/버섯아이템.png").getImage();
	//g.drawImage(mushroomItem, 100,100,130,130,0,0,25,25, null);
	Block currentBlock = new Block();
	private static int height = 45, width =45;
	private boolean isEvent;
	private boolean isMove;
	itemFalling falling;
	
	int speed = 1;
	int direction = 1;
	int screenX = 410;
	int screenY = 370;
	Mario mario = MarioGame.mario;
	
	
	public item() {
		setIsEvent(false);
	}
	public void mushroomEvent(Block block) {
		
		currentItem = new item();
		currentItem.X= block.x;
		currentItem.Y = block.y;
		this.setIsEvent(true);
		this.setIsMove(true);
		System.out.println("머쉬룸 이벤트함수 발생");

		
	}

	public void run() { // 스레드를 시작할 떄 실행하는 코드. Mario.start()로 호출 가능

		
		while (true) {

	
					try {
						move();
						screenProcess();
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
		//System.out.println("isEvent : " + isEvent);
		int a = 120;
		if(isEvent)
		{
			g.drawImage(mushroomItem, currentItem.X -a  ,currentItem.Y - height, currentItem.X +width -a,currentItem.Y  , 0, 0, 25,25, null);
			//g.drawImage(mushroomItem, screenX ,screenY, screenX+ width,screenY+height, 0, 0, 25,25, null);
			System.out.println("currentItemX : " + currentItem.X + " currentItemY : " + currentItem.Y);
		}
		
	}
	public void screenProcess() {
		if(MarioGame.mario.left)
			screenX += 1.99999999999999; ///2,3
		if(MarioGame.mario.right)
			screenX -= 1.3;
		
		
	}
	public void move() {
		int i;
		
		
		if(this.direction == 1)
			this.currentItem.X += speed;
		else
			this.currentItem.X -= speed;
			
		
		for (i = 0; i < Blocks.currentBlocks.size(); i++) {

			currentBlock = Blocks.currentBlocks.get(i); // 하나씩 블록 꺼낸다
			if(currentBlock.exist) {
				//System.out.println("blockX : " + currentBlock.x + " blockY : " + currentBlock.y);
				if(this.currentItem.X >=  currentBlock.x && this.currentItem.X <=  currentBlock.x +  currentBlock.blcokWidth) { //우선 현재 블럭의 영역에 있을 때
					if(!(this.currentItem.X-1 >=  currentBlock.x && this.currentItem.X+1 <=  currentBlock.x +  22)) {
						falling = new itemFalling();
						falling.start();
					}
						//this.currentItem.Y =  MarioGame.SCREEN_HEIGHT -70 ;//47 
					//if(!(this.currentItem.X >=  currentBlock.x && this.currentItem.X <=  currentBlock.x +  currentBlock.blcokWidth))
						
					
					if(this.currentItem.X + width == currentBlock.x && currentBlock.y +5 == currentItem.Y) { // 블록의 Y는 50이고 버섯의 Y는 45라서 +5
						if(direction == 1) direction = -1;
						else direction = 1;
					}
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

			
			System.out.println("폴링 실행");
			while (true) {

				
				
				while (currentItem.Y < MarioGame.SCREEN_HEIGHT -70) {
					currentItem.Y += 1;
					
					
					
				}
				
				try { // 위로 솟구치는 것을 천천히
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
	}
	
	public void setIsEvent(boolean isEvent) {
		this.isEvent = isEvent;
	}
	public void setIsMove(boolean isMove) {
		this.isMove = isMove;
	}
}
