package typoglycemia;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class typoglycemiaTest {
	
	Typoglycemia test = new Typoglycemia();

	@Test
	public void testGetSpaces() {
		char[] test1 = ("Can I tell you a story").toCharArray();
		ArrayList<Integer> expectedResult1 = new ArrayList<Integer>();
		expectedResult1.add(0);
		expectedResult1.add(3);
		expectedResult1.add(4);
		expectedResult1.add(5);
		expectedResult1.add(6);
		expectedResult1.add(10);
		expectedResult1.add(11);
		expectedResult1.add(14);
		expectedResult1.add(15);
		expectedResult1.add(16);
		expectedResult1.add(17);
		expectedResult1.add(22);
		assertEquals(expectedResult1, test.getWordBoundaryIndices(test1));
		
		char[] test2 = ("Can't I tell you a story").toCharArray();
		ArrayList<Integer> expectedResult2 = new ArrayList<Integer>();
		expectedResult2.add(0);
		expectedResult2.add(5);
		expectedResult2.add(6);
		expectedResult2.add(7);
		expectedResult2.add(8);
		expectedResult2.add(12);
		expectedResult2.add(13);
		expectedResult2.add(16);
		expectedResult2.add(17);
		expectedResult2.add(18);
		expectedResult2.add(19);
		expectedResult2.add(24);
		assertEquals(expectedResult2, test.getWordBoundaryIndices(test2));
	}
	
	@Test
	public void testJumbleWord() {
		
		char[] test1 = ("JonHopkins").toCharArray();
		char[] resultOutput = test.jumbleWord(test1, 0, test1.length);
		
		assertEquals(test1[0], resultOutput[0]);
		assertEquals(test1[test1.length - 1], resultOutput[test1.length - 1]);
		for(int i = 0; i < 100; i++){
			resultOutput = test.jumbleWord(test1, 0, test1.length);
			
			assertEquals(test1[0], resultOutput[0]);
			assertEquals(test1[test1.length - 1], resultOutput[test1.length - 1]);
		}

	}
	
	@Test
	public void testJumbleWordsInLine() {
		
		char[] test1 = ("Gerry Adams loves a bop").toCharArray();
		char[] resultOutput = test.jumbleWordsInLine(test1);
		
		assertEquals(test1[0], resultOutput[0]);
		assertEquals(test1[4], resultOutput[4]);
		assertEquals(test1[6], resultOutput[6]);
		assertEquals(test1[10], resultOutput[10]);
		assertEquals(test1[12], resultOutput[12]);
		assertEquals(test1[16], resultOutput[16]);
		assertEquals(test1[18], resultOutput[18]);
		assertEquals(test1[20], resultOutput[20]);
		assertEquals(test1[22], resultOutput[22]);
		
		for(int i = 0; i < 100; i++){
			resultOutput = test.jumbleWordsInLine(test1);
			
			assertEquals(test1[0], resultOutput[0]);
			assertEquals(test1[4], resultOutput[4]);
			assertEquals(test1[6], resultOutput[6]);
			assertEquals(test1[10], resultOutput[10]);
			assertEquals(test1[12], resultOutput[12]);
			assertEquals(test1[16], resultOutput[16]);
			assertEquals(test1[18], resultOutput[18]);
			assertEquals(test1[20], resultOutput[20]);
			assertEquals(test1[22], resultOutput[22]);
		}

	}

}
