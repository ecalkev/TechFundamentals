package markvshaney;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class MarkVShaneyTest {

	@Test
	public void testStorePrefixesAndFollowers() throws IOException {
		MarkVShaney mVShaney = new MarkVShaney();
		mVShaney.storePrefixesAndFollowers("testFile.txt", new int[3]);
		LinkedHashMap map = mVShaney.getMap();
				
		assertEquals("an ", map.get("A dog is ").getFollower().get(0));
		assertEquals("animal ", map.get("dog is an ").getFollower().get(0));
		assertEquals("with ", map.get("is an animal ").getFollower().get(0));
		assertEquals("4 ", map.get("an animal with ").getFollower().get(0));
		assertEquals("legs ", map.get("animal with 4 ").getFollower().get(0));
		assertEquals("and ", map.get("with 4 legs ").getFollower().get(0));
		assertEquals("a ", map.get("4 legs and ").getFollower().get(0));
		assertEquals("tail. ", map.get("legs and a ").getFollower().get(0));
		assertEquals("an ", map.get("A cat is ").getFollower().get(0));
	}
	
	@Test
	public void testStorePrefixesAndFollowersNoDuplication() throws IOException {
		MarkVShaney mVShaney = new MarkVShaney();
		mVShaney.storePrefixesAndFollowers("testFile.txt", new int[3]);
		LinkedHashMap map = mVShaney.getMap();
				
		assertNull(map.get("A dog is ").getFollower().get(1));
		assertNull(map.get("dog is an ").getFollower().get(1));
		assertNull(map.get("is an animal ").getFollower().get(1));
		assertNull(map.get("an animal with ").getFollower().get(1));
		assertNull(map.get("animal with 4 ").getFollower().get(1));
		assertNull(map.get("with 4 legs ").getFollower().get(1));
		assertNull(map.get("A dog is ").getFollower().get(1));
	}
	
	@Test
	public void testStorePrefixesAndFollowersMultipleFollowers() throws IOException {
		MarkVShaney mVShaney = new MarkVShaney();
		mVShaney.storePrefixesAndFollowers("testFile.txt", new int[3]);
		LinkedHashMap map = mVShaney.getMap();
				
		assertEquals("tail. ", map.get("legs and a ").getFollower().get(0));
		assertEquals("tail ", map.get("legs and a ").getFollower().get(1));
	}
	
	@Test
	public void testStorePrefixesAndFollowersDoesNotStoreLastPrefix() throws IOException {
		MarkVShaney mVShaney = new MarkVShaney();
		mVShaney.storePrefixesAndFollowers("testFile.txt", new int[3]);
		LinkedHashMap map = mVShaney.getMap();

		assertNull(map.get("a tail also. "));
	}
	
	@Test(expected = Test.None.class)
	public void testRandomSentencesDoesntFailWhenNoFollowerExists() throws IOException {
		MarkVShaney mVShaney = new MarkVShaney();
		mVShaney.storePrefixesAndFollowers("testFile.txt", new int[3]);
		mVShaney.randomSentences(300, "testOutput.txt", new int[3]);
	}

}
