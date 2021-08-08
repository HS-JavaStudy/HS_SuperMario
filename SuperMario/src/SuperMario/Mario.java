package SuperMario;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Mario extends Thread{

	private int delay = 20;
	private long pretime;
	private int cnt;
	private int score;
	
	boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean isOver;
	
	private Image smallRightMario = new ImageIcon("src/images/smallRightMario.png").getImage();
	private Image smallLefttMario = new ImageIcon("src/images/smallLefttMario.png").getImage();
	private Image smallJumpRightMario = new ImageIcon("src/images/smallJumpRightMario.png").getImage();
	private Image smallJumpLefttMario = new ImageIcon("src/images/smallJumpLefttMario.png").getImage();

	private int marioX, marioY;
	private int marioWidth = 35;
	private int marioHeight = 50;
	private int marioSpeed = 10;
	private int marioHp = 30;



	public void run() {

		reset();
		while (true) {
			while (!isOver) {
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						keyProcess();
						//playerAttackProcess();
					
						cnt++;
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
	
	 private void keyProcess() {
	        if (up && marioY - marioSpeed > 0) marioY -= marioSpeed;
	        if (down && marioY + marioHeight + marioSpeed < MarioGame.SCREEN_HEIGHT) marioY += marioSpeed;
	        if (left && marioX - marioSpeed > 0) marioX -= marioSpeed;
	        if (right && marioX + marioWidth + marioSpeed < MarioGame.SCREEN_WIDTH) marioX += marioSpeed;
	      
	    }
	 
	  public void gameDraw(Graphics g) {
	        playerDraw(g);
	       // infoDraw(g);
	    }

	   public void reset() {
	        isOver = false;
	        cnt = 0;
	        score = 0;
	        marioX = 10;
	        marioY = (MarioGame.SCREEN_HEIGHT - marioHeight) / 2;
	    }

		public void playerDraw(Graphics g) {
			g.drawImage(smallRightMario, marioX, marioY,
					marioX + marioWidth, marioY + marioHeight,
					0, 0, 1280, 1280,null);
			
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

	   
}
