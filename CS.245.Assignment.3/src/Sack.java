import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Sack<E> {

	private E[] elementData;
	private int size;
	public static final int DEFAULT_CAPACITY = 10;
	
	public Sack() {
		elementData = (E[]) (new Object[DEFAULT_CAPACITY]);
	}
	
	public Sack(int capacity) {
		if(capacity >= 0) {
			
			elementData = (E[]) (new Object[capacity]);
					
		} else {
			throw new IllegalArgumentException("Negative value");
		}
		
	}
	
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void add(E item) {
		ensureCapacity(size+1);
		elementData[size] = item;
		size++;
	}
	
	public E grab() {
	
		
		if(size > 0) {
			Random rand = new Random();
			int randNum = rand.nextInt(elementData.length) + 0;
			if(elementData[randNum] != null) {
				E value = elementData[randNum];
				
				for(int i=randNum; i<randNum-1; i++) {
					elementData[i] = elementData[i+1];
				}
				
				elementData[size-1] = null;
				size--;
				return value;
			} else {
				return null;
			}
		} else {
			return null;
		}
		
	}
	
	public E[] dump() {
		
		E[] newArray = (E[]) (new Object[size]);
	
		for(int i=0; i<size; i++) {
					
			newArray[i] = elementData[i];
			
			elementData[i] = null;
		}
		
		size = 0;
		
		return newArray;
		
	}
	
	private void ensureCapacity(int capacity) {
		if(capacity > this.elementData.length) {
			int newCapacity = 2 * this.elementData.length + 1;
			if(capacity > newCapacity) {
				newCapacity = capacity;
			}
			this.elementData = Arrays.copyOf(this.elementData, newCapacity);
		}
	}
	
	private void remove(int index) {
		if(index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index);
		}
		
		for(int i=index; i<size-1; i++) {
			elementData[i] = elementData[i+1];
		}
		elementData[size-1] = null;	
		size--;
	}
	
}
