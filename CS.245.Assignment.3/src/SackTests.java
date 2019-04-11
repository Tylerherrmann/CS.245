import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SackTests {

	private Sack<Integer> s;
	private Field elementData;
	private Field size;
	private Method ensureCapacity;
	private Method remove;
	
	@BeforeEach
	public void setUp() throws Exception {
		s = new Sack<Integer>();
		elementData = Sack.class.getDeclaredField("elementData");
		elementData.setAccessible(true);
		size = Sack.class.getDeclaredField("size");
		size.setAccessible(true);
		ensureCapacity = Sack.class.getDeclaredMethod("ensureCapacity", int.class);
		ensureCapacity.setAccessible(true);
		remove = Sack.class.getDeclaredMethod("remove", int.class);
		remove.setAccessible(true);
	}

	@Test
	public void testDefaultConstructor() {
		try {
			assertEquals("Sack() constructor is not working correctly", size.get(s), 0);
			assertEquals("Sack() constructor is not working correctly",  ((Object[]) elementData.get(s)).length, Sack.DEFAULT_CAPACITY);
		}  catch (Exception e) {
			fail("Sack() constructor and/or instance variables not found");
		}
	}
	
	@Test
	public void testConstructor() {
		try {
			Random rand = new Random();
			int capacity = rand.nextInt(50);
			s= new Sack<Integer>(capacity);
			assertEquals("Sack(int) constructor is not working correctly", size.get(s), 0);
			assertEquals("Sack(int) constructor is not working correctly",  ((Object[]) elementData.get(s)).length, capacity);
		}  catch (Exception e) {
			fail("Sack(int) constructor and/or instance variables not found");
		}
	}
	
	@Test
	public void testConstructorInvalidArgument() {
		Random rand = new Random();
		int capacity = rand.nextInt(49) + 1;
		Executable statement = () -> new Sack<Integer>(-capacity);
		assertThrows(IllegalArgumentException.class, statement, "Sack(int) constructor is not working correctly");
	}
	
	@Test
	public void testIsEmpty() {
		assertEquals("isEmpty is not working correctly", s.isEmpty(), true);
		try {
			size.set(s, 1);
			assertEquals("isEmpty is not working correctly", s.isEmpty(), false);
		} catch (Exception e) {
			fail("isEmpty is not working correctly");
		}
	}
	
	@Test
	public void testAdd() {
		try {
			Random rand = new Random();
			int numElements = rand.nextInt(9) + 11;
			ArrayList<Integer> expectedElements = new ArrayList<Integer>();
			int randElement;
			for(int i=0; i<numElements; ++i) {
				randElement = rand.nextInt(50) + 1;
				expectedElements.add(randElement);
				s.add(randElement);
				assertEquals("add is not working correctly", size.get(s), i+1);
			}
			for(int i=0; i<numElements; ++i) {
				assertEquals("add is not working correctly", ((Object[])elementData.get(s))[i], expectedElements.get(i));
			}
		} catch (Exception e) {
			fail("add is not working correctly");
		}
	}
	
	@Test
	public void testGrab() {
		assertEquals("grab is not working correctly", s.grab(), null);
		Random rand = new Random();
		int numElements = rand.nextInt(9) + 1;
		Integer[] setElementData = new Integer[10];
		ArrayList<Integer> expectedElements = new ArrayList<Integer>();
		int randElement;
		for(int i=0; i<numElements; ++i) {
			randElement = rand.nextInt(50) + 1;
			if(!expectedElements.contains(randElement)) {
				setElementData[i] = randElement;
				expectedElements.add(randElement);
			} else {
				--i;
			}
		}
		try {
			elementData.set(s, setElementData);
			size.set(s, numElements);
			for(int i=0; i<numElements; ++i) {
				expectedElements.remove(s.grab());
				assertEquals("grab is not working correctly", size.get(s), numElements-i-1);
			}
			assertEquals("grab is not working correctly", expectedElements.size(), 0);
		} catch (Exception e) {
			fail("grab is not working correctly");
		}
	}
	
	@Test
	public void testDump() {
		try {
			Random rand = new Random();
			int numElements = rand.nextInt(8) + 1;
			Integer[] setElementData = new Integer[numElements+1];
			for(int i=0; i<numElements; ++i) {
				setElementData[i] = rand.nextInt(50) + 1;
			}
			elementData.set(s, Arrays.copyOf(setElementData, setElementData.length));
			size.set(s, numElements);
			Object[] dumpedElements = s.dump();
			assertEquals("dump is not working correctly", size.get(s), 0);
			Object[] postDumpElementData = (Object[]) elementData.get(s);
			assertTrue("dump is not working correctly", dumpedElements != postDumpElementData);
			assertEquals("dump is not working correctly", dumpedElements.length, numElements);
			for(int i=0; i<numElements; ++i) {
				assertEquals("dump is not working correctly", dumpedElements[i], setElementData[i]);
			}
			assertEquals("dump is not working correctly", postDumpElementData.length, numElements+1);
			for(int i=0; i<numElements; ++i) {
				assertNull("dump is not working correctly", postDumpElementData[i]);
			}
		} catch (Exception e) {
			fail("dump is not working correctly");
		}
	}
	
	@Test
	public void testEnsureCapacity() {
		assertEquals("ensureCapacity does not have the correct modifiers", ensureCapacity.getModifiers(), 2);
		try {
			for(int i=0; i<=10; ++i) {
				ensureCapacity.invoke(s, i);
				assertEquals("ensureCapacity is not working correctly", ((Object[])elementData.get(s)).length, 10);
			}
			ensureCapacity.invoke(s, 11);
			assertEquals("ensureCapacity is not working correctly", ((Object[])elementData.get(s)).length, 21);
			
			Random rand = new Random();
			int capacity = rand.nextInt(100)+1;
			s = new Sack<Integer>(capacity);
			for(int i=0; i<=capacity; ++i) {
				ensureCapacity.invoke(s, i);
				assertEquals("ensureCapacity is not working correctly", ((Object[])elementData.get(s)).length, capacity);
			}
			ensureCapacity.invoke(s, capacity+1);
			assertEquals("ensureCapacity is not working correctly", ((Object[])elementData.get(s)).length, capacity*2+1);
		} catch (Exception e) {
			fail("ensureCapacity is not working correctly");
		}
	}
	
	@Test
	public void testRemove() {
		assertEquals("remove does not have the correct modifiers", remove.getModifiers(), 2);
		try {
			Random rand = new Random();
			Integer[] setElementData = new Integer[10];
			ArrayList<Integer> expectedElements = new ArrayList<Integer>();
			int randElement;
			for(int i=0; i<10; ++i) {
				randElement = rand.nextInt(50) + 1;
				setElementData[i] = randElement;
				expectedElements.add(randElement);
			}
			elementData.set(s, setElementData);
			size.set(s, 10);
			
			int randIndex;
			while(!expectedElements.isEmpty()) {
				randIndex = rand.nextInt(expectedElements.size());
				expectedElements.remove(randIndex);
				remove.invoke(s, randIndex);
				assertEquals("remove is not working correctly", expectedElements.size(), size.get(s));
				for(int i=0; i<expectedElements.size(); ++i) {
					assertEquals("remove is not working correctly", ((Object[])elementData.get(s))[i], expectedElements.get(i));
				}
				assertNull("remove is not working correctly", ((Object[])elementData.get(s))[expectedElements.size()]);
			}
			assertEquals("remove is not working correctly", size.get(s), 0);
			
		} catch (Exception e) {
			fail("remove is not working correctly");
		}
	}
}
