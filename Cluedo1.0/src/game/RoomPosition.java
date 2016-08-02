package game;

public class RoomPosition extends Position {

	
	protected int x;
	protected int y;
	
	public RoomPosition(int x, int y){
		super(x,y);
	}
	
	public String toString(){
		return "R";
	}	
	 //Type of Room eg ballroom

	@Override
	public void setPlayer(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
}
