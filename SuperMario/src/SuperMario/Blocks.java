package SuperMario;

import java.util.ArrayList;

public class Blocks extends Thread {

	// ���ϵ��� ����, �׸��� �� ������ ���Ϳ� ���� Ŭ���� // �� Ŭ���� ��ġ �ָ�, ó���� ���� Ŭ���� �����ߴٰ� ������ ��ǥ�� ���� ��
	// ���� ���⿡ ����.

		Mario mario = MarioGame.mario;
		private ArrayList<Block> blocks; // �̸� ������ ��ǥ�� ���¸� �����ص� block ����Ʈ
		public ArrayList<Block> currentBlocks; // ��ũ�� �󿡼� ���� �����ȿ� ��� �ǽð����� �׷����� �� ���� ����Ʈ

		public static int blockYsize = 50;
		public static int blockXsize = 10;
		private Block currenBlock = new Block(); // ���� ����

		public Blocks() {
			blocks = new ArrayList<Block>();
			blocks.add(new Block(522, 413, 3)); // �׽�Ʈ�� ���� ��ǥ
			// blocks.add( new Block(210, 500, 3));
			currentBlocks = new ArrayList<Block>();
		}

		public void run() {

			while (true) {
				blockProcess();
				try {
					Thread.sleep(50); // �����尡 ���� �ڴ� �ð�. ���� �ڴ� ���� catch ���� �����Ѵ�

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

//		public void blockDraw(Graphics g) {
//			// ����ִ� ������� �簢�� �׷����� ������ �� ���� �׷��ôµ� ��ǥ�� ������ ���� �׸��°� �ʹ� ������� ����
//			/// ��ũ������ ��ǥ�� ������ ��ǥ�� �ƿ� Ʋ���� ū�� ...
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
						&& currenBlock.x < MarioGame.realX + MarioGame.SCREEN_WIDTH / 5) // �̸� �����ص� �����迭���� ���� ���� �ȿ� �ִ� ������ ������
				{
//    					System.out.println((MarioGame.realX + mario.marioX) - MarioGame.SCREEN_WIDTH/2 +"  " +
//    					(int)( MarioGame.realX + mario.marioX) +(int)( MarioGame.SCREEN_WIDTH/2));

					if (!currentBlocks.contains(currenBlock)) // ��������� ����Ʈ�� ���Ե��� �ʾҴٸ�
					{
						blockActive(currenBlock); // ���� ������ Ȱ��ȭ
						currentBlocks.add(currenBlock); // ������� ����Ʈ�� �߰�
						System.out.println(">>>>>>>>>1111");
						System.out.println("marioX + realX = " + ((int) mario.marioX + (int) MarioGame.realX)
								+ "marioY = " + mario.marioY);
					}

				} else {
					if (!currentBlocks.isEmpty()) // ���� �ȿ� ���ԵȰ� �ƴ϶��
						if (currentBlocks.contains(currenBlock)) // ���� ���� ������ ����Ʈ�� �ִ�
						{
							currentBlocks.remove(currenBlock); // �� ���� �����
							System.out.println("dddd  realX = " + MarioGame.realX + "marioY = " + mario.marioY);
						}

				}
			}

			// ���� �󿡼� ����ִ� ������ �������� �ൿ ó��
			for (i = 0; i < currentBlocks.size(); i++) {

				currenBlock = currentBlocks.get(i); // �ϳ��� ���� ������

				if (currenBlock.exist) {// �����Ѵٸ� (���� ������ ��������)
					//currenBlock�� ������ ħ���ҽ� ++++ �׷�����ǥ�� ������ ��ǥ�� Ʋ�� �����ʿ�... �������ϰԴ� ����( ù �̺�Ʈ ���� �ֺ�)
					if(MarioGame.realX + mario.marioWidth >= currenBlock.x)//&& MarioGame.realX == currenBlock.x +blockXsize)
	                     //&&  currenBlock.y + blockYsize > mario.marioY  )  // ���� �� ����
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