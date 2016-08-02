package game;

public class EastEntrancePosition extends Door {

	protected int x;
	protected int y;
	
	protected Room room;
	
	public EastEntrancePosition(int x, int y){
		super(x,y);
		String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
		coord = alphabet[x]+y;
	}
	
	
	public String toString(){
		return "e";
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
