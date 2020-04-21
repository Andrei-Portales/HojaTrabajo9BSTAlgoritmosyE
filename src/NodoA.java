import java.util.Map;

public class NodoA<K extends Comparable<K>, V extends Comparable<V>>  implements Comparable<NodoA<K, V>> , Map.Entry<K,V> {
	
	protected K key;
	protected V value;
	protected NodoA<K,V> left, right;
	protected int size;
	protected boolean color;
	
	/**
	 * constructor
	 * @param key
	 * @param value
	 */
	public NodoA(K key, V value) {
		this.key = key;
		this.value = value;
		left = null;
		right = null;
	}
	
	public NodoA(K key, V value,boolean color, int size) {
		this.key = key;
		this.value = value;
		left = null;
		right = null;
		this.color = color;
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	/**
	 * metodo para obtener la key
	 */
	@Override
	public K getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	/**
	 * metodo para obtener el value
	 */
	@Override
	public V getValue() {
		// TODO Auto-generated method stub
		return value;
	}

	/**
	 * metodo para asignar un nuevo valor de value
	 * @param value
	 */
	@Override
	public V setValue(V value) {
		V temp = this.value;
		this.value = value;
		return temp;
	}
	
	/**
	 * metodo para asignar nuevo valor de key
	 * @param key
	 */
	public void setKey(K key) {
		// TODO Auto-generated method stub
		this.key = key;
	}

	/**
	 * Metodo compare to para compara nodos
	 */
	@Override
	public int compareTo(NodoA<K, V> node) {
		return this.key.compareTo(node.getKey());
	}

	/**
	 * metodo para signar nodo izquierdo
	 * @param node
	 */
	public void setRight(NodoA<K,V> node) {
		right = node;
	}
	
	/**
	 * metodo para asignar nodo izquierdo
	 * @param node
	 */
	public void setLeft(NodoA<K,V> node) {
		left = node;
	}
	
	/**Metodo para obtener el nodo derecho
	 * 
	 * @return
	 */
	public NodoA<K,V> getRight() {
		return right;
	}
	
	/**
	 * metodo para obtener nodo izquierdo
	 * @return
	 */
	public NodoA<K,V> getLeft() {
		return left;
	}

}
