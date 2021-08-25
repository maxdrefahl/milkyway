import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
import org.junit.Test;
public class MilkyWayTests {
	ArrayList<Orbit> test = new ArrayList<>();
	Planet sun = new Planet("Sun", 3e6, 1, 0, 0, null);
	@Test
	public void testFileManager(){
		FileManager.readFile(sun, "Planets.txt", test);
		assertEquals("ArrayList length does not match", 19, test.size());
	}
	@Test
	public void testVector(){
		Vector test = Vector.scalarProduct(sun.getPosition(0), 10);
		assertNotEquals(true, test.getPolar());
		assertEquals(0, test.getX(), 0);
		assertEquals(0, test.getY(), 0);
	}
}
