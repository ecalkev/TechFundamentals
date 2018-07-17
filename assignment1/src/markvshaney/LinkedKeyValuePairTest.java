package markvshaney;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedKeyValuePairTest {

	@Test
	public void testAddNextKeyWithSameHash() {
		LinkedKeyValuePair start = new LinkedKeyValuePair("start");
		start.addNextKeyWithSameHash("second");
		start.addNextKeyWithSameHash("third");

		assertEquals("start", start.get("start").getPrefix());
		assertEquals("second", start.get("second").getPrefix());
		assertEquals("third", start.get("third").getPrefix());
	}

	@Test
	public void testAddFollower() {
		LinkedKeyValuePair start = new LinkedKeyValuePair("start");
		start.addFollower("f1");
		start.addFollower("f2");

		assertEquals("f1", start.getFollower().get(0));
		assertEquals("f2", start.getFollower().get(1));
	}
	
	@Test
	public void testAddFollowerDoesNotDuplicateData() {
		LinkedKeyValuePair start = new LinkedKeyValuePair("start");
		start.addFollower("f1");
		start.addFollower("f2");
		start.addFollower("f1");
		
		assertNull(start.getFollower().get(2));
	}
	
	@Test
	public void testAddNextKeyWithSameHashDoesNotDeleteFollowerData() {
		LinkedKeyValuePair start = new LinkedKeyValuePair("start");
		start.addFollower("f1");
		start.addFollower("f2");
		
		start.addNextKeyWithSameHash("second");
		start.addNextKeyWithSameHash("third");
		start.addNextKeyWithSameHash("start");

		
		assertEquals("f1", start.get("start").getFollower().get(0));
		assertEquals("f2", start.get("start").getFollower().get(1));

	}
	
	@Test
	public void testAddNextKeyWithSameHashReturnData() {
		LinkedKeyValuePair start = new LinkedKeyValuePair("start");
		start.addFollower("f1");
		start.addFollower("f2");
		
		start.addNextKeyWithSameHash("second");
		start.addNextKeyWithSameHash("third");

		
		assertEquals("f1", start.addNextKeyWithSameHash("start").getFollower().get(0));
		assertEquals("f2", start.addNextKeyWithSameHash("start").getFollower().get(1));

	}
}
