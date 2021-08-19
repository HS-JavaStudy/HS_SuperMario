package SuperMario;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class item extends Blocks{

	private int X,Y;
	item currentItem;
	//private Block block = new Block();
	Image mushroomItem = new ImageIcon("src/images/버섯아이템.png").getImage();
	//g.drawImage(mushroomItem, 100,100,130,130,0,0,25,25, null);

	
	public void mushroomEvent(Block block) {
		currentItem = new item();
		currentItem.X= block.x;
		currentItem.Y = block.y;
	}

	public void itemDraw(Graphics g) {
		
		g.drawImage(mushroomItem, X,Y, X+30,Y +30, 0, 0, 25,25, null);
	}
	
	public void itemProcess() {
		int i;
		for (i = 0; i < currentBlocks.size(); i++) {

			currentBlock = currentBlocks.get(i); // 하나씩 블록 꺼낸다
			if(currentBlock.exist) {
				
			}
			
		}
	}
}
