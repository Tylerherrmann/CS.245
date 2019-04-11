import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class MaxHeapPriorityQueueTests {
	
	private MaxHeapPriorityQueue<Integer> mhpq;
	private Field elementData;
	private Field size;

	@BeforeEach
	void setUp() throws Exception {
		mhpq = new MaxHeapPriorityQueue<Integer>();
		elementData = MaxHeapPriorityQueue.class.getDeclaredField("elementData");
		elementData.setAccessible(true);
		size = MaxHeapPriorityQueue.class.getDeclaredField("size");
		size.setAccessible(true);
	}

	@Test
	public void testFields() {
		assertEquals("MaxHeapPriorityQueue should only have \"elementData\" and \"size\" fields", MaxHeapPriorityQueue.class.getDeclaredFields().length, 2);
	}
	
	@Test
	public void testConstructor() {
		try {
			assertEquals("MaxHeapPriorityQueue constructor is not working correctly", ((Object[])elementData.get(mhpq)).length, 10);
			assertEquals("MaxHeapPriorityQueue constructor is not working correctly", size.get(mhpq), 0);
		} catch (Exception e) {
			fail("MaxHeapPriorityQueue constructor is not working correctly");
		}
	}
	
	@Test
	public void testIsEmpty() {
		try {
			assertEquals("isEmpty is not working correctly", mhpq.isEmpty(), true);
			size.set(mhpq, 1);
			assertEquals("isEmpty is not working correctly", mhpq.isEmpty(), false);
		} catch (Exception e) {
			fail("isEmpty is not working correctly");
		}
	}
	
	@Test
	public void testSize() {
		try {
			assertEquals("size method is not working correctly", mhpq.size(), 0);
			size.set(mhpq, 1);
			assertEquals("size method is not working correctly", mhpq.size(), 1);
		} catch (Exception e) {
			fail("size method is not working correctly");
		}
	}
	
	@Test
	public void testClear() {
		try {
			size.set(mhpq, 8);
			Integer[] data = {null,8,7,6,5,4,3,2,1,null};
			elementData.set(mhpq, data);
			mhpq.clear();
			assertEquals("clear method is not working correctly", size.get(mhpq), 0);
		} catch (Exception e) {
			fail("clear method is not working correctly");
		}
	}

	@Test
	public void testPeek() {
		try {
			size.set(mhpq, 8);
			Integer[] data = {null,8,7,6,5,4,3,2,1,null};
			elementData.set(mhpq, data);
			assertEquals("peek method is not working correctly", mhpq.peek(), new Integer(8));
			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			assertEquals("peek method is not working correctly", mhpq.peek(), new Integer(randVal));
		} catch (Exception e) {
			fail("peek method is not working correctly");
		}
	}
	
	@Test
	public void testEmptyPeek() {
		Executable statement = () -> mhpq.peek();
		assertThrows(NoSuchElementException.class, statement, "peek is not throwing an exception correctly");
	}
	
	@Test
	public void testAdd() {
		try {
			Integer[] data = {null,5,null,null,null,null,null,null,null,null};
			mhpq.add(5);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data);
			assertEquals("add method is not working correctly", size.get(mhpq), 1);
			
			Integer[] data2 = {null,5,3,null,null,null,null,null,null,null};
			mhpq.add(3);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data2);
			assertEquals("add method is not working correctly", size.get(mhpq), 2);
			
			Integer[] data3 = {null,5,3,2,null,null,null,null,null,null};
			mhpq.add(2);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data3);
			assertEquals("add method is not working correctly", size.get(mhpq), 3);
			
			Integer[] data4 = {null,7,5,2,3,null,null,null,null,null};
			mhpq.add(7);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data4);
			assertEquals("add method is not working correctly", size.get(mhpq), 4);
			
			Integer[] data5 = {null,7,5,2,3,4,null,null,null,null};
			mhpq.add(4);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data5);
			assertEquals("add method is not working correctly", size.get(mhpq), 5);
			
			Integer[] data6 = {null,7,5,4,3,4,2,null,null,null};
			mhpq.add(4);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data6);
			assertEquals("add method is not working correctly", size.get(mhpq), 6);
			
			Integer[] data7 = {null,8,5,7,3,4,2,4,null,null};
			mhpq.add(8);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data7);
			assertEquals("add method is not working correctly", size.get(mhpq), 7);
			
			Integer[] data8 = {null,10,8,7,5,4,2,4,3,null};
			mhpq.add(10);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data8);
			assertEquals("add method is not working correctly", size.get(mhpq), 8);
			
			Integer[] data9 = {null,10,9,7,8,4,2,4,3,5};
			mhpq.add(9);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data9);
			assertEquals("add method is not working correctly", size.get(mhpq), 9);
			
			Integer[] data10 = {null,10,9,7,8,4,2,4,3,5,1,null,null,null,null,null,null,null,null,null};
			mhpq.add(1);
			assertArrayEquals("add method is not working correctly", (Comparable[])elementData.get(mhpq), data10);
			assertEquals("add method is not working correctly", size.get(mhpq), 10);
		} catch (Exception e) {
			fail("add method is not working correctly");
		}
	}
	
	@Test
	public void testRemove() {
		try {
			Integer[] data = {null,10,9,7,8,4,2,4,3,5};
			elementData.set(mhpq, data);
			size.set(mhpq, 9);
			
			Integer[] data9 = {null,9,8,7,5,4,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(10));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data9);
			assertEquals("remove method is not working correctly", size.get(mhpq), 8);
			
			Integer[] data8 = {null,8,5,7,3,4,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(9));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data8);
			assertEquals("remove method is not working correctly", size.get(mhpq), 7);
			
			Integer[] data7 = {null,7,5,4,3,4,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(8));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data7);
			assertEquals("remove method is not working correctly", size.get(mhpq), 6);
			
			Integer[] data6 = {null,5,4,4,3,2,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(7));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data6);
			assertEquals("remove method is not working correctly", size.get(mhpq), 5);
			
			Integer[] data5 = {null,4,3,4,2,2,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(5));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data5);
			assertEquals("remove method is not working correctly", size.get(mhpq), 4);
			
			Integer[] data4 = {null,4,3,2,2,2,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(4));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data4);
			assertEquals("remove method is not working correctly", size.get(mhpq), 3);
			
			Integer[] data3 = {null,3,2,2,2,2,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(4));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data3);
			assertEquals("remove method is not working correctly", size.get(mhpq), 2);
			
			Integer[] data2 = {null,2,2,2,2,2,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(3));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data2);
			assertEquals("remove method is not working correctly", size.get(mhpq), 1);
			
			Integer[] data1 = {null,2,2,2,2,2,2,4,3,5};
			assertEquals("remove method is not working correctly", mhpq.remove(), new Integer(2));
			assertArrayEquals("remove method is not working correctly", (Comparable[])elementData.get(mhpq), data1);
			assertEquals("remove method is not working correctly", size.get(mhpq), 0);
		} catch (Exception e) {
			fail("remove method is not working correctly");
		}
	}
	
	@Test
	public void testEmptyRemove() {
		Executable statement = () -> mhpq.remove();
		assertThrows(NoSuchElementException.class, statement, "remove is not throwing an exception correctly");
	}
	
	@Test
	public void testContains() {
		try {
			assertFalse("contains method is not working correctly", mhpq.contains(0));
			
			Integer[] data = {null,10,9,7,8,4,2,4,3,5,1,11,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data);
			size.set(mhpq, 10);
			
			assertTrue("contains method is not working correctly", mhpq.contains(10));
			assertTrue("contains method is not working correctly", mhpq.contains(9));
			assertTrue("contains method is not working correctly", mhpq.contains(7));
			assertTrue("contains method is not working correctly", mhpq.contains(8));
			assertTrue("contains method is not working correctly", mhpq.contains(4));
			assertTrue("contains method is not working correctly", mhpq.contains(2));
			assertTrue("contains method is not working correctly", mhpq.contains(3));
			assertTrue("contains method is not working correctly", mhpq.contains(5));
			assertTrue("contains method is not working correctly", mhpq.contains(1));
			assertFalse("contains method is not working correctly", mhpq.contains(11));
			assertArrayEquals("contains method is not working correctly", (Comparable[])elementData.get(mhpq), data);
			assertEquals("contains method is not working correctly", size.get(mhpq), 10);
		} catch (Exception e) {
			fail("contains method is not working correctly");
		}
	}
	
	@Test
	public void testToString() {
		try {
			assertEquals("toString method is not working correctly", mhpq.toString(), "[]");
			
			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			assertEquals("toString method is not working correctly", mhpq.toString(), "["+randVal+"]");
			
			size.set(mhpq, 9);
			Integer[] data = {null,8,6,7,5,4,3,2,1,0};
			elementData.set(mhpq, data);
			assertEquals("toString method is not working correctly", mhpq.toString(), "[8, 6, 7, 5, 4, 3, 2, 1, 0]");
		} catch (Exception e) {
			fail("toString method is not working correctly");
		}
	}
	
	@Test
	public void testToArray() {
		try {
			Integer[] a = new Integer[0];
			assertArrayEquals("toArray method is not working correctly", mhpq.toArray(), a);
			
			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			Integer[] a2 = {randVal};
			assertArrayEquals("toArray method is not working correctly", mhpq.toArray(), a2);
			
			size.set(mhpq, 9);
			Integer[] data = {null,8,6,7,5,4,3,2,1,0};
			elementData.set(mhpq, data);
			Integer[] a3 = {8,6,7,5,4,3,2,1,0};
			assertArrayEquals("toArray method is not working correctly", mhpq.toArray(), a3);
		} catch (Exception e) {
			fail("toArray method is not working correctly");
		}
	}
	
	@Test
	public void testIterator() {
		try {
			Iterator<Integer> itr = mhpq.iterator();
			assertFalse("your iterator is not working correctly",itr.hasNext());
			
			size.set(mhpq, 1);
			Random r = new Random();
			Integer randVal = r.nextInt(10);
			Integer[] data2 = {null,randVal,null,null,null,null,null,null,null,null};
			elementData.set(mhpq, data2);
			itr = mhpq.iterator();
			assertTrue("your iterator is not working correctly",itr.hasNext());
			assertEquals("your iterator is not working correctly", itr.next(), new Integer(randVal));
			assertFalse("your iterator is not working correctly",itr.hasNext());
			
			size.set(mhpq, 9);
			Integer[] data = {null,8,7,6,5,4,3,2,0,1};
			elementData.set(mhpq, data);
			itr = mhpq.iterator();
			for(int i=8; i>=2; --i) {
				assertTrue("your iterator is not working correctly",itr.hasNext());
				assertEquals("your iterator is not working correctly", itr.next(), new Integer(i));
			}
			assertTrue("your iterator is not working correctly",itr.hasNext());
			assertEquals("your iterator is not working correctly", itr.next(), new Integer(0));
			assertTrue("your iterator is not working correctly",itr.hasNext());
			assertEquals("your iterator is not working correctly", itr.next(), new Integer(1));
			assertFalse("your iterator is not working correctly",itr.hasNext());
		} catch (Exception e) {
			fail("your iterator is not working correctly");
		}
	}
	
	@Test
	public void testHeapSort() {
		try {
			Integer[] a = new Integer[0];
			MaxHeapPriorityQueue.heapSort(a, 0);
			assertArrayEquals("heapSort method is not working correctly", new Integer[0], a);
			
			Integer[] a1 = {5};
			Integer[] a1s = {5};
			MaxHeapPriorityQueue.heapSort(a1, 1);
			assertArrayEquals("heapSort method is not working correctly", a1s, a1);
			
			Integer[] a2 = {5,3};
			Integer[] a2s = {3,5};
			MaxHeapPriorityQueue.heapSort(a2, 2);
			assertArrayEquals("heapSort method is not working correctly", a2s, a2);
			
			Integer[] a3 = {5,3,2};
			Integer[] a3s = {2,3,5};
			MaxHeapPriorityQueue.heapSort(a3, 3);
			assertArrayEquals("heapSort method is not working correctly", a3s, a3);
			
			Integer[] a4 = {5,3,2,7};
			Integer[] a4s = {2,3,5,7};
			MaxHeapPriorityQueue.heapSort(a4, 4);
			assertArrayEquals("heapSort method is not working correctly", a4s, a4);
			
			Integer[] a5 = {5,3,2,7,4};
			Integer[] a5s = {2,3,4,5,7};
			MaxHeapPriorityQueue.heapSort(a5, 5);
			assertArrayEquals("heapSort method is not working correctly", a5s, a5);
			
			Integer[] a6 = {5,3,2,7,4,4};
			Integer[] a6s = {2,3,4,4,5,7};
			MaxHeapPriorityQueue.heapSort(a6, 6);
			assertArrayEquals("heapSort method is not working correctly", a6s, a6);
			
			Integer[] a7 = {5,3,2,7,4,4,8};
			Integer[] a7s = {2,3,4,4,5,7,8};
			MaxHeapPriorityQueue.heapSort(a7, 7);
			assertArrayEquals("heapSort method is not working correctly", a7s, a7);
			
			Integer[] a8 = {5,3,2,7,4,4,8,10};
			Integer[] a8s = {2,3,4,4,5,7,8,10};
			MaxHeapPriorityQueue.heapSort(a8, 8);
			assertArrayEquals("heapSort method is not working correctly", a8s, a8);
			
			Integer[] a9 = {5,3,2,7,4,4,8,10,9};
			Integer[] a9s = {2,3,4,4,5,7,8,9,10};
			MaxHeapPriorityQueue.heapSort(a9, 9);
			assertArrayEquals("heapSort method is not working correctly", a9s, a9);
			
			Integer[] a10 = {5,3,2,7,4,4,8,10,9,1};
			Integer[] a10s = {1,2,3,4,4,5,7,8,9,10};
			MaxHeapPriorityQueue.heapSort(a10, 10);
			assertArrayEquals("heapSort method is not working correctly", a10s, a10);
			
			Random r = new Random();
			int randVal = r.nextInt(10);
			Integer[] a102 = {5,3,2,7,4,4,8,10,9,1,randVal};
			Integer[] a102s = {1,2,3,4,4,5,7,8,9,10,randVal};
			MaxHeapPriorityQueue.heapSort(a102, 10);
			assertArrayEquals("heapSort method is not working correctly", a102s, a102);

		} catch (Exception e) {
			e.printStackTrace();
			fail("heapSort method is not working correctly");
		}
	}
}
