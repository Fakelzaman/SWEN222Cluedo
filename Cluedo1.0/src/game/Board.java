package game;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.JFileChooser;

import assets.*;

public class Board {

	public ArrayList <Player> humanPlayers; //An array of actual players
	public Player[] allPlayers; //Array of all actual game pieces
	public Room[] rooms; //Array of all rooms
	public Weapon[] weap; //Array of all Weapons
	public Cards[] guiltyCards; //The 3 guilty cards
	// Win State
	public Position[][] Map; //2D Array of positions on the map
	public int currentPlayer = 0; //Current players turn
	
	Scanner scan;

	public Board() {

		createMap();  //Creates a map of the game using 2D array of positions
		placePlayers(); //Creates and places the player in starting positions,
		createRooms(); //Creates an array of rooms who know their positions
		scatterWeapons(); //Scatters the weapons randomly into the rooms
		humanPlayers(); // Asks how many players are playing.
		createCards(); //create, distribute guilty cards
		scan = new Scanner(System.in);
		takeTurn(); //first turn to scarlet

	}
	
	public void takeTurn(){
		display();
		Player current = humanPlayers.get(currentPlayer);
		System.out.println("It is "+ current.name + " Turn. Would you like to Roll, Accuse or Display Innocent Cards?");
		
		String s = scan.nextLine();
		while(!(s.equalsIgnoreCase("Roll")||s.equalsIgnoreCase("Accuse")||s.equalsIgnoreCase("Display Innocent Cards"))){
			System.out.println("Invalid Response");
			s = scan.nextLine();
		}
        if(s.equalsIgnoreCase("Display Innocent Cards")){
        	current.displayCards();
        	System.out.println(" Turn. Would you like to Roll, Accuse?");
        	s = scan.nextLine();
        	while(!(s.equalsIgnoreCase("Roll")||s.equalsIgnoreCase("Accuse"))){
    			System.out.println("Invalid Response");
    			s = scan.nextLine();
    		}
        }
        //scan.close();
		if(s.equalsIgnoreCase("Roll")){
			int roll = randomInt(1,12);
			current.move(roll,scan);
			
		}
		else if(s.equalsIgnoreCase("Accuse")){
			//current.accuse();
		}
		
		
		
		
		if(current.room != null){
			System.out.println("Would you like to Accuse, Suspect, Display Innocent Cards or End Turn?");

			s = scan.nextLine();
			while(!(s.equalsIgnoreCase("Accuse")||s.equalsIgnoreCase("Suspect")||s.equalsIgnoreCase("End Turn")||s.equalsIgnoreCase("Display Innocent Cards"))){
				System.out.println("Invalid Response");
				s = scan.nextLine();
			}
			if(s.equalsIgnoreCase("Display Innocent Cards")){
	        	current.displayCards();
	        	System.out.println("Would you like to Suspect, Accuse or End Turn?");
	        	s = scan.nextLine();
	        	while(!(s.equalsIgnoreCase("Suspect")||s.equalsIgnoreCase("Accuse")||s.equalsIgnoreCase("End Turn"))){
	    			System.out.println("Invalid Response");
	    			s = scan.nextLine();
	    		}
	        }
			if(s.equalsIgnoreCase("Accuse")){
				current.accuse();
			}
			else if(s.equalsIgnoreCase("Suspect")){
				current.suspect();
			}
			//scan.close();
		}
		else{
			System.out.println("Would you like to Accuse, Display Innocent Cards or End Turn?");
			//Scanner scan3 = new Scanner(System.in);
			
			s = scan.nextLine();
			while(!(s.equalsIgnoreCase("Accuse")||s.equalsIgnoreCase("End Turn")||s.equalsIgnoreCase("Display Innocent Cards"))){
				System.out.println("Invalid Response");
				s = scan.nextLine();
			}
			if(s.equalsIgnoreCase("Display Innocent Cards")){
	        	current.displayCards();
	        	System.out.println("Would you like to Accuse or End Turn?");
	        	s = scan.nextLine();
	        	while(!(s.equalsIgnoreCase("Accuse")||s.equalsIgnoreCase("End Turn"))){
	    			System.out.println("Invalid Response");
	    			s = scan.nextLine();
	    		}
	        }
			if(s.equalsIgnoreCase("Accuse")){
				current.accuse();
			}
			
		}
			
			System.out.println("Your turn is now over. Type anything when next player is ready");	
			//Scanner scan4 = new Scanner(System.in);
				s = scan.nextLine();
				
				currentPlayer++;
				if(currentPlayer >= humanPlayers.size()){currentPlayer = 0;}
				while(!humanPlayers.get(currentPlayer).inPlay){
					currentPlayer++;
					if(currentPlayer >= humanPlayers.size()){currentPlayer = 0;}
				}
				System.out.flush();
				//scan.close();
				System.out.println("We got to the End");
				takeTurn();
		
	}
	


	// TAKE TURN
	// find current player
	// If in corner room use passage?
	// roll
	// player.move
	// Update positions
	// player.guess (if able)
	// Move guessed players and weapons
	// player.accuse
	// end turn

