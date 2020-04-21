

/**
 * 
 * @author andrei portales 19825, 
 * Referencias de condigo =  https://algs4.cs.princeton.edu/33balanced/SplayBST.java.html
 *
 * @param <K>
 * @param <V>
 */

public class SplayTree<K extends Comparable<K>, V extends Comparable<V>> implements BST<K,V>{
	
	private NodoA<K,V> root;

    public SplayTree() {
        root = null;
    }
/*
	@Override
	public void insert( K key, V value) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public V buscar( K key) {
		// TODO Auto-generated method stub
		return find(key);
	}
	
	
	@Override
	 public void insert(K key, V value) {
		NodoA<K,V> n;
		int c;
		if (root == null) {
			   root = new NodoA<K,V>(key,value);
			   return;
		}
		splay(key);
		if ((c = key.compareTo(root.key)) == 0) {
			   //	    throw new DuplicateItemException(x.toString());	    
			return;
		}
		n = new NodoA<K,V>(key,value);
		if (c < 0) {
			   n.left = root.left;
			   n.right = root;
			   root.left = null;
		} else {
			   n.right = root.right;
			   n.left = root;
			   root.right = null;
		}
		root = n;
	}	
	
	 
	 
	 public void remove(K key) {
			NodoA<K,V> x;
			splay(key);
			if (key.compareTo(root.key) != 0) {
			    //            throw new ItemNotFoundException(x.toString());
			    return;
			}
			// Now delete the root
			if (root.left == null) {
			    root = root.right;
			} else {
			    x = root.right;
			    root = root.left;
			    splay(key);
			    root.right = x;
			}
	 }
	 
	 public V findMin() {
	        NodoA<K,V> x = root;
	        if(root == null) return null;
	        while(x.left != null) x = x.left;
	        splay(x.key);
	        return x.value;
	    }

	    /**
	     * Find the largest item in the tree.
	     */
	    public V findMax() {
	    	NodoA<K,V> x = root;
	        if(root == null) return null;
	        while(x.right != null) x = x.right;
	        splay(x.key);
	        return x.value;
	    }

	    /**
	     * Find an item in the tree.
	     */
	    public V find(K key) {
		if (root == null) return null;
		splay(key);
	        if(root.key.compareTo(key) != 0) return null;
	        return root.value;
	    }
	    
	    public boolean isEmpty() {
	        return root == null;
	    }
	
	    
		private void moveToRoot(K key) {
	    	NodoA<K,V> l, r, t, y;
	    	l = r = header;
	    	t = root;
	    	header.left = header.right = null;
	    	for (;;) {
	    	    if (key.compareTo(t.key) < 0) {
	    		if (t.left == null) break;
	    		r.left = t;                                 /* link right */
	    		r = t;
	    		t = t.left;
	    	    } else if (key.compareTo(t.key) > 0) {
	    		if (t.right == null) break;
	    		l.right = t;                                /* link left */
	    		l = t;
	    		t = t.right;
	    	    } else {
	    		break;
	    	    }
	    	}
	    	l.right = t.left;                                   /* assemble */
	    	r.left = t.right;
	    	t.left = header.right;
	    	t.right = header.left;
	    	root = t;
	        }
	    
	    private NodoA<K,V> header = new  NodoA<K,V>(null,null);

	    
	    private void splay(K key) {
	    	NodoA<K,V> l, r, t, y;
	    	l = r = header;
	    	t = root;
	    	header.left = header.right = null;
	    	for (;;) {
	    	    if (key.compareTo(t.key) < 0) {
	    		if (t.left == null) break;
	    		if (key.compareTo(t.left.key) < 0) {
	    		    y = t.left;                            /* rotate right */
	    		    t.left = y.right;
	    		    y.right = t;
	    		    t = y;
	    		    if (t.left == null) break;
	    		}
	    		r.left = t;                                 /* link right */
	    		r = t;
	    		t = t.left;
	    	    } else if (key.compareTo(t.key) > 0) {
	    		if (t.right == null) break;
	    		if (key.compareTo(t.right.key) > 0) {
	    		    y = t.right;                            /* rotate left */
	    		    t.right = y.left;
	    		    y.left = t;
	    		    t = y;
	    		    if (t.right == null) break;
	    		}
	    		l.right = t;                                /* link left */
	    		l = t;
	    		t = t.right;
	    	    } else {
	    		break;
	    	    }
	    	}
	    	l.right = t.left;                                   /* assemble */
	    	r.left = t.right;
	    	t.left = header.right;
	    	t.right = header.left;
	    	root = t;
	    }
	    
	    
  
}
