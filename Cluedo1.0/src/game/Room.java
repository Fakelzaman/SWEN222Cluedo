package game;

import java.awt.List;
import java.util.ArrayList;

public class Room {
	
	public String name;
	public ArrayList pos;
	public Boolean corner = false;
	public List players;
	public List weapons;
	
	public Room(String name, ArrayList pos, Boolean corner){
		this.name = name;
		this.pos = pos;
		this.corner = corner;
		
	}
	//Name
	//Positions occupied
	//Corner passage
}
