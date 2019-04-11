import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class SortedLinkedListTests {
	
	private SortedLinkedList<Integer> sll;
	private Field first;
	private Field size;
	private Field data;
	private Field next;
	private Constructor<?> nodeConstructor;

	@BeforeEach
	public void setUp() throws Exception {
		sll = new SortedLinkedList<Integer>();
		first = SortedLinkedList.class.getDeclaredField("first");
		first.setAccessible(true);
		size = SortedLinkedList.class.getDeclaredField("size");
		size.setAccessible(true);
		
		Class<?> nodeClass = Class.forName("SortedLinkedList$Node");
		data = nodeClass.getDeclaredField("data");
		data.setAccessible(true);
		next = nodeClass.getDeclaredField("next");
		next.setAccessible(true);
		nodeConstructor = nodeClass.getDeclaredConstructors()[0];
	}
	
	@Test
	public void testFields() {
		assertEquals("SortedLinkedList should only have \"first\" and \"size\" fields", SortedLinkedList.class.getDeclaredFields().length, 2);
	}

	@Test
	public void testConstructor() {
		try {
			assertNull("SortedLinkedList constructor is not working correctly", first.get(sll));
			assertEquals("SortedLinkedList constructor is not working correctly", size.get(sll), 0);
		} catch (Exception e) {
			fail("SortedLinkedList constructor is not working correctly");
		}
	}
	
	@Test
	public void testIsEmpty() {
		try {
			assertEquals("isEmpty is not working correctly", sll.isEmpty(), true);
			size.set(sll, 1);
			assertEquals("isEmpty is not working correctly", sll.isEmpty(), false);
		} catch (Exception e) {
			fail("isEmpty is not working correctly");
		}
	}
	
	@Test
	public void testSize() {
		try {
			assertEquals("size method is not working correctly", sll.size(), 0);
			size.set(sll, 1);
			assertEquals("size method is not working correctly", sll.size(), 1);
		} catch (Exception e) {
			fail("size method is not working correctly");
		}
	}
	
	@Test
	public void testAdd() {
		try {
			ArrayList<Integer> list = new ArrayList<Integer>();
			Random rand = new Random();
			int r;
			for(int i=0; i<10; ++i) {
				r = rand.nextInt(10);
				sll.add(r);
				assertEquals("add(E) is not working correctly", size.get(sll), i+1);
				list.add(r);
				Collections.sort(list);
				assertTrue("add(E) is not working correctly", equalLists(list));
			}
		} catch (Exception e) {
			fail("add(E) method is not working correctly");
		}
	}
	
	@Test
	public void testRemove() {
		try {
			ArrayList<Integer> list = generateLists();
			Random rand = new Random();
			int randIndex;
			//ensure early remove first
			sll.remove(0);
			list.remove(0);
			assertEquals("remove is not working correctly", size.get(sll), list.size());
			assertTrue("remove is not working correctly", equalLists(list));
			
			while(!list.isEmpty()) {
				randIndex = rand.nextInt(list.size());
				sll.remove(randIndex);
				list.remove(randIndex);
				assertEquals("remove is not working correctly", size.get(sll), list.size());
				assertTrue("remove is not working correctly", equalLists(list));
			}
		} catch(Exception e) {
			fail("remove is not working correctly");
		}
	}
	
	@Test
	public void testRemoveIndexTooBig() {
		Executable statement = () -> sll.remove(1);
		assertThrows(IndexOutOfBoundsException.class, statement, "remove is not throwing an exception correctly");
	}
	
	@Test
	public void testRemoveIndexTooSmall() {
		Executable statement = () -> sll.remove(-1);
		assertThrows(IndexOutOfBoundsException.class, statement, "remove is not throwing an exception correctly");
	}
	
	@Test
	public void testGet() {
		try {
			ArrayList<Integer> list = generateLists();
			
			for(int i=0; i<list.size(); ++i) {
				assertEquals("get is not working correctly", sll.get(i), list.get(i));
				assertEquals("get is not working correctly", size.get(sll), list.size());
				assertTrue("get is not working correctly", equalLists(list));
			}
		} catch(Exception e) {
			fail("get is not working correctly");
		}
	}
	
	@Test
	public void testGetIndexTooBig() {
		Executable statement = () -> sll.get(1);
		assertThrows(IndexOutOfBoundsException.class, statement, "get is not throwing an exception correctly");
	}
	
	@Test
	public void testGetIndexTooSmall() {
		Executable statement = () -> sll.get(-1);
		assertThrows(IndexOutOfBoundsException.class, statement, "get is not throwing an exception correctly");
	}
	
	@Test
	public void testIndexOf() {
		try {
			ArrayList<Integer> list = generateLists();
			Random rand = new Random();
			int randIndex;
			int randValue;
			for(int i=0; i<100; ++i) {
				randIndex = rand.nextInt(list.size());
				randValue = list.get(randIndex);
				assertEquals("indexOf is not working correctly", sll.indexOf(randValue), list.indexOf(randValue));
				assertEquals("indexOf is not working correctly", size.get(sll), list.size());
				assertTrue("indexOf is not working correctly", equalLists(list));
			}
			assertEquals("indexOf is not working correctly", list.indexOf(100), -1);
		} catch(Exception e) {
			fail("indexOf is not working correctly");
		}
	}
	
	@Test
	public void testToString() {
		try {
			ArrayList<Integer> list = new ArrayList<Integer>();
			assertEquals("toString is not working correctly", sll.toString(), list.toString());
			Object n = nodeConstructor.newInstance(sll, 37, null);
			next.set(n, null);
			first.set(sll, n);
			size.set(sll,  1);
			list.add(37);
			assertEquals("toString is not working correctly", sll.toString(), list.toString());
			list = generateLists();
			assertEquals("toString is not working correctly", sll.toString(), list.toString());
		} catch(Exception e) {
			fail("toString is not working correctly");
		}
	}
	
	private ArrayList<Integer> generateLists() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		try {
			Random rand = new Random();
			int randSize = rand.nextInt(10)+10;
			Object p = nodeConstructor.newInstance(sll, 0, null);
			first.set(sll, p);
			list.add(0);
			Object n;
			for(int i=1; i<=randSize; ++i) {
				//n is the most recently created node
				n = nodeConstructor.newInstance(sll, i, null);
				next.set(p, n);
				p = n;
				list.add(i);
			}
			size.set(sll, randSize+1);
			return list;
		} catch(Exception e) {
			return null;
		}
	}
	
	private boolean equalLists(ArrayList<Integer> a) {
		try {
			if((int)size.get(sll) != a.size()) {
				return false;
			}
			Object n = first.get(sll);
			int i=0;
			while(i<a.size()) {
				if(!a.get(i).equals(data.get(n))) {
					return false;
				}
				n = next.get(n);
				i++;
			}
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
