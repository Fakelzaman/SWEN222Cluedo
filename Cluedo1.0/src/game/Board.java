package game;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.swing.JFileChooser;

import assets.Cards;

public class Board {

	public ArrayList humanPlayers; //An array of actual players
	public Player[] allPlayers; //Array of all actual game pieces
	public Room[] rooms; //Array of all rooms
	public Weapon[] weap; //Array of all Weapons
	// Array of cards
	// Array of guilty
	// Win State
	public Position[][] Map; //2D Array of positions on the map
	// Current players turn

	public Board() {

		createMap();  //Creates a map of the game using 2D array of positions
		placePlayers(); //Creates and places the player in starting positions,
		createRooms(); //Creates an array of rooms who know their positions
		scatterWeapons(); //Scatters the weopons randomly into the rooms
		humanPlayers(); // Asks how many players are playing.
		createCards(); //create, distribute guilty
		// Distribute remaining cards

		// Reveal Left over cards

		// First Turn to Scarlet
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
							p = new BlockedPosition();
							break;
						case 'B':
							p = new MoveablePosition();
							break;
						case 'R':
							p = new RoomPosition();
							break;
						case '-':
							p = new MoveablePosition();
							break;
						case 'N':
							p = new NorthEntrancePosition();
							break;
						case 'E':
							p = new EastEntrancePosition();
							break;
						case 'S':
							p = new SouthEntrancePosition();
							break;
						case 'W':
							p = new WestEntrancePosition();
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
		rooms[0] = new Room("Kitchen", kpos, true);
		ArrayList Ballpos = iterateRooms(8,15,1,7);
		Ballpos.remove(Map[8][1]);
		Ballpos.remove(Map[9][1]);
		Ballpos.remove(Map[14][1]);
		Ballpos.remove(Map[15][1]);
		rooms[1] = new Room("Ball Room", Ballpos, false);
		ArrayList Conpos = iterateRooms(18,23,1,5);
		Conpos.remove(Map[18][5]);
		Conpos.remove(Map[23][5]);
		rooms[2] = new Room("Conservatory", Conpos, true);
		ArrayList Dinpos = iterateRooms(0,7,9,15);
		Dinpos.remove(Map[5][9]);
		Dinpos.remove(Map[6][9]);
		Dinpos.remove(Map[7][9]);
		rooms[3] = new Room("Dining Room", Dinpos, false);
		ArrayList Billpos = iterateRooms(18,23,8,12);
		rooms[4] = new Room("Billiard Room", Billpos, false);
		ArrayList Libpos = iterateRooms(17,23,14,18);
		Libpos.remove(Map[17][14]);
		Libpos.remove(Map[17][18]);
		Libpos.remove(Map[23][14]);
		Libpos.remove(Map[23][18]);
		rooms[5] = new Room("Library", Libpos, false);
		ArrayList Loupos = iterateRooms(0,6,19,24);
		Loupos.remove(Map[6][24]);
		rooms[6] = new Room("Lounge", Loupos, true);
		ArrayList Halpos = iterateRooms(9,14,18,24);
		rooms[7] = new Room("Hall", Halpos, false);
		ArrayList Stupos = iterateRooms(17,23,21,24);
		Loupos.remove(Map[17][24]);
		rooms[8] = new Room("Study", Stupos, true);
		
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
       System.out.print(ran);
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

			}
		} else {
			System.out.println("Invalid Integer. Must be between 1-6");
			humanPlayers();
		}

	}
	
	public void createCards(){
		Set pack = new HashSet();
		//Add all cards to Game Pls
		
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
			System.out.println();
		}
		
	}
}
