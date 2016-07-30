package game;

public class Weapon {

	//Name
	//Position
	
	String name;
	Position pos;
	
	public Weapon(String name){
		this.name = name;
		
	}
	
	public String getName(){
		return name;
	}
	
	public void movePos(Position pos){
		this.pos=pos;
	}
	
	
}
