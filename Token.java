package lexico;

import java.util.ArrayList;

public class Token {

	public TipoToken nome;

	public String lexema;
	
	public ArrayList<String> tipos_de_token = new ArrayList<String>();
	
	
	public void Token(TipoToken nome, String lexema) {
		this.nome = nome;
		this.lexema = lexema;
		tipos_de_token.add("< " + nome + " , " + lexema + " >");
		
		System.out.println("< " + nome + " , " + lexema + " >");
	}
	@Override
	public String toString() {
		return  "< " + nome + " , " + lexema + " >";
	}
}
