
public class Factory<K extends Comparable<K>, V extends Comparable<V>> {
	
	public BST<K,V> getInstance(String metodo){
		
		switch(metodo) {
		case "SplayTree":
			return new SplayTree<K,V>();
		case "RedBlackTree":
			return new RedBlackTree<K,V>();
		case "HashMap":
			return new HashMapC<K,V>();
		default: return null;
		
		}
	}

}
