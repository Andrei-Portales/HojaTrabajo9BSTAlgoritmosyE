import java.util.ArrayList;

public class Controlador {
	private boolean isFill;
	
	private BST<String,String> tree;
	
	
	public Controlador(BST<String,String> bst) {
		setFill(false);
		tree = bst;
	}
	
	
	public long fillDic() {
		ArrayList<String> lineas = ArchivoTXT.leerDic();
		long td = 0;
		long ti = System.nanoTime();
		for (String l:lineas) {
			if (l != null) {
				String[] split = l.split("\t");
				tree.insert(split[0].replace("null", ""),split[1].replace("null", ""));
			}
		}
		if (lineas.size() > 0) {
			long tf = System.nanoTime();
			td = Math.abs(tf - ti);
			setFill(true);
		}
		return td;
	}
	
	
	public String getTextTxt() {
		String retorno = "";
		ArrayList<String> lineas = ArchivoTXT.leerTexto();
		for (String linea : lineas) {
			retorno += linea +" ";
		}
		return retorno;
	}
	
	public String[] traducir(String texto) {
		long ti = System.nanoTime();
		String[] traduccion = new String[] {"",""};
		String replaced = texto.replace("\n", "");
		String[] split = replaced.split(" ");
		
		for (String s:split) {
			if (s.contains(".")) {
				String tt = clean(s);
				String response = tree.buscar(tt.toLowerCase());
				if (response != null) {
					traduccion[0] += response + ". ";
				}else {
					traduccion[0] += "*" + s + ".* ";
				}
			}else {
				String response = tree.buscar(s.toLowerCase());
				if (response != null) {
					traduccion[0] += response + " ";
				}else {
					traduccion[0] += "*" + s + "* ";
				}
			}
		}
		long tf = System.nanoTime();
		long delta = Math.abs(tf-ti);
		traduccion[1] = Long.toString(delta);
		
		
		
		return traduccion;		
	}


	
	public boolean isFill() {
		return isFill;
	}


	public void setFill(boolean isFill) {
		this.isFill = isFill;
	}
	
	public String clean(String txt) {
		String r = "";
		String[] split = txt.split("");
		for (String s:split) {
			if (!s.equals(".")) {
				r += s;
			}
		}
		
		return r;
	}
	
	

}
