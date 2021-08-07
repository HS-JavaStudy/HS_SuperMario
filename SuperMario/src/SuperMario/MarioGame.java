package SuperMario;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;
public class MarioGame extends JFrame{

	public static final int SCREEN_WIDTH = 816;
    public static final int SCREEN_HEIGHT = 678;
    
    public MarioGame() {
    	
    	setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    	setTitle("Super Mario!");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
    }
    
    public void paint(Graphics g) {

        screenDraw(g);
       
    }

    public void screenDraw(Graphics g) {
       // if (isMainScreen) { //¿¹½Ã
        //    g.drawImage(mainScreen, 0, 0, null);
       // }
    
        this.repaint();
    }

    class KeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
            
            }
        }

        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
               
            }
        }
    }
	
}
