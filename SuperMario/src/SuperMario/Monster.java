package SuperMario;

import java.util.ArrayList;

public class Monster extends Thread {

	private int MonsterX; // 몬스터 X
	private int MonsterY; // 몬스터 Y
	private boolean Exist; // 몬스터 존재 여부 True 죽거나 넘어가면 False;
	private boolean Conflict; // 마리오랑 몬스터랑 출동 했을때 True; 평상시는 False;
	
	public Monster(int x, int y) {
		this.MonsterX = x;
		this.MonsterY = y;
		this.Exist = true; 
		this.Conflict = false;
	}

}
