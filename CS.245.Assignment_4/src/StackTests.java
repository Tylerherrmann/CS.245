import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class StackTests {

	private Stack<Integer> s;
	private Field elementData;
	private Field size;
	private Method ensureCapacity;
	
	@BeforeEach
	public void setUp() throws Exception {
		s = new Stack<Integer>();
		elementData = Stack.class.getDeclaredField("elementData");
		elementData.setAccessible(true);
		size = Stack.class.getDeclaredField("size");
		size.setAccessible(true);
		ensureCapacity = Stack.class.getDeclaredMethod("ensureCapacity", int.class);
		ensureCapacity.setAccessible(true);
	}
	
	@Test
	public void testDefaultConstructor() {
		try {
			assertEquals("Stack() constructor is not working correctly", size.get(s), 0);
			assertEquals("Stack() constructor is not working correctly",  ((Object[]) elementData.get(s)).length, Stack.DEFAULT_CAPACITY);
		}  catch (Exception e) {
			fail("Stack() constructor and/or instance variables not found");
		}
	}
	
	@Test
	public void testConstructor() {
		try {
			Random rand = new Random();
			int capacity = rand.nextInt(50);
			s= new Stack<Integer>(capacity);
			assertEquals("Stack(int) constructor is not working correctly", size.get(s), 0);
			assertEquals("Stack(int) constructor is not working correctly",  ((Object[]) elementData.get(s)).length, capacity);
		}  catch (Exception e) {
			fail("Stack(int) constructor and/or instance variables not found");
		}
	}
	
	@Test
	public void testConstructorInvalidArgument() {
		Random rand = new Random();
		int capacity = rand.nextInt(49) + 1;
		Executable statement = () -> new Stack<Integer>(-capacity);
		assertThrows(IllegalArgumentException.class, statement, "Stack(int) constructor is not working correctly");
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
	public void testPush() {
		try {
			Random rand = new Random();
			int numElements = rand.nextInt(9) + 11;
			ArrayList<Integer> expectedElements = new ArrayList<Integer>();
			int randElement;
			for(int i=0; i<numElements; ++i) {
				randElement = rand.nextInt(50) + 1;
				expectedElements.add(randElement);
				s.push(randElement);
				assertEquals("push is not working correctly", size.get(s), i+1);
			}
			for(int i=0; i<numElements; ++i) {
				assertEquals("push is not working correctly", ((Object[])elementData.get(s))[i], expectedElements.get(i));
			}
		} catch (Exception e) {
			fail("push is not working correctly");
		}
	}

	@Test
	public void testPop() {
		Random rand = new Random();
		int numElements = rand.nextInt(9) + 1;
		Integer[] setElementData = new Integer[10];
		ArrayList<Integer> expectedElements = new ArrayList<Integer>();
		int randElement;
		for(int i=0; i<numElements; ++i) {
			randElement = rand.nextInt(50) + 1;
			setElementData[i] = randElement;
			expectedElements.add(randElement);
		}
		try {
			elementData.set(s, setElementData);
			size.set(s, numElements);
			for(int i=0; i<numElements; ++i) {
				assertEquals("pop is not working correctly", s.pop(), expectedElements.get(numElements-i-1));
				assertEquals("pop is not working correctly", size.get(s), numElements-i-1);
			}
		} catch (Exception e) {
			fail("pop is not working correctly");
		}
	}
	
	@Test
	public void testPopEmptyStack() {
		Executable statement = () -> s.pop();
		assertThrows(EmptyStackException.class, statement, "pop is not working correctly");
	}
	
	@Test
	public void testPeek() {
		Random rand = new Random();
		int numElements = rand.nextInt(8) + 2;
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
			assertEquals("peek is not working correctly", s.peek(), setElementData[numElements-1]);
			assertEquals("peek is not working correctly", size.get(s), numElements);
			assertEquals("peek is not working correctly", s.peek(), setElementData[numElements-1]);
			assertEquals("peek is not working correctly", size.get(s), numElements);
		} catch (Exception e) {
			fail("pop is not working correctly");
		}
	}
	
	@Test
	public void testPeekEmptyStack() {
		Executable statement = () -> s.peek();
		assertThrows(EmptyStackException.class, statement, "peek is not working correctly");
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
			s = new Stack<Integer>(capacity);
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
}
