package SuperMario;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.math.*;

public class Monster extends Thread {

	Mario Mario = MarioGame.mario;
	private ArrayList<Goomba> CurrentGoomba = new ArrayList<Goomba>();; // 굼바 생성을 위한 좌표
	public static int MonsterYSize = 70; // 몬스터 X 크기
	public static int MonsterXSize = 70; // 몬스터 Y 크기
	public Goomba Goomba;
	private Image GoombaImage = new ImageIcon("src/images/GoombaLeft.png").getImage();
	int MonsterCount = 0;
	public void run() {

		while (true) {
			/* 마리오 realx 좌표로 생성하게했는데 a == 1000 이런식으로 하면 소환이 안돼서 범위값으로 지정함 */
			if (MarioGame.realX >= 450 && MarioGame.realX <= 800 && MonsterCount == 0) {
				MonsterCreate(Mario.marioX + MarioGame.SCREEN_WIDTH/2 , 555, MarioGame.realX + MarioGame.SCREEN_WIDTH/2);
				MonsterCount++;
			}
			if (MarioGame.realX >= 550 && MarioGame.realX <= 1000 && MonsterCount == 1) {
				MonsterCreate(Mario.marioX + MarioGame.SCREEN_WIDTH/2 , 555, MarioGame.realX + MarioGame.SCREEN_WIDTH/2);
				MonsterCount++;
			}
			if (MarioGame.realX >= 650 && MarioGame.realX <= 1100 && MonsterCount == 2) {
				MonsterCreate(Mario.marioX + MarioGame.SCREEN_WIDTH/2 , 555, MarioGame.realX + MarioGame.SCREEN_WIDTH/2);
				MonsterCount++;
			}

			try {
				Thread.sleep(20); // 스레드가 잠을 자는 시간. 잠을 자는 동안 catch 블럭 실행한다
				MonsterMoveProcess();
				MonsterEventProcess();
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
	}

	public void MonsterCreate(int x, int y, int MonsterRealX) { // 몬스터 생성 X 는 X축 값 Y 는 Y 축 값
		this.CurrentGoomba.add(new Goomba(x, y, MonsterRealX)); // 몬스터 생성 좌표
	}

	public void MonsterMoveProcess() {
		// for문 안에있는 굼바가 객체 안에 Exist == True 일때 실행
		for (int i = 0; i < this.CurrentGoomba.size() && this.CurrentGoomba.get(i).Exist == true; i++) {
			if (this.CurrentGoomba.get(i).Exist == false) {
				CurrentGoomba.remove(i);
				continue; // 굼바가 죽었을때 아래 연산을 안하게만들고 어레이 리스트에서 삭제 하여 프로세스 처리를 조금 감소시킴
			}
			System.out.println(MarioGame.realX);
			System.out.println("Goomba RealX         " + this.CurrentGoomba.get(i).MonsterRealX + "          Goomba printX        " + CurrentGoomba.get(i).MonsterX );
			if (this.CurrentGoomba.get(i).Direction == true) { //굼바 방향 왼쪽이동
				if (Mario.right == true) { // 마리오 오른쪽으로 움직일때 print 굼바 좌표 realx 굼바 좌표
					this.CurrentGoomba.get(i).MonsterRealX -= 2; 
					this.CurrentGoomba.get(i).MonsterX -= 8;
				} else if (Mario.left == true) { //마리오 왼쪽으로 움직일때 print 굼바 좌표 realx 굼바 좌표
					this.CurrentGoomba.get(i).MonsterRealX -= 2; 
					this.CurrentGoomba.get(i).MonsterX += 4;
				} else { //마리오 가만히 있을때 print 굼바 좌표 realx 굼바 좌표
					this.CurrentGoomba.get(i).MonsterRealX -= 2;
					this.CurrentGoomba.get(i).MonsterX -= 2;
				}
			} 
			else { //굼바 방향 오른쪽이동
				if (Mario.right == true) { // 마리오 오른쪽으로 움직일때  print 굼바 좌표 realx 굼바 좌표
					this.CurrentGoomba.get(i).MonsterRealX += 2; 
					this.CurrentGoomba.get(i).MonsterX -= 4;
				} else if (Mario.left == true) { //마리오 왼쪽으로 움직일때 print 굼바 좌표 realx 굼바 좌표
					this.CurrentGoomba.get(i).MonsterRealX += 2; 
					this.CurrentGoomba.get(i).MonsterX += 8;
				} else { //마리오 가만히 있을때 print 굼바 좌표 realx 굼바 좌표
					this.CurrentGoomba.get(i).MonsterRealX += 2;
					this.CurrentGoomba.get(i).MonsterX += 2;
				}
			}
		}
	}

	
	public void MonsterEventProcess() {
		for (int i = 0; i < this.CurrentGoomba.size() ; i++) {
			if(this.CurrentGoomba.get(i).Exist == false) {
				continue;
			}
			if (Mario.marioX >= CurrentGoomba.get(i).MonsterX
					&& Mario.marioX <= CurrentGoomba.get(i).MonsterX + MonsterXSize
					&& Mario.marioY-CurrentGoomba.get(i).MonsterY <= MonsterYSize) {
				System.out.print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); // 마리오랑 굼바랑 붙딛혔을때 마리오 죽음
				CurrentGoomba.get(i).Conflict = true; // 클래스 Goomba에 있는 Conflict 가 트루이면 마리오 죽음 처리
				MonsterInit();
				break;
			}
			if (Mario.marioX - CurrentGoomba.get(i).MonsterX <= MonsterXSize
					&& Mario.marioX - CurrentGoomba.get(i).MonsterX >= 0
					&& CurrentGoomba.get(i).MonsterY - Mario.marioY >= MonsterYSize
					&&CurrentGoomba.get(i).MonsterY - Mario.marioY <= MonsterYSize+4) {
					this.CurrentGoomba.get(i).Exist = false; // 몬스터 죽이기
			}
			
			
			if (CurrentGoomba.get(i).MonsterRealX ==720) {
				CurrentGoomba.get(i).Direction = !CurrentGoomba.get(i).Direction; 
			}
			if (CurrentGoomba.get(i).MonsterRealX ==880) {
				CurrentGoomba.get(i).Direction = !CurrentGoomba.get(i).Direction; 
			}
			if (CurrentGoomba.get(i).MonsterRealX ==1008) {
				CurrentGoomba.get(i).Direction = !CurrentGoomba.get(i).Direction; 
			}
		}
	}
	
	
	public void MonsterInit() { // 모든 값을 재 설정 하는 함수 마리오가 죽었을때 다시 시작하는 것 처럼 다시 초기화
		this.MonsterCount = 0;
		this.CurrentGoomba.clear();
	}

	
	
	
	public void MonsterDraw(Graphics g) {

		if (MarioGame.mario.cnt % 45 < 23) {
			for (int i = 0; i < this.CurrentGoomba.size() && this.CurrentGoomba.get(i).Exist == true; i++) {
				if(this.CurrentGoomba.get(i).Exist == false) 
					continue;
				
				GoombaImage = new ImageIcon("src/images/GoombaLeft.png").getImage();
				g.drawImage(GoombaImage, this.CurrentGoomba.get(i).MonsterX, this.CurrentGoomba.get(i).MonsterY,
						MonsterXSize, MonsterYSize, null);
			}
		} else {
			for (int i = 0; i < this.CurrentGoomba.size() && this.CurrentGoomba.get(i).Exist == true; i++) {
				if(this.CurrentGoomba.get(i).Exist == false) 
					continue;
				GoombaImage = new ImageIcon("src/images/GoombaRight.png").getImage();
				g.drawImage(GoombaImage, this.CurrentGoomba.get(i).MonsterX, this.CurrentGoomba.get(i).MonsterY,
						MonsterXSize, MonsterYSize, null);
			}
		}
	}

	public void gameDraw(Graphics g) {
		MonsterDraw(g); // MarioGame 클라스의 paint()함수 안에 있는 gameDraw() 함수
	}

}
