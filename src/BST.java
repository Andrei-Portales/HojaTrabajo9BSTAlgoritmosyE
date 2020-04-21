
public interface BST<K extends Comparable<K>, V extends Comparable<V>> {
	
	public void insert(K key, V value);
	
	public V buscar(K key);

}
