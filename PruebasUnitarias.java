import static org.junit.Assert.*;

import org.junit.Test;

public class PruebasUnitarias {

	@Test
	public void test1() {
		SplayTree<String,String> splay = new SplayTree<String,String>();
		splay.insert("hello", "hola");
		splay.insert("bye", "adios");
		splay.insert("motion", "movimiento");
		
		String v1 = splay.buscar("hello");
		String v2 = splay.buscar("bye");
		String v3 = splay.buscar("motion");
		
		assertEquals("hola", v1);
		assertEquals("adios", v2);
		assertEquals("movimiento", v3);
	}
	
	@Test
	public void test2() {
		RedBlackTree<String,String> redblack = new RedBlackTree<String,String>();
		redblack.insert("hello", "hola");
		redblack.insert("bye", "adios");
		redblack.insert("motion", "movimiento");
		
		String v1 = redblack.buscar("hello");
		String v2 = redblack.buscar("bye");
		String v3 = redblack.buscar("motion");
		
		assertEquals("hola", v1);
		assertEquals("adios", v2);
		assertEquals("movimiento", v3);
		
	}



}
