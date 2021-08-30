package SuperMario;

class Goomba {
	int MonsterX; // 그려지는 몬스터 X 
	int MonsterY; // 그려지는 몬스터 Y
	boolean Exist; // 몬스터 존재 여부 True 죽거나 넘어가면 False;
	boolean Conflict; // 마리오랑 몬스터랑 출동 했을때 True; 평상시는 False;
	int MonsterRealX; //몬스터 실제 좌표
	boolean Falling;  //버섯도 떨어지는걸 구현해야함
	boolean Direction;  // 몬스터 방향 True 면 왼쪽 False 면 오른쪽
	boolean MarioKillMonster;
	
	public Goomba(int x, int y ,int MonsterRealX) { //몬스터 생성자
		this.MonsterX = x;
		this.MonsterY = y;
		this.MonsterRealX = MonsterRealX;
		this.Exist = true; 
		this.Conflict = false;
		this.Falling = false;
		this.Direction = true;
		this.MarioKillMonster = false;
	}
	
	public void GoombaDie() {
		this.Exist = false;
	}
	
	
}
