package SuperMario;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Blocks extends Thread {

	// 블록들의 상태, 그리기 등 블록의 모든것에 관한 클래스 // 이 클래스 위치 애매, 처음에 따로 클래스 생성했다가 마리오 좌표를 얻을 수
	// 없어 여기에 만듦.

	Mario mario = MarioGame.mario;
	private ArrayList<Block> blocks; // 미리 블럭의 좌표와 상태를 저장해둔 block 리스트
	static public ArrayList<Block> currentBlocks; // 스크린 상에서 일정 범위안에 들어 실시간으로 그려져야 할 블록 리스트
	
	public static int blockYsize = 50;
	public static int blockXsize = 50;
	public Block currentBlock = new Block(); // 현재 블록
	static public item Item = MarioGame.Item;

	public Blocks() {

		blocks = new ArrayList<Block>();

		blocks.add(new Block(762, 413, 3)); // 테스트용 블록 좌표
	
		blocks.add(new Block(946, 413, 2));
		blocks.add(new Block(996, 413, 2));
		blocks.add(new Block(1046, 413, 2));
		blocks.add(new Block(1046, 220, 2));
		
		
		blocks.add(new Block(1096, 413, 2));
		blocks.add(new Block(1146, 413, 2));
		
        // 첫번째 파이프
		blocks.add(new Block(1336, 509, 1));
		
		//두번째 파이프
		blocks.add(new Block(1826, 460, 1));
		
		//세번째 파이프
		blocks.add(new Block(2210, 413, 1));
		
		// 네번째 파이프
		blocks.add(new Block(2738, 413, 1));
		
		
		blocks.add(new Block(3074, 364, 3));
		
		blocks.add(new Block(3698, 412, 2));
		blocks.add(new Block(3740, 412, 2));
		blocks.add(new Block(3790, 412, 2));
		
		
		blocks.add(new Block(4508, 412, 2));
		
		blocks.add(new Block(4802, 412, 1));
		
		//물음표 블럭 3개
		blocks.add(new Block(5084, 412, 3));
		blocks.add(new Block(5234, 412, 3));
		blocks.add(new Block(5384, 412, 3));
		
		blocks.add(new Block(5662, 412, 2));
		
		blocks.add(new Block(6194, 412, 1));
	

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

//		public void blockDraw(Graphics g) {
//			// 살아있는 블럭대로 사각형 그려보면 수월할 것 같아 그려봤는데 좌표를 마리오 따라 그리는게 너무 어려워서 포기
//			/// 스크린상의 좌표와 마리오 좌표가 아예 틀려서 큰일 ...
//			int i;
//			Block b = new Block();
//			for (i = 0; i < currentBlocks.size(); i++) {
//				b = currentBlocks.get(i);
//
//				g.drawRect((b.x - (realX * 2)) + 400, b.y, blockSize, blockSize);
//
//			}
//
//		}
	

	public void isOnBlock() {
		int i;
		
		for (i = 0; i < currentBlocks.size(); i++) {

			currentBlock = currentBlocks.get(i);
			
			if (currentBlock.exist) {
				
				if(currentBlock.state == 1)
					Blocks.blockXsize = 80;
				else
					Blocks.blockXsize = 50;
				
				if (MarioGame.mario.marioY == currentBlock.y
						- MarioGame.mario.marioHeight && MarioGame.realX + 21 >= currentBlock.x && MarioGame.realX <=
		            currentBlock.x + Blocks.blockXsize) {
					MarioGame.mario.setBlocking4(true);
					System.out.println("blocking4 true");
					return;
				}
				
			}
		}
		System.out.println("blocking4 false");
		//Mario.MarioJump.basicY = 560;
		MarioGame.mario.setBlocking4(false);
	}
	


	
	
	public void blockProcess() {
		int i;
		for (i = 0; i < blocks.size(); i++) {
			currentBlock = blocks.get(i);
			if (currentBlock.x > MarioGame.realX - MarioGame.SCREEN_WIDTH*2 // 범위 넓힘
					&& currentBlock.x < MarioGame.realX + MarioGame.SCREEN_WIDTH*2) // 미리 구현해둔 블럭배열에서 일정 범위 안에 있는 블럭을
																						// 꺼내기
			{

				if (!currentBlocks.contains(currentBlock)) // 현재블럭들 리스트에 포함되지 않았다면
				{
					blockActive(currentBlock); // 현재 블럭을 활성화
					currentBlocks.add(currentBlock); // 현재블럭 리스트에 추가

				}

			} else {
				if (!currentBlocks.isEmpty()) // 범위 안에 포함된게 아니라면
					if (currentBlocks.contains(currentBlock)) // 만약 현재 블럭들 리스트에 있다

						currentBlocks.remove(currentBlock); // 그 블럭 지운다

			}
		}

		// 게임 상에서 살아있는 블럭과 마리오의 행동 처리
		for (i = 0; i < currentBlocks.size(); i++) {

			currentBlock = currentBlocks.get(i); // 하나씩 블록 꺼낸다

			if (currentBlock.exist) {// 존재한다면 (깨진 블럭과 구별위해)
				// currenBlock의 공간을 침범할시 ++++ 그래픽좌표와 마리오 좌표가 틀려 수정필요... 점프못하게는 가능( 첫 이벤트 블록 주변)
				// System.out.println("current block i = " + i);
				// System.out.println("current blockY = " + currentBlock.y);
				// System.out.println("MarioGame.mario.marioY = " + MarioGame.mario.marioY);
				

				if (MarioGame.realX + 7 >= currentBlock.x // 7은 마리오 넓이
						&& MarioGame.realX <= currentBlock.x + blockXsize) {
				
					if (MarioGame.mario.marioY + 1 <= currentBlock.y + blockYsize) {

						if (MarioGame.mario.marioY == currentBlock.y + blockYsize) { // 블럭 아래에서 점프 막히는 기능
							mario.setBlocking3(true);
							System.out.println(">>>>>>>>>33333");
						}
						if (MarioGame.mario.marioY < currentBlock.y + blockYsize
								&& MarioGame.mario.marioY + MarioGame.mario.marioHeight > currentBlock.y) {
							MarioGame.mario.setBlocking1(true); // 블럭과 부딪히는 기능 양옆

							System.out.println(">>>>>>>>>1111"); // 이것만 호출되는데 돌아가긴 하는듯..?

						}

					} else {

						// System.out.println("여기 들어옴!!!!!!!!!");

						MarioGame.mario.setBlocking1(false);
						MarioGame.mario.setFalling(false);
					
						// mario.setBlcoking3(false);
						
					}

				}

			}
			if (currentBlock.item) {
				if (MarioGame.realX + MarioGame.mario.marioWidth /2>= currentBlock.x // 7은 마리오 넓이
						&& MarioGame.realX + MarioGame.mario.marioWidth /2 <= currentBlock.x + blockXsize)
					if (MarioGame.mario.marioY <= currentBlock.y + blockYsize) { //이벤트 블록 영역 안에 들어왔다					
						MarioGame.Item.mushroomEvent(currentBlock);
						currentBlock.setState(2);
						blockActive(currentBlock); 
						if (MarioGame.Item.getState() == Thread.State.NEW)
							MarioGame.Item.start();	
					}
			}
			else if(currentBlock.coin) {
				if (MarioGame.realX + MarioGame.mario.marioWidth /2>= currentBlock.x // 7은 마리오 넓이
						&& MarioGame.realX + MarioGame.mario.marioWidth /2 <= currentBlock.x + blockXsize)
					if (MarioGame.mario.marioY <= currentBlock.y + blockYsize) { //이벤트 블록 영역 안에 들어왔다					
						MarioGame.Item.coinEvent(currentBlock);
						//currentBlock.setState(2);
						//blockActive(currentBlock); 
						if (MarioGame.Item.getState() == Thread.State.NEW)
							MarioGame.Item.start();	
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
		case 4:
			block.setBroken(false);
			block.setCoin(true);
			block.setExist(true);
		default:
			break;
		}

	}

}