	// GUESS
	// iterate through array
	// show innocent cards only 1

	// ACCUSE
	// Player accuses
	// if guilty matches accusation = win
	// else lose

	// UPDATE MOVEMENT
	// Move players to selected positions
	// Update Board

	// UPDATE GUESS
	// Move guessed players and weapons
	// update board

	// UPDATE ACCUSE
	// WINSTATE = True
	// else player.inPLay = false
	// show cards of player

	/* Creates the game map by scanning a txt file of the Game. X means wall (Not accesseable
	 * to players) R = room, n,s,e,w = doors to the room based on their respective direction
	 * W,G,P,S,M = players, - = movable positions*/
	
	public void createMap() {

		try {
			Scanner scan = new Scanner(new File("src/CLUEDO_BOARD.txt"));

			Map = new Position[25][25];
			int y = 0;
			while (scan.hasNext() && y < 25) {

				char[] c = scan.nextLine().toCharArray();

				if (c.length == 25) {
					for (int i = 0; i < 25; i++) {

						Position p;
						switch (c[i]) {
						case 'X':
							p = new BlockedPosition(i,y);
							break;
						case 'B':
							p = new MoveablePosition(i,y);
							break;
						case 'R':
							p = new RoomPosition(i,y);
							break;
						case '-':
							p = new MoveablePosition(i,y);
							break;
						case 'N':
							p = new NorthEntrancePosition(i,y);
							break;
						case 'E':
							p = new EastEntrancePosition(i,y);
							break;
						case 'S':
							p = new SouthEntrancePosition(i,y);
							break;
						case 'W':
							p = new WestEntrancePosition(i,y);
							break;
						default:
							// should do something meaningful instead of just
							// returning
							return;
						}

						Map[i][y] = p;

					}
					y++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/* Creates an array of player tokens and places them at their respective beginning 
	 * locations*/

	public void placePlayers() {

		allPlayers = new Player[6];

		Player white = new Player("White", this, Map[9][0]);
		Map[9][0].setPlayer(white);
		allPlayers[2] = white;
		Player green = new Player("Green", this, Map[14][0]);
		Map[14][0].setPlayer(green);
		allPlayers[3] = green;
		Player peacock = new Player("Peacock", this, Map[23][6]);
		Map[23][6].setPlayer(peacock);
		allPlayers[4] = peacock;
		Player mustard = new Player("Mustard", this, Map[0][17]);
		Map[0][17].setPlayer(mustard);
		allPlayers[1] = mustard;
		Player plum = new Player("Plum", this, Map[23][19]);
		Map[23][19].setPlayer(plum);
		allPlayers[5] = plum;
		Player scarlet = new Player("Scarlet", this, Map[7][24]);
		Map[7][24].setPlayer(scarlet);
		allPlayers[0] = scarlet;

	}
	
	public void createRooms(){
		rooms = new Room[9];
		
		ArrayList kpos = iterateRooms(0,5,1,6);
		kpos.remove(Map[0][6]);
		rooms[0] = new Room("Kitchen", kpos);
		
		ArrayList Ballpos = iterateRooms(8,15,1,7);
		Ballpos.remove(Map[8][1]);
		Ballpos.remove(Map[9][1]);
		Ballpos.remove(Map[14][1]);
		Ballpos.remove(Map[15][1]);
		
		rooms[1] = new Room("Ball Room", Ballpos);
		ArrayList Conpos = iterateRooms(18,23,1,5);
		Conpos.remove(Map[18][5]);
		Conpos.remove(Map[23][5]);
		rooms[2] = new Room("Conservatory", Conpos);
		
		ArrayList Dinpos = iterateRooms(0,7,9,15);
		Dinpos.remove(Map[5][9]);
		Dinpos.remove(Map[6][9]);
		Dinpos.remove(Map[7][9]);
		rooms[3] = new Room("Dining Room", Dinpos);
		
		ArrayList Billpos = iterateRooms(18,23,8,12);
		rooms[4] = new Room("Billiard Room", Billpos);
		
		ArrayList Libpos = iterateRooms(17,23,14,18);
		Libpos.remove(Map[17][14]);
		Libpos.remove(Map[17][18]);
		Libpos.remove(Map[23][14]);
		Libpos.remove(Map[23][18]);
		rooms[5] = new Room("Library", Libpos);
		
		ArrayList Loupos = iterateRooms(0,6,19,24);
		Loupos.remove(Map[6][24]);
		rooms[6] = new Room("Lounge", Loupos);
		
		ArrayList Halpos = iterateRooms(9,14,18,24);
		rooms[7] = new Room("Hall", Halpos);
		
		ArrayList Stupos = iterateRooms(17,23,21,24);
		Loupos.remove(Map[17][24]);
		rooms[8] = new Room("Study", Stupos);
		
		//Adding Corner passageways
		rooms[0].addCornerRoom(rooms[8]);
		rooms[8].addCornerRoom(rooms[0]);
		rooms[2].addCornerRoom(rooms[6]);
		rooms[6].addCornerRoom(rooms[2]);
		
		
	}
    public ArrayList iterateRooms(int startx, int endx, int starty, int endy){
    	ArrayList that = new ArrayList<Position>();
    	while(startx <= endx){
    		while(starty <= endy){
    			that.add(Map[startx][starty]);
    			starty++;
    		}
    		startx++;
    	}
    	return that;
    }
    
	public void scatterWeapons() {
       weap = new Weapon[6];
       weap[0] = new Weapon("Candlestick");
       weap[1] = new Weapon("Dagger");
       weap[2] = new Weapon("Lead Pipe");
       weap[3] = new Weapon("Revolver");
       weap[4] = new Weapon("Rope");
       weap[5] = new Weapon("Spanner");
       int count = 0;
       while(count < 6){
       int ran = randomInt(0,9);
       //System.out.print(ran);
       if(rooms[ran].weapons.isEmpty()){
    	  rooms[ran].weapons.add(weap[count]);
    	  count++;
       }
       }
       
	}
	
	public int randomInt(int min, int max){
		int num = (int) (Math.random()*max + min);
		return num;
	}

	public void humanPlayers() {
		Scanner scan = new Scanner(System.in);
		int numPlayer = -1;
		System.out.println("How Many Players?");

		if (scan.hasNextInt()) {
			numPlayer = scan.nextInt();
		} else {
			System.out.println("Invalid Command. Must be integer");
			humanPlayers();
		}
		if (numPlayer > 0 && numPlayer < 7) {
			this.humanPlayers = new ArrayList<Player>();
			for (int i = 0; i < numPlayer; i++) {
				humanPlayers.add(allPlayers[i]);
				humanPlayers.get(i).inPlay=true;
			}
		} else {
			System.out.println("Invalid Integer. Must be between 1-6");
			humanPlayers();
		}
		

	}
	
	public void createCards(){
		ArrayList<Cards> pack = new ArrayList<Cards>();
		guiltyCards = new Cards[3];
		//Add all cards to Game Pack
		pack.add(new CharacterCard("Scarlet"));
		pack.add(new CharacterCard("White"));		
		pack.add(new CharacterCard("Green"));
		pack.add(new CharacterCard("Mustard"));
		pack.add(new CharacterCard("Peacock"));
		pack.add(new CharacterCard("Plum"));
		
		pack.add(new WeaponCard("Candlestick"));
		pack.add(new WeaponCard("Dagger"));
		pack.add(new WeaponCard("Lead Pipe"));
		pack.add(new WeaponCard("Revolver"));
		pack.add(new WeaponCard("Rope"));
		pack.add(new WeaponCard("Spanner"));
		
		pack.add(new RoomCard("Kitchen"));
		pack.add(new RoomCard("Ball Room"));
		pack.add(new RoomCard("Conservatory"));
		pack.add(new RoomCard("Billiard Room"));
		pack.add(new RoomCard("Library"));
		pack.add(new RoomCard("Study"));
		pack.add(new RoomCard("Hall"));
		pack.add(new RoomCard("Lounge"));
		pack.add(new RoomCard("Dining Room"));
		
		//Select guilty cards
		int rand = (int)Math.ceil(Math.random()*pack.size()-1);
		Cards c = pack.get(rand);
		
		//Selecting guilty character
		do{
			rand = (int)Math.ceil(Math.random()*pack.size()-1);
			c = pack.get(rand);
		}while(!(c instanceof CharacterCard));
		guiltyCards[0] = c;
		pack.remove(c);
		
		//Selecting guilty room
		do{
			rand = (int)Math.ceil(Math.random()*pack.size()-1);
			c = pack.get(rand);
		}while(!(c instanceof RoomCard));
		guiltyCards[1] = c;
		pack.remove(c);
		
		//Selecting guilty weapon
		do{
			rand = (int)Math.ceil(Math.random()*pack.size()-1);
			c = pack.get(rand);
		}while(!(c instanceof WeaponCard));
		guiltyCards[2] = c;
		pack.remove(c);
		
		
		//Compute amount of cards to deal evenly and how many cards will remain
		int cardsToDeal = (pack.size() % humanPlayers.size());
		cardsToDeal = pack.size() - cardsToDeal;
		
		//Deal cards to players
		int curHuman=0;
		while(cardsToDeal>0){
			rand = (int)Math.ceil(Math.random()*pack.size()-1);
			humanPlayers.get(curHuman++).dealCard(pack.get(rand));
			pack.remove(rand);
			cardsToDeal--;
			if(curHuman==humanPlayers.size()){
				curHuman=0;
			}
		}
		
		//reveal remaining cards to all players
		for(Cards remainingCards : pack){
			for(Player p : humanPlayers){
				p.addToInnoccent(remainingCards);
			}
		}
		
	}
	
	public void display(){
		for(int y = 0; y < Map.length; y++){
			for(int x = 0; x < Map[y].length; x++){
				
				if(Map[x][y].getPlayer() != null){
					System.out.print(Map[x][y].getPlayer().toString());
				}
				else{
				System.out.print(Map[x][y].toString());
				}				
			}
			//System.out.print(y);
			System.out.println();
		}
		//System.out.println("ABCDEFGHIJKLMNOPQRSTUVWXY");
		
	}
}
