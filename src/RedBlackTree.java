import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * 
 * @author andrei portales 19825, 
 *	Referencias de condigo = https://www.sanfoundry.com/java-program-implement-red-black-tree/
 * @param <K>
 * @param <V>
 */

public class RedBlackTree<K extends Comparable<K>, V extends Comparable<V>> implements BST<K,V>{
	
	 private static final boolean RED   = true;
	 private static final boolean BLACK = false;

	 private NodoA<K,V> root;     // root of the BST
	 
	 public RedBlackTree() {
		root = null;
	}

	@Override
	public void insert(K key, V value) {
		put(key, value);
	}

	@Override
	public V buscar(K key) {
		// TODO Auto-generated method stub
		return get(key);
	}
	
	private boolean isRed(NodoA<K,V> x) {
        if (x == null) return false;
        return x.color == RED;
    }

    // number of NodoA<K,V> in subtree rooted at x; 0 if x is null
    private int size(NodoA<K,V> x) {
        if (x == null) return 0;
        return x.size;
    } 


    /**
     * Returns the number of K-V pairs in this symbol table.
     * @return the number of K-V pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

   /**
     * Is this symbol table empty?
     * @return {@code true} if this symbol table is empty and {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }


   /***************************************************************************
    *  Standard BST search.
    ***************************************************************************/

