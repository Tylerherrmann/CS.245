import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;


public class BinarySearchTree<E extends Comparable<E>> {
	private BSTNode<E> root; // root of overall tree
    private int numElements;
    private BSTNode<E> first;

    // post: constructs an empty search tree
    public BinarySearchTree() {
        this.root = null;
        this.numElements = 0;
    }

    // post: value added to tree so as to preserve binary search tree
    public void add(E value) {
    	
    	this.root = add(root, value, null, null);
       
    }

    // post: value added to tree so as to preserve binary search tree
    private BSTNode<E> add(BSTNode<E> node, E value,BSTNode<E> parent, BSTNode<E> prev) {
        if (node == null) {
            
            
            if(parent != null && parent.data.compareTo(value) > 0) {
            	
            	if(getPrevNode(parent) == null) {
            		node = new BSTNode<E>(value);
            		node.next = parent;
            		first = node;
            	} else {
            		node = new BSTNode<E>(value);
            		node.next = parent;
            		getPrevNode(parent).next = node;
            	}
            	
            } else if(parent != null && parent.data.compareTo(value) < 0) { 
            	
            	if(parent.next == null) {
            		node = new BSTNode<E>(value);
            		parent.next = node;
            		node.next = null;
            	} else if(parent.next != null) {
            		node = new BSTNode<E>(value);
            		node.next = parent.next;
            		parent.next = node;
            	}
            	
            } else if (parent == null) {
            	node = new BSTNode<E>(value);
            	node.next = null;
            	node.parent = null;
            }
            
            // ASSIGN FIRST
    	    if(first == null) {
  	    	   first = node;
     	    }
    	    
    	    // INCREMENT numElements (NUM NUMS)
            this.numElements++;    
            
        } else if (node.data.compareTo(value) > 0) {
        	
        	if(getPrevNode(node) != null) {
        		node.left = add(node.left, value, node, getPrevNode(node));
        	} else {
        		node.left = add(node.left, value, node, null);
        	}
        	
        	node.left.parent = node;
            
        } else if (node.data.compareTo(value) < 0) {
        	
            node.right = add(node.right, value, node, node);
            node.right.parent = node;
    
        }
        return node;
    }

    // post: returns true if tree contains value, returns false otherwise
    public boolean contains(E value) {
        return contains(this.root, value);
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
    	this.root = remove(root, value);

        
        if(numElements > 0 && value.compareTo(first.data) <= 0) {
        	assignFirst();
        }
        
    }

    private BSTNode<E> remove(BSTNode<E> node, E value) {
    	
        if (node == null) {
            return null;
        } else if (node.data.compareTo(value) > 0) {
            node.left = remove(node.left, value);
        } else if (node.data.compareTo(value) < 0) {
            node.right = remove(node.right, value);
        } else {  // node.data == value; remove this node
        	
			if (node.right == null) {
            		numElements--;
            		
                    if(numElements == 0) {
                    	first = null;
                    }
            		
            		if(node.left != null) {
            			node.left.parent = node.parent;
            		} 
            		if(getPrevNode(node) != null) {
            			getPrevNode(node).next = node.next;
            		}
            		
            		
            		return node.left;    // no R child; replace w/ L
            } else if (node.left == null) {
            	
            	
            		numElements--;
            		
                    if(numElements == 0) {
                    	first = null;
                    }
            		
            		if(getPrevNode(node) != null && node.next != null) {
            			getPrevNode(node).next = node.next;
            		} else if(getPrevNode(node) != null && node.next == null) {
            			getPrevNode(node).next = null;
            		}
            		
            		if(node.right != null) {
            			node.right.parent = node.parent;
            		}
            		
            		
            		
            	return node.right;   // no L child; replace w/ R
            } else {
            	
                // both children; replace w/ max from L
                node.data = getMax(node.left);
                node.left = remove(node.left, node.data); 
            }
        }
        return node;
    }
    
    private E getMax(BSTNode<E> node) {
        if (node.right == null) {
            return node.data;
        } else {
            return getMax(node.right);
        }
    }

    public void clear() {
    	this.root = null;
    	this.first = null;
    	this.numElements = 0;
    }
    
    public boolean isEmpty() {
    	return this.numElements == 0;
    }
    
    public int size() {
    	return this.numElements;
    }
    
