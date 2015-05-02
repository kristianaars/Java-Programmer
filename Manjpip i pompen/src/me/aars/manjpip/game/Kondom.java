package me.aars.manjpip.game;

public enum Kondom {
	
	liv(),
	kondom();
	
	private Kondom() {
		
	}
	
	private int item;
	
	public void reset() {
		item = 0;
	}
	
	public void addKondomer(int i) {
		item+=i;
	}
	
	public void fjernKondomer(int i) {
		item-=i;
		if(item<0) item = 0;
	}
	
	public int getKondomer() {
		return item;
	}
}
