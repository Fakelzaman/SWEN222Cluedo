package game;
import assets.*;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;

import assets.Cards;

public class Player {
	
	public Boolean inPlay = false; 
	public String name;
	public Board game;
	public Position CurrentPos;
	public ArrayList<Cards> hand = new ArrayList<Cards>();
	public ArrayList<Cards> innocent = new ArrayList<Cards>();
	public Room room = null;
	
	//Player in play
	
	public Player(String name, Board game, Position start){
		this.name = name;
		this.game = game;
		this.CurrentPos = start;
	}
	
	
		public String toString(){
			char[] c = name.toCharArray();
			return Character.toString(c[0]);
		}


		public void dealCard(Cards card) {
			this.hand.add(card);
			this.addToInnoccent(card);
			
		}
		
		public void addToInnoccent(Cards card){
			this.innocent.add(card);
		}
		
		public void move(int roll, Scanner scan){
			
			//Set up taking user input
			
			String s = "";
			Position newPos = CurrentPos;
			System.out.println("Type up, down, left and right to move");
			
			//Player is in a room
			while(room!=null){
				
				//Take the next input by the player
				s = scan.nextLine();
				System.out.println("You are in a room, move to an exit (labelled n, e, s or w) or type corner if you're in the Kitchen, Conservatory, Study or Lounge to move to the opposite corner");
				
				
				if(s.equalsIgnoreCase("up")&&CurrentPos.y-1>=0){
					newPos = game.Map[CurrentPos.y-1][CurrentPos.x];
					if(newPos instanceof RoomPosition || newPos instanceof Door){
						CurrentPos = newPos;
					}
				} else if(s.equalsIgnoreCase("right")&&CurrentPos.x+1<25){
					newPos = game.Map[CurrentPos.y][CurrentPos.x+1];
					if(newPos instanceof RoomPosition || newPos instanceof Door){
						CurrentPos = newPos;
					}
				} else if(s.equalsIgnoreCase("down")&&CurrentPos.y+1<25){
					newPos = game.Map[CurrentPos.y+1][CurrentPos.x];
					if(newPos instanceof RoomPosition || newPos instanceof Door){
						CurrentPos = newPos;
					}
				} else if(s.equalsIgnoreCase("left")&&CurrentPos.x-1>=0){
					newPos = game.Map[CurrentPos.y][CurrentPos.x-1];
					if(newPos instanceof RoomPosition || newPos instanceof Door){
						CurrentPos = newPos;
					}
				} else if(s.equalsIgnoreCase("corner")&&room.corner!=null){
					
						
						
					//MOVE TO OTHER CORNER
					//STILL NEED TO CHANGE CURPOSITION
					this.room = room.corner;
					CurrentPos = newPos;
				
				} else {
					System.out.println("That was not a valid command, use up, down, left and right to move");
				}
				
				
			}
			
			boolean moved=false;
			while(roll>0){
				System.out.println("You have "+roll+" moves left");
				s=scan.nextLine();
				System.out.println(CurrentPos.y);
				if(s.equalsIgnoreCase("up")&&CurrentPos.y-1>=0){
					newPos = game.Map[CurrentPos.x][CurrentPos.y-1];
					System.out.println(newPos instanceof MoveablePosition );
					if((newPos instanceof MoveablePosition || newPos instanceof Door) && newPos.getPlayer()==null){
						
						this.CurrentPos=newPos;
						game.Map[CurrentPos.x][CurrentPos.y].setPlayer(this);
						game.Map[CurrentPos.x][CurrentPos.y+1].setPlayer(null);
						moved=true;
						
					}
				} else if(s.equalsIgnoreCase("right")&&CurrentPos.x+1<25){
					newPos = game.Map[CurrentPos.x+1][CurrentPos.y];
					if((newPos instanceof MoveablePosition || newPos instanceof Door) && newPos.getPlayer()==null){
						
						this.CurrentPos=newPos;
						game.Map[CurrentPos.x][CurrentPos.y].setPlayer(this);
						game.Map[CurrentPos.x-1][CurrentPos.y].setPlayer(null);
						moved=true;
					}
				} else if(s.equalsIgnoreCase("down")&&CurrentPos.y+1<25){
					newPos = game.Map[CurrentPos.x][CurrentPos.y+1];
					if((newPos instanceof MoveablePosition || newPos instanceof Door) && newPos.getPlayer()==null){
						
						this.CurrentPos=newPos;
						game.Map[CurrentPos.x][CurrentPos.y].setPlayer(this);
						game.Map[CurrentPos.x][CurrentPos.y-1].setPlayer(null);
						moved=true;
					}
				} else if(s.equalsIgnoreCase("left")&&CurrentPos.x-1>=0){
					newPos = game.Map[CurrentPos.x-1][CurrentPos.y];
					if((newPos instanceof MoveablePosition || newPos instanceof Door) && newPos.getPlayer()==null){
						
						this.CurrentPos=newPos;
						game.Map[CurrentPos.x][CurrentPos.y].setPlayer(this);
					
						game.Map[CurrentPos.x+1][CurrentPos.y].setPlayer(null);
						moved=true;
					}
				}
				
				
				
				if(newPos instanceof Door){
					//iterate through all rooms
					//check if this players Current position is in the room
					//if it is in the room put the player in the room using a method
					
					
				}
				//game.display();
				game.display();
				if(moved==true){
					roll--;
					moved=false;
				}
			}
			//scan.close();
		}
		
		
		
		
		public void suspect(){
			CharacterCard c;
			WeaponCard w;
			
			RoomCard r = null;
			for(Room R : game.rooms){
				if(R.toString().equalsIgnoreCase(room.toString())){
					r = new RoomCard(R.toString());
					break;
				}
			}
			System.out.println("Please type in a character to accuse: eg(Scarlet, Mustard, White, Green, Peacock, Plum)");
			Scanner scan = new Scanner(System.in);
			String s = scan.nextLine();
			while(!(s.equalsIgnoreCase("Scarlet")|| s.equalsIgnoreCase("Mustard")||s.equalsIgnoreCase("White")||s.equalsIgnoreCase("Green")||s.equalsIgnoreCase("Peacock")||s.equalsIgnoreCase("Plum"))){
				System.out.println("Invalid Input");
				s = scan.nextLine();
			}
			c = new CharacterCard(s);
			
			System.out.println("Please type in a weapon to accuse: eg(Candlestick, Dagger, Lead Pipe, Revolver, Rope, Spanner)");
			s = scan.nextLine();
			while(!(s.equalsIgnoreCase("CandleStick")|| s.equalsIgnoreCase("Dagger")||s.equalsIgnoreCase("Lead Pipe")||s.equalsIgnoreCase("Revolver")||s.equalsIgnoreCase("Rope")||s.equalsIgnoreCase("Spanner"))){
				System.out.println("Invalid Input");
				s = scan.nextLine();
			}
			w = new WeaponCard(s);
			
			for(Player p : game.humanPlayers){
				for(Cards a : p.hand){
					if(a.toString().equalsIgnoreCase(r.toString()) || a.toString().equalsIgnoreCase(p.toString()) || a.toString().equalsIgnoreCase(w.toString())){
						System.out.println(p.toString() + " Shows you " + a.toString() + ".");
					    if(!innocent.contains(a)){
					    	innocent.add(a);
					    }
					    System.out.println(a.toString() + " has been added to innocent display.");
					    break;
					} 
				}
			}
		}	
		
		public void displayCards(){
		ArrayList <Cards> w = new ArrayList<Cards>();
		ArrayList <Cards> p	= new ArrayList<Cards>();
		ArrayList <Cards> r = new ArrayList<Cards>();
		
		for(Cards c : innocent){
			if(c instanceof WeaponCard){
				w.add(c);
			}
			else if(c instanceof CharacterCard){
				p.add(c);
			}
			else if(c instanceof RoomCard){
				r.add(c);
			}
		}
		
		System.out.println("Innocent Characters: " + p.toString());
		System.out.println("Innocent Weapons: " + w.toString());
		System.out.println("Innocent Rooms: " + r.toString());
		}


		public void accuse() {
			// TODO Auto-generated method stub
			
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