public class SortedLinkedList<E extends Comparable<E>> {

	private Node first;
	private int size;
	
	public void add(E value) {
		
			if(first == null || size == 0) {
				first = new Node(value, null);
				size++;
			} else if(first.data.compareTo(value) >= 0) {
				 Node n = new Node(value, first);
				 first = n;
				 size++;
			}else {
				add(value, first);
				size++;	
			}
	}
	
	public void remove(int index) {
		if(index > size-1 || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {
			remove(index, 0, first);
			size--;
		}
	}
	
	public E get(int index) {
		if(index > size-1 || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {
			return get(index,0,first);
		}	
	}
	
	public int indexOf(E value) {
		return indexOf(value, 0, first);
	}
	
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public int size() {
		return size;
	}
	
	public String toString() {

		
		if(size == 0 && first == null) {
			return "[]";
		} else {
			if(size == 1 && first != null) {
				return "[" + first.data + "]";
			} else {
							
				return "[" + toString(first) + "]";
			}
		}
	}
	
	private void add(E value, Node n) {
		
		if(n.next == null) {
			Node n2 = new Node(value, null);
			n.next = n2;
		}else if(n.next.data.compareTo(value) == 0) {
			Node n2 = new Node(value, n.next);
			n.next = n2;
		}else if(n.next.data.compareTo(value) == 1) {
			Node n2 = new Node(value, n.next);
			n.next = n2;
		}else if(n.next.data.compareTo(value) == -1) {
			n = n.next;
			add(value, n);
		}
		
		}
		
	
	private void remove(int index, int currentIndex, Node n) {
		
		
		if(index == 0 && size == 1) {
			first = null;
		}else if(index == 0 && size > 1) {
			first = first.next;
		}else if(index-1 == currentIndex) {
			n.next = n.next.next;
		} else if(currentIndex < index) {
			currentIndex++;
			n = n.next;
			remove(index, currentIndex, n);
		}
	}
	
	private E get(int index, int currentIndex, Node n) {

		
		if(index == currentIndex) {
			return n.data;
		} else if(currentIndex < index) {
			currentIndex++;
			n = n.next;
			return get(index, currentIndex, n);
		}
		
		
		return n.data;
		
		
	}
	
	private int indexOf(E value, int currentIndex, Node n) {
		if(n.data == value) {
			return currentIndex;
		} else if(n.next != null) {
			n = n.next;
			currentIndex++;
			return indexOf(value, currentIndex, n);
		} else {
			return -1;
		}
	}
	
	private String toString(Node n) {
		
			if(n.next != null) {
				String data = n.data+", ";
				n = n.next;
				return data + toString(n);
			}
		
			
			return ""+n.data;
	}
	
	private class Node {
		
		private E data;
		private Node next;
		
		public Node(E data, Node next) {
			this.data = data;
			this.next = next;
		}
		
	}
	
}
