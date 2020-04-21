import java.util.HashMap;

public class HashMapC <K extends Comparable<K>, V extends Comparable<V>> implements BST<K,V>{
	
	private HashMap<K,V> mapa;
	
	public HashMapC() {
		mapa = new HashMap<K,V>();
	}

	@Override
	public void insert(K key, V value) {
		mapa.put(key, value);
	}

	@Override
	public V buscar(K key) {
		// TODO Auto-generated method stub
		return mapa.get(key);
	}

}
