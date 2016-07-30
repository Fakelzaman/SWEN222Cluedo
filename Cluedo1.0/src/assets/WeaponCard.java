package assets;

public class WeaponCard extends Cards {

	public WeaponCard(String name){
		super(name);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