    /**
     * Returns the V associated with the given K.
     * @param K the K
     * @return the V associated with the given K if the K is in the symbol table
     *     and {@code null} if the K is not in the symbol table
     * @throws IllegalArgumentException if {@code K} is {@code null}
     */
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return get(root,key);
    }

    // V associated with the given K in subtree rooted at x; null if no such K
    private V get(NodoA<K,V> x, K key) {
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else  return x.value;
        }
        return null;
    }

    /**
     * Does this symbol table contain the given K?
     * @param K the K
     * @return {@code true} if this symbol table contains {@code K} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code K} is {@code null}
     */
    public boolean contains(K K) {
        return get(K) != null;
    }

   /***************************************************************************
    *  Red-black tree insertion.
    ***************************************************************************/

    /**
     * Inserts the specified K-V pair into the symbol table, overwriting the old 
     * V with the new V if the symbol table already contains the specified K.
     * Deletes the specified K (and its associated V) from this symbol table
     * if the specified V is {@code null}.
     *
     * @param K the K
     * @param value the V
     * @throws IllegalArgumentException if {@code K} is {@code null}
     */
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (value == null) {
            delete(key);
            return;
        }

        root = put(root, key, value);
        root.color = BLACK;
        // assert check();
    }

    // insert the K-V pair in the subtree rooted at h
    private NodoA<K,V> put(NodoA<K,V> h, K key, V value) { 
        if (h == null) return new NodoA<K,V>(key, value, RED, 1);

        int cmp = key.compareTo(h.key);
        if      (cmp < 0) h.left  = put(h.left,  key, value); 
        else if (cmp > 0) h.right = put(h.right, key, value); 
        else              h.value   = value;

        // fix-up any right-leaning links
        if (isRed(h.right) && !isRed(h.left))      h = rotateLeft(h);
        if (isRed(h.left)  &&  isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left)  &&  isRed(h.right))     flipColors(h);
        h.size = size(h.left) + size(h.right) + 1;

        return h;
    }

   /***************************************************************************
    *  Red-black tree deletion.
    ***************************************************************************/

    /**
     * Removes the smallest K and associated V from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the K-V pair with the minimum K rooted at h
    private NodoA<K,V> deleteMin(NodoA<K,V> h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = deleteMin(h.left);
        return balance(h);
    }


    /**
     * Removes the largest K and associated V from the symbol table.
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the K-V pair with the maximum K rooted at h
    private NodoA<K,V> deleteMax(NodoA<K,V> h) { 
        if (isRed(h.left))
            h = rotateRight(h);

        if (h.right == null)
            return null;

        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);

        h.right = deleteMax(h.right);

        return balance(h);
    }

    /**
     * Removes the specified K and its associated V from this symbol table     
     * (if the K is in this symbol table).    
     *
     * @param  K the K
     * @throws IllegalArgumentException if {@code K} is {@code null}
     */
    public void delete(K K) { 
        if (K == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(K)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = delete(root, K);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    // delete the K-V pair with the given K rooted at h
    private NodoA<K,V> delete(NodoA<K,V> h, K key) { 
        // assert get(h, K) != null;

        if (key.compareTo(h.key) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                NodoA<K,V> x = min(h.right);
                h.key = x.key;
                h.value = x.value;
                // h.value = get(h.right, min(h.right).K);
                // h.K = min(h.right).K;
                h.right = deleteMin(h.right);
            }
            else h.right = delete(h.right, key);
        }
        return balance(h);
    }

   /***************************************************************************
    *  Red-black tree helper functions.
    ***************************************************************************/

    // make a left-leaning link lean to the right
    private NodoA<K,V> rotateRight(NodoA<K,V> h) {
        // assert (h != null) && isRed(h.left);
        NodoA<K,V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // make a right-leaning link lean to the left
    private NodoA<K,V> rotateLeft(NodoA<K,V> h) {
        // assert (h != null) && isRed(h.right);
        NodoA<K,V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a NodoA<K,V> and its two children
    private void flipColors(NodoA<K,V> h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private NodoA<K,V> moveRedLeft(NodoA<K,V> h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private NodoA<K,V> moveRedRight(NodoA<K,V> h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    // restore red-black tree invariant
    private NodoA<K,V> balance(NodoA<K,V> h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }


   /***************************************************************************
    *  Utility functions.
    ***************************************************************************/

    /**
     * Returns the height of the BST (for debugging).
     * @return the height of the BST (a 1-NodoA<K,V> tree has height 0)
     */
    public int height() {
        return height(root);
    }
    private int height(NodoA<K,V> x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

   /***************************************************************************
    *  Ordered symbol table methods.
    ***************************************************************************/

    /**
     * Returns the smallest K in the symbol table.
     * @return the smallest K in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    } 

    // the smallest K in subtree rooted at x; null if no such K
    private NodoA<K,V> min(NodoA<K,V> x) { 
        // assert x != null;
        if (x.left == null) return x; 
        else                return min(x.left); 
    } 

    /**
     * Returns the largest K in the symbol table.
     * @return the largest K in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public K max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    } 

    // the largest K in the subtree rooted at x; null if no such K
    private NodoA<K,V> max(NodoA<K,V> x) { 
        // assert x != null;
        if (x.right == null) return x; 
        else                 return max(x.right); 
    } 


    /**
     * Returns the largest K in the symbol table less than or equal to {@code K}.
     * @param K the K
     * @return the largest K in the symbol table less than or equal to {@code K}
     * @throws NoSuchElementException if there is no such K
     * @throws IllegalArgumentException if {@code K} is {@code null}
     */
    public K floor(K key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        NodoA<K,V> x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else           return x.key;
    }    

    // the largest K in the subtree rooted at x less than or equal to the given K
    private NodoA<K,V> floor(NodoA<K,V> x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left, key);
        NodoA<K,V> t = floor(x.right, key);
        if (t != null) return t; 
        else           return x;
    }

    /**
     * Returns the smallest K in the symbol table greater than or equal to {@code K}.
     * @param K the K
     * @return the smallest K in the symbol table greater than or equal to {@code K}
     * @throws NoSuchElementException if there is no such K
     * @throws IllegalArgumentException if {@code K} is {@code null}
     */
    public K ceiling(K key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        NodoA<K,V> x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too small");
        else           return x.key;  
    }

    // the smallest K in the subtree rooted at x greater than or equal to the given K
    private NodoA<K,V> ceiling(NodoA<K,V> x, K key) {  
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0)  return ceiling(x.right, key);
        NodoA<K,V> t = ceiling(x.left, key);
        if (t != null) return t; 
        else           return x;
    }

    /**
     * Return the K in the symbol table of a given {@code rank}.
     * This K has the property that there are {@code rank} Ks in
     * the symbol table that are smaller. In other words, this K is the
     * ({@code rank}+1)st smallest K in the symbol table.
     *
     * @param  rank the order statistic
     * @return the K in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *        <em>n</em>–1
     */
    public K select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalueid: " + rank);
        }
        return select(root, rank);
    }

    // Return K in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private K select(NodoA<K,V> x, int rank) {
        if (x == null) return null;
        int leftSize = size(x.left);
        if      (leftSize > rank) return select(x.left,  rank);
        else if (leftSize < rank) return select(x.right, rank - leftSize - 1); 
        else                      return x.key;
    }

    /**
     * Return the number of Ks in the symbol table strictly less than {@code K}.
     * @param K the K
     * @return the number of Ks in the symbol table strictly less than {@code K}
     * @throws IllegalArgumentException if {@code K} is {@code null}
     */
    public int rank(K K) {
        if (K == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(K, root);
    } 

    // number of Ks less than K in the subtree rooted at x
    private int rank(K key, NodoA<K,V> x) {
        if (x == null) return 0; 
        int cmp = key.compareTo(x.key); 
        if      (cmp < 0) return rank(key, x.left); 
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right); 
        else              return size(x.left); 
    } 

  


    
   


	
	
}
