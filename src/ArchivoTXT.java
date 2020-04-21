import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ArchivoTXT {
	
	/**
	 * Funcion para obtener la ruta de el archivo txt
	 * @return
	 */
	public static String getPath()  {
		
		JFileChooser chooser = new JFileChooser();
	 	FileNameExtensionFilter filtroImagen =new FileNameExtensionFilter("*.TXT", "txt");
	 	chooser.setFileFilter(filtroImagen);
	 	File f = null;
	 	
		try {
			f = new File(new File(".").getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		String path = "";
		
		try {
			chooser.setCurrentDirectory(f);
			chooser.setCurrentDirectory(null);
			chooser.showOpenDialog(null);
	    
			path = chooser.getSelectedFile().toString();
		}catch(Exception e) {
			
		}
	    return path;
	}
	
	
	
	/**
	 * Funcion para leer el txt de diccionario
	 * @return
	 */
	public static ArrayList<String> leerDic() {
		String path = getPath();
		File archivo = new File(path);
		FileReader fr;
		BufferedReader br;
		ArrayList<String> lineas = new ArrayList<String>();
		try {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea = "";
			while((linea = br.readLine()) != null) {
				if(!linea.contains("#")) {
					lineas.add(cleanLine(linea));		
				}
				
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha sucedido un error leyendo el archivo " + e);
		}
		
		return lineas;
	}
	
	
	public static ArrayList<String> leerTexto() {
		String path = getPath();
		File archivo = new File(path);
		FileReader fr;
		BufferedReader br;
		ArrayList<String> lineas = new ArrayList<String>();
		try {
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			String linea = "";
			while((linea = br.readLine()) != null) {
				lineas.add(linea);			
			}
			br.close();
			fr.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha sucedido un error leyendo el archivo " + e);
		}
		
		return lineas;
	}
	
	
	/**
	 * Metodo para limpiar linea ingresada
	 * @param line
	 * @return
	 */
	private static String cleanLine(String line) {
		String temp = "";
		String[] split = line.split("");
		int c1 = 0;
		for (String a : split) {
			if (a.equals("(") || a.equals("[")) c1 ++;
			if (c1 == 0) temp += a;
			if (a.equals(")") || a.equals("]")) c1 = 0;
		}
		
		if (temp.contains(",")) {
			temp = temp.split(",")[0];
		}else if (temp.contains(";")) {
			temp = temp.split(";")[0];
		}else if (temp.contains(":")) {
			String[] f = temp.split(":");
			String[] ff = f[0].split("\t");
			temp = ff[0] + "\t" + f[f.length - 1];
	
		}
		return temp;
	}
	
	
	
	
	
	

}