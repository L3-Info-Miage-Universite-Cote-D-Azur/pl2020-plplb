package metier;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class
StudentTest
{
	private Student st;
	
	@BeforeEach
	public void
	testConstructor ()
	{
		this.st = new Student();
		assertTrue(this.st.getNom().equals("nom par défaut"));
		assertTrue(this.st.getNom().equals(this.st.toString()));
		this.st.setNom("ax400800");
		assertFalse(this.st.getNom().equals("nom par défaut"));
		assertTrue(this.st.getNom().equals("ax400800"));
		assertTrue(this.st.getNom().equals(this.st.toString()));
	}
	
	@Test
	public void
	testAcceptINE ()
	{
		this.st = new Student();
		
		String[] testCasesFalse = new String[] {"nom par défaut",
										   		"a1a1a1a1",
										   		"aa11111a",
										   		"11aaaaaa",
										   		"zz11l111",
										   		"aa",
										   		"aa1111111111",
										   		"ax40080O",
										   		"1a123456"};
		String[] testCasesTrue = new String[] {"ax400800",
											   "jm706705",
											   "my713752",
											   "kr601479",
											   "gt703409",
											   "zz999999",
											   "aa111111"};
		for (String falseCase : testCasesFalse)
			assertFalse(this.st.acceptINE(falseCase));
		for (String trueCase : testCasesTrue)
			assertTrue(this.st.acceptINE(trueCase));
	}
}
