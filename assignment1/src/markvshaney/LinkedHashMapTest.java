package markvshaney;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedHashMapTest {

	@Test
	public void testPutKeyAndGet() {
		LinkedHashMap map = new LinkedHashMap();
		map.putKey("Aa");
		map.putKey("BB");
		
		assertEquals("Aa", map.get("Aa").getPrefix());
		assertEquals("BB", map.get("BB").getPrefix());
		assertNull(map.get("Abc"));
	}
	
	@Test
	public void testPutKeyReturnData() {
		LinkedHashMap map = new LinkedHashMap();
		map.putKey("Aa");
		map.putKey("BB");
		map.get("Aa").addFollower("f1");
		
		assertEquals("Aa", map.putKey("Aa").getPrefix());
		assertEquals("f1", map.putKey("Aa").getFollower().get(0));
	}

}
