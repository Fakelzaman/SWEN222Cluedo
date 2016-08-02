package game;

public class SouthEntrancePosition extends Door {

	
	protected int x;
	protected int y;
	
	protected String coord;
	
	public Room room;
	
	public SouthEntrancePosition(int x, int y){
		
		super(x,y);
		String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
		coord = alphabet[x]+y;
	}
	
	public String toString(){
		return "s";
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
