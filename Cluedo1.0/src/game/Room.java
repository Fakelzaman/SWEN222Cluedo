package game;

import java.awt.List;
import java.util.ArrayList;

public class Room {
	
	public String name;
	public ArrayList<Position> pos;
	public Room corner;
	public ArrayList players = new ArrayList<Player>();
	public ArrayList weapons = new ArrayList<Weapon>();
	
	public Room(String name, ArrayList<Position> pos){
		this.name = name;
		this.pos = pos;
		this.corner = corner;
		
	}
	
	public void addCornerRoom(Room r){
		this.corner = r;
	}
	//Name
	//Positions occupied
	//Corner passage
}
