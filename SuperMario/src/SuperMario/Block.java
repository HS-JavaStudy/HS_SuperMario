package SuperMario;

class Block {
	boolean broken;
	boolean item;
	boolean exist;

	static int blcok_Width = 15;
	static int block_Height = 15;

	public int x, y;
	public int state;
	

	public Block() {
	};

	public Block(int x, int y, int state) {
		this.x = x;
		this.y = y;
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
}