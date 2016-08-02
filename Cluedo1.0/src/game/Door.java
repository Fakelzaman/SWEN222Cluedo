package game;

public class Door extends Position {
	
	
	protected Room room;
	
	public Door(int x, int y) {
		super(x, y);
		
	}

	@Override
	public void setPlayer(Player p) {
		// TODO Auto-generated method stub
		this.p = p;
	}

	@Override
	public Player getPlayer() {
		
		return p;
	}
	
	public void setRoom(Room room){
		this.room=room;
	}
	
	public Room room(){
		return room;
	}

}
