package game;

public abstract class Position {

	public Player p;
	
	protected int x;
	protected int y;
	
	protected String coord;
	
	public Position(int x, int y){
		this.x=x;
		this.y=y;
		String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
		coord = alphabet[x]+y;
	}
	
	
	public abstract void setPlayer(Player p);

	public  abstract Player getPlayer();

	//What it contains
	
}
