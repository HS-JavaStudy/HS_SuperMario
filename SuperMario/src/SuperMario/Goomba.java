package SuperMario;

class Goomba {
	int MonsterX; // �׷����� ���� X 
	int MonsterY; // �׷����� ���� Y
	boolean Exist; // ���� ���� ���� True �װų� �Ѿ�� False;
	boolean Conflict; // �������� ���Ͷ� �⵿ ������ True; ���ô� False;
	int MonsterRealX; //���� ���� ��ǥ
	boolean Falling;  //������ �������°� �����ؾ���
	boolean Direction;  // ���� ���� True �� ���� False �� ������
	
	
	public Goomba(int x, int y ,int MonsterRealX) { //���� ������
		this.MonsterX = x;
		this.MonsterY = y;
		this.MonsterRealX = MonsterRealX;
		this.Exist = true; 
		this.Conflict = false;
		this.Falling = false;
		this.Direction = true;
	}
	
	public void GoombaDie() {
		this.Exist = false;
	}
	
	
}