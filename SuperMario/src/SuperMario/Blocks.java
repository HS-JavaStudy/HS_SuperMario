package SuperMario;

import java.util.ArrayList;

public class Blocks extends Thread {

	// 블록들의 상태, 그리기 등 블록의 모든것에 관한 클래스 // 이 클래스 위치 애매, 처음에 따로 클래스 생성했다가 마리오 좌표를 얻을 수
	// 없어 여기에 만듦.

		Mario mario = MarioGame.mario;
		private ArrayList<Block> blocks; // 미리 블럭의 좌표와 상태를 저장해둔 block 리스트
		public ArrayList<Block> currentBlocks; // 스크린 상에서 일정 범위안에 들어 실시간으로 그려져야 할 블록 리스트

		public static int blockYsize = 50;
		public static int blockXsize = 10;
		Block currenBlock = new Block(); // 현재 블록

		public Blocks() {
			blocks = new ArrayList<Block>();
		    blocks.add(new Block(522, 413, 3)); // 테스트용 블록 좌표
			blocks.add( new Block(592, 413, 3));
			//System.out.println("Blocks size1! = "+ currentBlocks.size());
			currentBlocks = new ArrayList<Block>();
			System.out.println("Blocks size1! = "+ currentBlocks.size());
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
				currenBlock = blocks.get(i);
				if (currenBlock.x > MarioGame.realX - MarioGame.SCREEN_WIDTH / 5
						&& currenBlock.x < MarioGame.realX + MarioGame.SCREEN_WIDTH / 5) // 미리 구현해둔 블럭배열에서 일정 범위 안에 있는 블럭을 꺼내기
				{
//    					System.out.println((MarioGame.realX + mario.marioX) - MarioGame.SCREEN_WIDTH/2 +"  " +
//    					(int)( MarioGame.realX + mario.marioX) +(int)( MarioGame.SCREEN_WIDTH/2));

					if (!currentBlocks.contains(currenBlock)) // 현재블럭들 리스트에 포함되지 않았다면
					{
						blockActive(currenBlock); // 현재 블럭을 활성화
						currentBlocks.add(currenBlock); // 현재블럭 리스트에 추가
						//System.out.println(">>>>>>>>>1111");
						//System.out.println("marioX + realX = " + ((int) mario.marioX + (int) MarioGame.realX)
						//		+ "marioY = " + mario.marioY);
					}

				} else {
					if (!currentBlocks.isEmpty()) // 범위 안에 포함된게 아니라면
						if (currentBlocks.contains(currenBlock)) // 만약 현재 블럭들 리스트에 있다
						{
							currentBlocks.remove(currenBlock); // 그 블럭 지운다
							System.out.println("dddd  realX = " + MarioGame.realX + "marioY = " + mario.marioY);
						}

				}
			}

			// 게임 상에서 살아있는 블럭과 마리오의 행동 처리
			for (i = 0; i < currentBlocks.size(); i++) {

				currenBlock = currentBlocks.get(i); // 하나씩 블록 꺼낸다

				if (currenBlock.exist) {// 존재한다면 (깨진 블럭과 구별위해)
					//currenBlock의 공간을 침범할시 ++++ 그래픽좌표와 마리오 좌표가 틀려 수정필요... 점프못하게는 가능( 첫 이벤트 블록 주변)
					if(MarioGame.realX +5 >= currenBlock.x&& MarioGame.realX -5 <= currenBlock.x && mario.marioY >550)
	                     //currenBlock.y + blockYsize > mario.marioY &&  
	                     //currenBlock.y  < mario.marioY)  // 왼쪽 벽 막힘
	                  {
	                     mario.setBlcoking1(true);
	                     System.out.println(">>>>>>>>>11111");
	                  }
	               //else mario.setBlcoking1(false);
	               
	               else if (MarioGame.realX >= currenBlock.x &&
	                     MarioGame.realX <= currenBlock.x + blockXsize &&
	               currenBlock.y + blockYsize >= mario.marioY)
	                  {
	                  mario.setBlcoking3(true);
	                  System.out.println(">>>>>>>>>33333");
	                  System.out.println("realX : " + MarioGame.realX + " marioY : " + mario.marioY);
//	                      
	                  }
	         

	                  //mario.setBlcoking1(true);
	                     
	               
	            else 
	               {
	                  mario.setBlcoking1(false);
	                  mario.setBlcoking3(false);
	                  //System.out.println(">>>>>>>>>4444");
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
