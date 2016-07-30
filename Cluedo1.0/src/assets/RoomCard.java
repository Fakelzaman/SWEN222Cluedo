package assets;

public class RoomCard extends Cards {
	
	public RoomCard(String name){
		super(name);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