    @SuppressWarnings("unchecked")
	public E[] toArray() {
		ArrayList<E> aList = new ArrayList<E>();
		E[] arr = (E[]) new Comparable[this.numElements];
		toArray(this.root, aList);
		return aList.toArray(arr);
    }
    
    private void toArray(BSTNode<E> node, List<E> aList) {
		if (node != null) {
            toArray(node.left, aList);
            aList.add(node.data);
            toArray(node.right, aList);
        }
    }
    
	public void balance() {     
        root = buildTreeUtil(toArray(), 0, numElements-1, null);
		
        assignFirst();
	}
    
	public Iterator iterator() {
		return new Iterator();
	}
    
    public class Iterator {
		private Stack<BSTNode<E>> stack;
		
		private BSTNode<E> currentNode;
		
		public Iterator() {
			currentNode = first;
		}
 
		public boolean hasNext() {
			return currentNode != null;
		}
 
		public E next() {
			E value = currentNode.data;
			currentNode = currentNode.next;
			return value;
		}
    }
    
    private static class BSTNode<E> {
		public E data;
        public BSTNode<E> left;
        public BSTNode<E> right;
		public BSTNode<E> parent;
		public BSTNode<E> next;

        public BSTNode(E data) {
            this(data, null, null, null, null);
        }

        public BSTNode(E data, BSTNode<E> left, BSTNode<E> right, BSTNode<E> parent, BSTNode<E> next) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
            this.next = next;
        }
    }
    
    // HELPER METHODS
    
    private void assignFirst() {
    	
    	// The optional assignFirst method should find the node in the tree with the smallest value and 
    	//update the tree's "first" attribute accordingly.
    	
    	BSTNode<E> node = root;
    	
        while(node.left != null) {
        	node = node.left;
        } 

        first = node;
    }
    
    private BSTNode<E> getPrevNode(BSTNode<E> node) {

    	if(node.left != null) {
			node = node.left;
			while(node.right != null) {
				node = node.right;
			}
			return node;
		} else if(node.parent != null) {
			
			// If the node is a right child, return its parent
			if(node.parent.right == node) {
				return node.parent;
			}
			
			// If the node is a left child, move up the tree 
			// until you are a right child and return its parent
			if(node.parent.left == node) {
				
				while(node.parent.right != node) {
					
					// If you reach the root and are never a right child, no previous node return null
					if(node.parent == root) {
						return null;
					}
					node = node.parent;	
				}
				return node.parent;
				
			}			
		}
  
		return null;
	}
    
    private BSTNode<E> buildTreeUtil(E[] values, int start, int end, BSTNode<E> parent) {
    	 if (start > end) {
             return null;
         }
    	 
    	 int mid = (start + end) / 2;

    	 BSTNode<E> node = new BSTNode<E>(values[mid]);
    	 
 		 node.parent = parent;
 		 
  		 if(parent == null) {
 			 root = node;
   		 }
    	 
    	 node.left = buildTreeUtil(values, start, mid - 1, node);
    	 
    	 node.right = buildTreeUtil(values, mid+1, end, node);
    	 
 		
		if(node.parent != null && node.parent.data.compareTo(node.data) > 0 && node.right == null ) {
        	node.next = node.parent;
        } else if(node.parent != null && node.parent.data.compareTo(node.data) > 0 && node.right != null) {
        	
        	BSTNode<E> rightMostNode = node.right;
        		 
        	while(rightMostNode.right != null) {
        		rightMostNode = rightMostNode.right;
        	}
        		 
        	
        	rightMostNode.next = node.parent;
        }else if(node.parent != null && node.parent.data.compareTo(node.data) < 0 && node.left == null) {
        	node.parent.next = node; 
        } else if(node.parent != null && node.parent.data.compareTo(node.data) < 0 && node.left != null) {
        	
         BSTNode<E> leftMostNode = node.left;
         
         while(leftMostNode.left != null) {
         	leftMostNode = leftMostNode.left;
      	 }
      		node.parent.next = leftMostNode;
      	}  else {
			node.next = null;
   	 	}  
		
		if(node == root && node.right != null) {
   		 
			BSTNode<E> leftMostRightNode = node.right;;
			
   		 	while(leftMostRightNode.left != null) {
   		 		leftMostRightNode = leftMostRightNode.left;
   		 	}
   		 
       		node.next = leftMostRightNode;
		}
    	 
    	 return node;
    	 
    	 
    }
    


}
