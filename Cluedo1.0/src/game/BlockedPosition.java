package game;

public class BlockedPosition extends Position {
	
	protected int x;
	protected int y;
	
	public BlockedPosition(int x, int y){
		super(x,y);
		String[] alphabet = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y"};
		coord = alphabet[x]+y;
	}
	
	
	public String toString(){
		return "X";
	}

	@Override
	public void setPlayer(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
}
