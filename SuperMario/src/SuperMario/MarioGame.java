package SuperMario;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
public class MarioGame extends JFrame{


	public static final int SCREEN_WIDTH = 816;
    public static final int SCREEN_HEIGHT = 678;
    
    private Mario mario = new Mario();
    public MarioGame() {
    	
    	setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    	setTitle("Super Mario!");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        addKeyListener(new KeyListener());
        gameStart();
    }
    
    void init() {
    	
    }
    private void gameStart() {
       
        Timer loadingTimer = new Timer();
        TimerTask loadingTask = new TimerTask() {
            @Override
            public void run() {
            	 mario.start();
            }
        };
        loadingTimer.schedule(loadingTask, 3000);
    }
 

    public void paint(Graphics g) {
        
        screenDraw(g);
        
    }

    public void screenDraw(Graphics g) {
       
        mario.gameDraw(g);
      
        this.repaint();
    }

    class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    mario.setUp(true);
                    break;
                case KeyEvent.VK_DOWN:
                	mario.setDown(true);
                    break;
                case KeyEvent.VK_LEFT:
                	mario.setLeft(true);
                    break;
                case KeyEvent.VK_RIGHT:
                	mario.setRight(true);
                    break;
                case KeyEvent.VK_R:
                    if (mario.isOver()) 
                    break;
                case KeyEvent.VK_ENTER:
                    
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                	mario.setUp(false);
                    break;
                case KeyEvent.VK_DOWN:
                	mario.setDown(false);
                    break;
                case KeyEvent.VK_LEFT:
                	mario.setLeft(false);
                    break;
                case KeyEvent.VK_RIGHT:
                	mario.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                	//mario.setShooting(false);
                    break;
            }
        }
    }

    
}
