package game;

import java.awt.List;
import java.util.ArrayList;

import assets.Cards;

public class Player {
	
	public Boolean inPlay = false; 
	public String name;
	public Board game;
	public Position CurrentPos;
	public ArrayList<Cards> hand = new ArrayList<assets.Cards>();
	public ArrayList<Cards> innocent = new ArrayList<assets.Cards>();
	//Player in play
	
	public Player(String name, Board game, Position start){
		this.name = name;
		this.game = game;
		this.CurrentPos = start;
		//this.hand = hand;
		//for(Cards p : hand){
			//innocent.add(p);
			
		//}
	}
	
	
		public String toString(){
			char[] c = name.toCharArray();
			return Character.toString(c[0]);
		}
}
	//INITILISE
    //Who they are
	//needs to know the board
	//Current Position
	//know its hand
	//needs to know innocent cards
	
	
	//MOVE
	//needs to Move
	//Roll dice
	//Choose position
	
	
	//SUSPECT
	//Guess
	//Choose weapon + character (In room)
	//Move guessed player and weapon to that room
	//Be shown incorrect guesses
	//Accuse
	//Choose w+c and room
	
	//Show incorrect cards
	

