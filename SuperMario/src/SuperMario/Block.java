package SuperMario;

class Block {
	boolean broken;
	boolean item;
	boolean exist;
	boolean coin;

	public static int blcokWidth = 20;
	public static int blockHeight = 50;

	public int x, y;
	public int state;
	

	public Block() {
	};
	public Block(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	public Block(int x, int y, int state) {
		this.x = x;
		this.y = y;
		this.state = state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setBroken(boolean broken) {
		this.broken = broken;
	}

	public void setItem(boolean item) {
		this.item = item;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}
	public void setCoin(boolean coin) {
		this.coin = coin;
	}
}