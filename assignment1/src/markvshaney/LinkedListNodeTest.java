package markvshaney;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkedListNodeTest {

	@Test
	public void testAdd() {
		LinkedListNode<Integer> start = new LinkedListNode<>(1);
		
		start.add(2);
		start.add(3);
		
		assertTrue(1 == start.get(0));
		assertTrue(2 == start.get(1));
		assertTrue(3 == start.get(2));
	}
	
	@Test
	public void testAddDoesNotDuplicateData() {
		LinkedListNode<Integer> start = new LinkedListNode<>(1);
		
		start.add(2);
		start.add(3);
		start.add(1);
		
		assertNull(start.get(3));
	}
	
	@Test
	public void getLength() {
		LinkedListNode<Integer> start = new LinkedListNode<>(1);
		
		start.add(2);
		start.add(3);
		start.add(1);
		
		assertTrue(3 == start.getLength());
	}

}
