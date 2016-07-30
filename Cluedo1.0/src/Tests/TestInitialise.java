package Tests;

import static org.junit.Assert.*;

import org.junit.Test;
import assets.*;
import game.*;


public class TestInitialise {

	
	@Test
	//test should produce a correct cluedo board
	public void correctMap(){
		Board b = new Board();
		for(int i=0;i<25;i++){
			for(int j=0;j<25;j++){
				assertTrue(b.map[i][j]!=null);
			}
		}
		
		
		
	}
	
	
	
	
	
}
