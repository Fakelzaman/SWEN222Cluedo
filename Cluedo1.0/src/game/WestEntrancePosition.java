package game;

public class WestEntrancePosition extends Door {

	
	protected int x;
	protected int y;
	
	public Room room;
	
	public WestEntrancePosition(int x, int y){
		super(x,y);
	}
	
	public String toString(){
		return "w";
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
