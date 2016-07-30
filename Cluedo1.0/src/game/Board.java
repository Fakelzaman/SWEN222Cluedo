package game;

import assets.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.JFileChooser;

import assets.Cards;

public class Board {

	public ArrayList<Player> humanPlayers;
	public Player[] allPlayers;
	public Room[] rooms;
	public Weapon[] weap;
	// Array of cards
	public Cards[] guiltyCards;
	// Win State
	public Position[][] map;
	// Current players turn

	public Board() {

		createmap();
		placePlayers();
		//createRooms
		// Place weapons randomly
		humanPlayers();
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

	public void createmap() {

		try {
			Scanner scan = new Scanner(new File("src/CLUEDO_BOARD.txt"));

			map = new Position[25][25];
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

						map[i][y] = p;

					}
					y++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void placePlayers() {

		allPlayers = new Player[6];

		Player white = new Player("White", this, map[9][0]);
		map[9][0].setPlayer(white);
		allPlayers[2] = white;
		Player green = new Player("Green", this, map[14][0]);
		map[14][0].setPlayer(green);
		allPlayers[3] = green;
		Player peacock = new Player("Peacock", this, map[23][6]);
		map[23][6].setPlayer(peacock);
		allPlayers[4] = peacock;
		Player mustard = new Player("Mustard", this, map[0][17]);
		map[0][17].setPlayer(mustard);
		allPlayers[1] = mustard;
		Player plum = new Player("Plum", this, map[23][19]);
		map[23][19].setPlayer(plum);
		allPlayers[5] = plum;
		Player scarlet = new Player("Scarlet", this, map[7][24]);
		map[7][24].setPlayer(scarlet);
		allPlayers[0] = scarlet;

	}
	
	public void createRooms(){
		rooms = new Room[9];
		ArrayList kpos = iterateRooms(0,5,1,6);
		kpos.remove(map[0][6]);
		rooms[0] = new Room("Kitchen", kpos, true);
		ArrayList Ballpos = iterateRooms(8,15,1,7);
		Ballpos.remove(map[8][1]);
		Ballpos.remove(map[9][1]);
		Ballpos.remove(map[14][1]);
		Ballpos.remove(map[15][1]);
		rooms[1] = new Room("Ball Room", Ballpos, false);
		ArrayList Conpos = iterateRooms(18,23,1,5);
		Conpos.remove(map[18][5]);
		Conpos.remove(map[23][5]);
		rooms[2] = new Room("Conservatory", Conpos, true);
		ArrayList Dinpos = iterateRooms(0,7,9,15);
		Dinpos.remove(map[5][9]);
		Dinpos.remove(map[6][9]);
		Dinpos.remove(map[7][9]);
		rooms[3] = new Room("Dining Room", Dinpos, false);
		ArrayList Billpos = iterateRooms(18,23,8,12);
		rooms[4] = new Room("Billiard Room", Billpos, false);
		ArrayList Libpos = iterateRooms(17,23,14,18);
		Libpos.remove(map[17][14]);
		Libpos.remove(map[17][18]);
		Libpos.remove(map[23][14]);
		Libpos.remove(map[23][18]);
		rooms[5] = new Room("Library", Libpos, false);
		ArrayList Loupos = iterateRooms(0,6,19,24);
		Loupos.remove(map[6][24]);
		rooms[6] = new Room("Lounge", Loupos, true);
		
	}
    public ArrayList iterateRooms(int startx, int endx, int starty, int endy){
    	ArrayList that = new ArrayList<Position>();
    	while(startx <= endx){
    		while(starty <= endy){
    			that.add(map[startx][starty]);
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
		ArrayList<Cards> pack = new ArrayList<Cards>();
		guiltyCards = new Cards[3];
		//Add all cards to Game Pack
		pack.add(new CharacterCard("Miss Scarlet"));
		pack.add(new CharacterCard("Mrs White"));		
		pack.add(new CharacterCard("Reverend Green"));
		pack.add(new CharacterCard("Colonol Mustard"));
		pack.add(new CharacterCard("Mrs Peacock"));
		pack.add(new CharacterCard("Professor Plum"));
		
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
		int cardsToDeal =( pack.size() % humanPlayers.size());
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
		for(int y = 0; y < map.length; y++){
			for(int x = 0; x < map[y].length; x++){
				
				if(map[x][y].getPlayer() != null){
					System.out.print(map[x][y].getPlayer().toString());
				}
				else{
				System.out.print(map[x][y].toString());
				}				
				
			}
			System.out.println();
		}
		
	}
}
