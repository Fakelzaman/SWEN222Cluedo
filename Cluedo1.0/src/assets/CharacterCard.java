package assets;
	
public class CharacterCard extends Cards {
	
	public CharacterCard(String name){
		super(name);
	}

	
	@Override
	public String toString() {
		return this.name;
	}
	
}
