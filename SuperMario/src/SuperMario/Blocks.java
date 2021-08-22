package SuperMario;

import java.util.ArrayList;

public class Blocks extends Thread {

	// 블록들의 상태, 그리기 등 블록의 모든것에 관한 클래스 // 이 클래스 위치 애매, 처음에 따로 클래스 생성했다가 마리오 좌표를 얻을 수
	// 없어 여기에 만듦.

	Mario mario = MarioGame.mario;
	private ArrayList<Block> blocks; // 미리 블럭의 좌표와 상태를 저장해둔 block 리스트
	static public ArrayList<Block> currentBlocks; // 스크린 상에서 일정 범위안에 들어 실시간으로 그려져야 할 블록 리스트

	public static int blockYsize = 50;
	public static int blockXsize = 20;
	public Block currentBlock = new Block(); // 현재 블록
	static public item Item = MarioGame.Item;

	public Blocks() {

		blocks = new ArrayList<Block>();
		blocks.add(new Block(522, 413, 3)); // 테스트용 블록 좌표
		// blocks.add(new Block(542, 413, 3));
		// blocks.add(new Block(562, 413, 3));
		// blocks.add(new Block(584, 413, 3));
		blocks.add(new Block(592, 413, 3));
		blocks.add(new Block(612, 413, 3));
		blocks.add(new Block(650, 613, 3));
//		blocks.add(new Block(652, 413, 3));
		blocks.add(new Block(672, 413, 3));
//		 blocks.add( new Block(210, 500, 3));
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

	public void blockProcess() {
		int i;
		for (i = 0; i < blocks.size(); i++) {
			currentBlock = blocks.get(i);
			if (currentBlock.x > MarioGame.realX - MarioGame.SCREEN_WIDTH / 5
					&& currentBlock.x < MarioGame.realX + MarioGame.SCREEN_WIDTH / 5) // 미리 구현해둔 블럭배열에서 일정 범위 안에 있는 블럭을
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
				if (MarioGame.mario.marioY == currentBlock.y
						- MarioGame.mario.marioHeight /*
														 * && MarioGame.realX >= currentBlock.x && MarioGame.realX <=
														 * currentBlock.x + Blocks.blockXsize
														 */) {
					// System.out.println("blocking4 marioY = "+ MarioGame.mario.marioY +
					// "currentBlock "+ i + "= "+ currentBlock.x);
					MarioGame.mario.setBlocking4(true);
					System.out.println("blocking4 true");
				}

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
						// System.out.println(">>>>>>>>>4444");
					}

				}

			}
			if (currentBlock.item) {
				if (MarioGame.realX >= currentBlock.x // 7은 마리오 넓이
						&& MarioGame.realX + 12 <= currentBlock.x + blockXsize)
					if (MarioGame.mario.marioY <= currentBlock.y + blockYsize) {
						//Item = new item();					
						if (MarioGame.mario.marioY <= currentBlock.y + blockYsize) {
							 MarioGame.Item.mushroomEvent(currentBlock);
							if (MarioGame.Item.getState() == Thread.State.NEW)
								 MarioGame.Item.start();
							else
								MarioGame.Item.mushroomEvent(currentBlock);
								System.out.println("아이템아이템아이템");
						}
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
		default:
			break;
		}

	}

}
