import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> {

	public final static int DEFAULT_CAPACITY = 10; 
	private E[] elementData;
	private int size;
	
	public Stack() {
		this(DEFAULT_CAPACITY);
	}
	
	public Stack(int capacity) {
		if(capacity >= 0) {
			
			elementData = (E[]) (new Object[capacity]);
					
		} else {
			throw new IllegalArgumentException("Negative value");
		}
	}
	
	public void push(E item) {
		ensureCapacity(size+1);
		elementData[size] = item;
		size++;
	}
	
	public E pop() {
		if(size > 0) {
			E value = elementData[size-1];
			size--;
			return value;
		} else {
			throw new EmptyStackException();
		}

	}
	
	public E peek() {
		if(size > 0) {
			E value = elementData[size-1];
			return value;		
		} else {
			throw new EmptyStackException();
		}
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
	
	public boolean isEmpty() {
		return size == 0;
	}
	
}
