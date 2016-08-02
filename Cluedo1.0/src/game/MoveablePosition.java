package game;

public class MoveablePosition extends Position {

	public Player p = null;
	
	protected int x;
	protected int y;
	
	public MoveablePosition(int x, int y){
		super(x,y);
	}
	
	public void setPlayer(Player p){
	 this.p = p;
	}
	public Player getPlayer(){
		return p;
	}
	
	public String toString(){
		return "-";
	}
	
}
