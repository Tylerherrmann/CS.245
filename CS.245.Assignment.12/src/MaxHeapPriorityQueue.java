import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MaxHeapPriorityQueue<E extends Comparable<E>> {

	   private E[] elementData;
	    private int size;
	    
	    @SuppressWarnings("unchecked")
	    public MaxHeapPriorityQueue() {
	        elementData = (E[]) new Comparable[10];
	        size = 0;
	    }
	    
	    public void add(E value) {
	        if (size + 1 >= elementData.length) {
	            elementData = Arrays.copyOf(elementData, elementData.length * 2);
	        }
	        
	        elementData[size + 1] = value;

	        int index = size + 1;
	        boolean found = false;
	        while (!found && hasParent(index)) {
	            int parent = parent(index);
	            if (elementData[index].compareTo(elementData[parent]) > 0) {
	                swap(elementData, index, parent(index));
	                index = parent(index);
	            } else {
	                found = true;
	            }
	        }
	        
	        size++;
	    }

	    public boolean isEmpty() {
	        return size == 0;
	    }

	    public E peek() {
	        if (isEmpty()) {
	            throw new NoSuchElementException();
	        }
	        return elementData[1];
	    }

	    public E remove() {
	        E result = peek();

	        elementData[1] = elementData[size];
	        size--;

	        int index = 1;
	        boolean found = false;   
	        while (!found && hasLeftChild(index)) {
	            int left = leftChild(index);
	            int right = rightChild(index);
	            int child = left;
	            if (hasRightChild(index) &&
	                    elementData[right].compareTo(elementData[left]) > 0) {
	                child = right;
	            }
	            
	            if (elementData[index].compareTo(elementData[child]) < 0) {
	                swap(elementData, index, child);
	                index = child;
	            } else {
	                found = true; 
	            }
	        }
	        
	        return result;
	    }
	    

	    public int size() {
	        return size;
	    }
	    
	    public void clear() {
	    	size = 0;
	    }
	    
	    public boolean contains(Object value) {
			return contains(value, 1);
		}
	    
	    public Object[] toArray() {
	    	int index = 0;
	    	Object[] array = new Object[size];

	    	for(int i = 0; i < size+1; i++) {
	    		if(elementData[i] != null) {
	    			array[index] = elementData[i];
	    			index++;
	    		}
	    	}
	    	
			return array;
	    }
	    
	    public Iterator<E> iterator() {
			return new MHPQIterator();
	    }
	    

	    public String toString() {
	        String result = "[";
	        if (!isEmpty()) {
	            result += elementData[1];
	            for (int i = 2; i <= size; i++) {
	                result += ", " + elementData[i];
	            }
	        }
	        return result + "]";
	    }
	    
	    public static void heapSort(Comparable[] a, int size) {
	    	MaxHeapPriorityQueue mhpq = new MaxHeapPriorityQueue();
	    	mhpq.elementData = a;
	    	
	    	
	    	mhpq.size = size;
	    	
	    	for(int i=((mhpq.size-1)/2); i>=0; --i) {
	    		mhpq.bubbleDown(i);
	    	}
	    	
	    	while(!mhpq.isEmpty()) {
	    		mhpq.elementData[mhpq.size-1] = mhpq.sortRemove();
	    	}
	    	
	    }
	    
	    private void bubbleDown(int index) {
	        boolean found = false;   
	        while (!found && hasLeftChild(index)) {

	        	if(index == 0) {
		            int left = 1;
		            int right = 2;
		            int child = left;
		            System.out.println("size: " + size);
		            System.out.println("index: " + index);
		            if (3 <= size &&
		                    elementData[right].compareTo(elementData[left]) > 0) {
		                child = right;
		            }
		            
		            if (2 <= size && elementData[index].compareTo(elementData[child]) < 0) {
		                swap(elementData, index, child);
		                index = child;
		            } else {
		                found = true; 
		            }
	        	}

	        	if(index != 0) {
		            int left = (index * 2) + 1;
		            int right = (index * 2) + 2;
		            int child = left;
		            if (right <= (size-1) &&
		                    elementData[right].compareTo(elementData[left]) > 0) {
		                child = right;
		            }
		            
		            if (left <= (size-1) && elementData[index].compareTo(elementData[child]) < 0) {
		                swap(elementData, index, child);
		                index = child;
		            } else {
		                found = true; 
		            }
	        	}
	        }
	    }
	    
	    private E sortRemove() {
			E result = elementData[0];
			
			elementData[0] = elementData[size-1];
			size--;
			
			bubbleDown(0);
			
			return result;
	    }
	    
	    private boolean contains(Object value, int index) {
	    	for(int i = 0; i < size+1; i++) {
	    		if(elementData[i] == value) {
	    			return true;
	    		}
	    	}
	    	return false;
	    }

	    private int parent(int index) {
	        return index / 2;
	    }

	    private int leftChild(int index) {
	        return index * 2;
	    }

	    private int rightChild(int index) {
	        return index * 2 + 1;
	    }

	    private boolean hasParent(int index) {
	        return index > 1;
	    }

	    private boolean hasLeftChild(int index) {
	        return leftChild(index) <= size;
	    }
	    

	    private boolean hasRightChild(int index) {
	        return rightChild(index) <= size;
	    }

	    private void swap(E[] a, int index1, int index2) {
	        E temp = a[index1];
	        a[index1] = a[index2];
	        a[index2] = temp;
	    }
	    

	    public class MHPQIterator implements Iterator<E> {
			int index;
	    	public boolean hasNext()  {
				if(index+1 <= size) {
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	}
	    	
	    	public E next() {
				if(index+1 <= size) {
					index++;
		    		return elementData[index];
		    	}
				return null;
	    	}
	
	    }
}

