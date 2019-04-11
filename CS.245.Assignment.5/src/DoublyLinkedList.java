public class DoublyLinkedList<E> {

	private int size;
	private Node first;
	
	public void add(E value) {
		if(first == null || size == 0) {
			first = new Node(value, null, null);
			first.next = first;
			first.prev = first;
			size++;
		} else {
			Node n = new Node(value, first, first.prev);
			first.prev.next = n;
			first.prev = n;
			size++;
		}
	}
	
	public void add(int index, E value) {
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {

			
			if(index == 0 && first == null) {
				first = new Node(value, null, null);
				first.next = first;
				first.prev = first;
				size++;
			}else if(index == 0 && first != null) {
				
				Node n = new Node(value, first, first.prev);
				
				first.prev.next = n;
				first.prev = n;
				first = n;
				size++;
			} else {
				Node n = first;
				for(int i=0; i<index; i++) {
					n = n.next;
				}

				Node n2 = new Node(value, n, n.prev);
				
				n.prev.next = n2;
				n.prev = n2;
				size++;
			}
		}
		
	}
	
	public void remove(int index) {
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		} else {

			if(index == 0 && first != null && first.next == first) {
				first = null;
				
				size--;
			} else if(index == 0 && first != null && first.next != first) {
				first.prev.next = first.next;
				first.next.prev = first.prev;
				
				first = first.next;
				size--;
			} else {
				Node n = first;
				for(int i=0; i<index; i++) {
					n = n.next;
				}
				
				n.prev.next = n.next;
				n.next.prev = n.prev;
				
				size--;
			}
		}
	}
	
	public E get(int index) {
		
		if(index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		Node n = first;
			
		for(int i=0; i<index; i++) {
			n = n.next;
		}
			
		return n.data;

	}
	
	public int indexOf(E value) {
		Node n = first;
		int index = 0;
		while(n.data != value && n.next != first) {
			n = n.next;
			index++;
		}
		
		if(index < size) {
			return index;
		} else {
			return -1;
		}
	
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
				Node n = first;
				String data = "[";
				while(n.next != first) {
					data+=n.data+", ";
					n = n.next;
				}
				data+=n.data+"]";
				return data;
			}
			
		}
	}
	
	private class Node {
		
		private E data;
		private Node next;
		private Node prev;
		
		public Node(E data, Node next, Node prev) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
		
	}
	
}
