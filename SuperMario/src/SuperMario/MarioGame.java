package SuperMario;
import java.awt.*;
import javax.swing.*;

import SuperMario.Mario.MarioJump;
import SuperMario.Mario.OperationTread;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class MarioGame extends JFrame{

   // 초기 이미지
   private Image introBackground = new ImageIcon("src/images/introBackground.jpg").getImage();
   private Image titleImage = new ImageIcon("src/images/title.png").getImage();
   static public Image mapImage = new ImageIcon("src/images/map.png").getImage();
   // 사이즈 설정 이미지
   private Image resizeIntroBackground= introBackground.getScaledInstance(MarioGame.SCREEN_WIDTH, MarioGame.SCREEN_HEIGHT, Image.SCALE_SMOOTH);
   private Image resizeTitleImage = titleImage.getScaledInstance(MarioGame.SCREEN_WIDTH/2, MarioGame.SCREEN_HEIGHT/4, Image.SCALE_SMOOTH);
   
   // Section2: 게임 배경음악 설정
   Music backgroundMusic = new Music("backgroundMusic.mp3",true); // 배경음악 객체 생성
         
         
   private Font font;
   
   private Image bufferImage;   //더블 버퍼링을 위한 전체 이미지를 담는 변수
   private Graphics screenGraphic;

   
   static boolean isMainScreen;
   static boolean isLoadingScreen;
   static boolean isGameScreen;
   
   static public int realX;
   public static final int SCREEN_WIDTH = 816;
    public static final int SCREEN_HEIGHT = 678;
    
   
    private Blocks blocks = new Blocks();
    public  Mario mario = new Mario();
   
    public MarioGame() {
       // 게임을 출력할 창 지정
       init();
       realX = 0;
       setSize(SCREEN_WIDTH, SCREEN_HEIGHT); 
       setTitle("Super Mario!");
       setLocation(200,0);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(null);
        addKeyListener(new KeyListener()); // 마우스 이벤트 추가
        //gameStart(); // 바로 게임 스타트
        backgroundMusic.start(); // 배경음악 시작
    }
    
    void init() {
       isMainScreen = true;
       isLoadingScreen= false;
       isGameScreen = false;
    }
    private void gameStart() {
    	
    	Timer loadingTimer = new Timer();
        TimerTask loadingTask = new TimerTask() {
        	
            @Override
            public void run() { //스레드 코드로서 JVM에 의해 호출. 반드시 오버라이딩 하여 스레드 코드를 작성하여야 한다
                mario.start(); //Mario mario 스레드 실행을 시작하도록 요청
                blocks.start();
                
       		 
            } 
        };
        loadingTimer.schedule(loadingTask, 1000);
    }
 

    public void paint(Graphics g) { // JVM에 의해 호출되는 그리기 함수. repaint() 요청시 이 함수 실행
       //더블 버퍼링 : 전체 화면에 맞는 이미지를 매순간 마다 생성해서 원하는 컴포넌트만을 출력하는 방법 
       // 그냥 이미지 객체로 게임을 만들면 버퍼링이 심해 더블 버퍼링 기법을 쓴다고 한다
        bufferImage = createImage(SCREEN_WIDTH,SCREEN_HEIGHT); 
         screenGraphic = bufferImage.getGraphics();
         screenDraw(screenGraphic);
         g.drawImage(bufferImage, 0, 0, null);
              
    }

	public void screenDraw(Graphics g) {

		if (isMainScreen) {

			g.drawImage(resizeIntroBackground, 0, 0, null);
			g.drawImage(resizeTitleImage, 210, 175, null);

			font = new Font("Monospaced", Font.BOLD, 22); // 폰트 설정
			g.setFont(font);
			g.setColor(Color.WHITE); // 글씨 색 설정

			g.drawString("< Press Enter to Start Game >", 242, 382);

		}
		
       else if(isGameScreen) {
    	  //224 : 맵 높이
          super.paint(g);
         
          // 값 임의로 넣은거라서 바꿔도 됨  
          // 그릴 사이즈는 SCREEN_WIDTH X SCREEN_HEIGHT 사이즈
          // realX 는 실제 이동거리를 나타냄
          //마리오가 일정 x좌표에 도달하면(정중앙) 마리오의 x좌표는 스피드만큼 감소시키고, realX는 스피드만큼 계속 증가    
          // 그림이 끝나는 지점이 문제..
         g.drawImage(mapImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, //맵 
        		  mario.marioX + realX , 0, mario.marioX + realX + SCREEN_WIDTH/3, mapImage.getHeight(rootPane), null);
        // System.out.println("marioX : " + mario.marioX + " realX : " + realX + " marioX + realX = "+ ((int)mario.marioX + (int)realX));
          mario.gameDraw(g); // Mario 클래스의 gameDraw() 함수 호출 - 캐릭터, 몬스터 등 그리기
          
          //isGameScreen = false;
          
       }
      
        this.repaint();   // 그래픽의 상태를 바꿨으니 화면에 구현되도록 rapaint 요청. 즉 종료될 때까지 매순간 다시 그린다
    }

    //키 이벤트 Adapter를 상속받아 필요한 것만 구현
    class KeyListener extends KeyAdapter {
       
        public void keyPressed(KeyEvent e) { // 키를 눌렀을 때
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    mario.setUp(true); // Mario.setUp == true;
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
                    if(isMainScreen) {                
                    isMainScreen =false;
                    isGameScreen = true;
                    gameStart();
                    }
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }
            
         
                       
          

        public void keyReleased(KeyEvent e) { //키를 뗴었을 때
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                   mario.setUp(false); // Mario.setUp == flase;
                   //MarioJump.interrupted();
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
               
                   
                   
            }
        }
    }
     class Blocks extends Thread {

    	private ArrayList<Block> blocks;
    	private ArrayList<Block> currentBlocks;
    	
    	
    	private Block currenBlock = new Block();
    	
    	public Blocks() {
    		 blocks = new ArrayList<Block>();
    		 blocks.add( new Block(190, 459, 3));
    		 blocks.add( new Block(210, 500, 3));
    		 currentBlocks = new  ArrayList<Block>();
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
    	
    	
    		public void blockDraw(Graphics g) {
    			
    		}
    		public void blockProcess() {
    			int i;
    			for(i=0; i< blocks.size(); i++) {
    				currenBlock = blocks.get(i);
    				if(currenBlock.x >  (MarioGame.realX + mario.marioX) - MarioGame.SCREEN_WIDTH &&
    						currenBlock.x < MarioGame.realX + mario.marioX) //일정 범위 안 블럭
    				{
    					if(!currentBlocks.contains(currenBlock))
    					{
    						blockActive(currenBlock);
    						currentBlocks.add(currenBlock);
        					System.out.println(">>>>>>>>>1111");
    					}
    					
    					
    					
    				}
					else {
						if (!currentBlocks.isEmpty())
							if (currentBlocks.contains(currenBlock))
								 currentBlocks.remove(currenBlock);
								//System.out.println(">>>>>>>>>222222");
					}
    			}
    			
    			for(i =0; i< currentBlocks.size(); i++) {
    				currenBlock = currentBlocks.get(i);
    				if(currenBlock.exist) {
    					if(mario.marioX + realX >= currenBlock.x 
    							&&  mario.marioX + realX <= currenBlock.x + 15 &&
    							currenBlock.y >= mario.marioY && currenBlock.y + 15>= mario.marioY ) 
    							{
    						
    							
    						mario.setBlcoking(true);
    						System.out.println(">>>>>>>>>33333");
    					}
    					else
    						{
    							mario.setBlcoking(false);
    							//System.out.println(">>>>>>>>>4444");
    						}
    				}
    			}
    		}
    		public void blockActive(Block block) {
    			
    			switch(block.state) {
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
    			default :
    				break;
    			}
    			
    		}
    		
    	

    }

 	
    
}