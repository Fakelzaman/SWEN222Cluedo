package game;

public class NorthEntrancePosition extends Door {
	
	protected int x;
	protected int y;
	
	public Room room;
	
	public NorthEntrancePosition(int x, int y){
		super(x,y);
	}
	
	public String toString(){
		return "n";
	}

	@Override
	public void setPlayer(Player p) {
		// TODO Auto-generated method stub
		this.p=p;
		
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return p;
	}
	
	public Room room(){
		return room;
	}
}
