package lexico;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Leitor_De_Arquivo {
	
	InputStream ipt;
	
	File file = new File("D:/Download/Luciano/Compiladores/script.txt");

	Verifica_lexo vrl = new Verifica_lexo();
	
	public Leitor_De_Arquivo(String arquivo) {
		try {
			ipt = new FileInputStream(new File(arquivo));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int leCaracter() {

		try {

			int ret = ipt.read();
		    //System.out.print((char)ret);
			return ret;

		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

	}
}
