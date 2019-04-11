import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree<E extends Comparable<E>> {
    private BSTNode<E> root; // root of overall tree
    private int numElements;

    // post: constructs an empty search tree
    public BinarySearchTree() {
        root = null;
    }

    // post: value added to tree so as to preserve binary search tree
    public void add(E value) {
        root = add(root, value);
    }

    // post: value added to tree so as to preserve binary search tree
    private BSTNode<E> add(BSTNode<E> node, E value) {
        if (node == null) {
            numElements++;
            node = new BSTNode<E>(value);
        } else if(node.data.compareTo(value) == 0) {
        	
        }
        else if (node.data.compareTo(value) > 0) {
            node.left = add(node.left, value);
        } else if (node.data.compareTo(value) < 0) {
            node.right = add(node.right, value);
        }
        return node;
    }

    // post: returns true if tree contains value, returns false otherwise
    public boolean contains(E value) {
        return contains(root, value);
    }   

    // post: returns true if given tree contains value, returns false otherwise
    private boolean contains(BSTNode<E> node, E value) {
        if (node == null) {
            return false;
        } else {
            int compare = value.compareTo(node.data);
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                return contains(node.left, value);
            } else {   // compare > 0
                return contains(node.right, value);
            }
        }
    }
    
    public void remove(E value) {
        root = remove(root, value);
    }
    
    private BSTNode<E> remove(BSTNode<E> node, E value) {
	    if (node == null) {
	        return null;
	    } else if (node.data.compareTo(value) > 0) {
	    	node.left = remove(node.left, value);
	    } else if (node.data.compareTo(value) < 0) {
	    	node.right = remove(node.right, value);
	    } else {  // root.data == value; remove this node
	        if (node.right == null) {
	        	numElements--;
	            return node.left;    // no R child; replace w/ L
	        } else if (node.left == null) {
	        	numElements--;
	            return node.right;   // no L child; replace w/ R
	        } else {
	        	node.data = getMax(node.left);
	        	node.left = remove(node.left, node.data);
	        }
	    }
	    return node;    
    }
    
    private E getMax(BSTNode<E> node) {
		if(node.right == null) {
			return node.data;
		} else {
			return getMax(node.right);
		}
    }
    
    public void clear() {
    	root = null;
    	numElements = 0;
    }
    
    public boolean isEmpty() {
		if(numElements <= 0) {
			return true;
		} else {
			return false;
		}
    }
    
    public int size() {
		return numElements;
    }
    
    public E[] toArray() {
    	List<E> arrayList = new ArrayList<E>();

    	Object[] nodeArray = new Comparable[numElements];
    	
		toArray(root, arrayList);
		
		 nodeArray = arrayList.toArray(nodeArray);
		 
		 return (E[]) nodeArray;
    }
    
    private void toArray(BSTNode<E> node, List<E> aList) {
        if (node == null) {
        	return;
        } 
        
        toArray(node.left, aList);
        
        aList.add(node.data);

        toArray(node.right, aList);
    }
    
    public Iterator<E> iterator() {
		Iterator<E> iterator = new Iterator(root);
		
		return iterator;
    }
    
    static class Iterator<E>{
    	private Stack<BSTNode<E>> stack;
		 
		public Iterator(BSTNode<E> node) {
			stack = new Stack<BSTNode<E>>();
			while (node != null) {
				stack.push(node);
				node = node.left;
			}
		}
	 
		public boolean hasNext() {
			return !stack.isEmpty();
		}
	 
		public E next() {
			BSTNode<E> node = stack.pop();
			E result = node.data;
			if (node.right != null) {
				node = node.right;
				while (node != null) {
					stack.push(node);
					node = node.left;
				}
			}
			return result;
		}
		
    	
    }

    private static class BSTNode<E> {
        public E data;
        public BSTNode<E> left;
        public BSTNode<E> right;

        public BSTNode(E data) {
            this(data, null, null);
        }

        public BSTNode(E data, BSTNode<E> left, BSTNode<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

}
