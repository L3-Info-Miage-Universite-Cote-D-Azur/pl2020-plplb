package metier;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class
UETest
{
	private UE ue;
	
	@Test
	public void
	testConstructor ()
	{
		this.ue = new UE("name","code");
		assertTrue(this.ue.getUeCode().equals("code"));
		assertEquals(this.ue.getSemestreNumber(), -1);
		assertTrue(this.ue.getUeName().equals("name"));
		
		this.ue.setCategorie("cat");
		assertTrue(this.ue.getCategorie().equals("cat"));
	}
}
