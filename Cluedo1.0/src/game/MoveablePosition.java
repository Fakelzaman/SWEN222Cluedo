package game;

public class MoveablePosition extends Position {

	public Player p = null;
	
	
	
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
