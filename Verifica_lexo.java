package lexico;

import java.util.ArrayList;

public class Verifica_lexo {

	private String tipo, lexema, comando;
	
	// -----------------------------------------

	public char c,check;

	int a = 0, b = 0;

	public Leitor_De_Arquivo lda;

	char[] buffer_1_vetor = new char[10];
	char[] buffer_2_vetor = new char[10];

	
	// operadores de controle----------
	
	public boolean lexeno = false;
	
	boolean vebf1 = true;
	boolean vebf2 = false;
	
	boolean altera_buffer = false;
	
	boolean proximo_a = false;
	boolean proximo_b = false;
	
	boolean token_a = false;
	boolean token_b = false;
	
	boolean palavra_reservada = false;
	//palavra reservada
	String Verificacao ="";
	int contador_reservadas = 0;
	boolean palavra_descoberta = false;
	//----
	
	//numeral
	String valor = "";
	char [] valores = new char [] {'0','1','2','3','4','5','6','7','8','9'};
	boolean numeral = false;
	boolean real = false;//double
	//--
	
	//---------------------------------
	
	int b1 = 0, b2 = 0, b3 = 0;
	
	int contador_a = 0, contador_b = 0;
	
	Token to = new Token();
	// ------------------------------------------

	public void La_Lex() {

		lda = new Leitor_De_Arquivo("D:/Download/Luciano/Compiladores/script.txt");
	}

	public void Proximo_Token() {

		int caracterlido = -1;

		while ((caracterlido = lda.leCaracter()) != -1) {

			c = (char) caracterlido;
			
			if (!altera_buffer) {//carrega buffer 1
				
				contador_b = 0;//zera contador 2
				
				if (b1 <10) {
					
					buffer_1_vetor[b1] = c;
					
					b1++;
					
				} else {//inicia checagem
					
					buffer_2_vetor[0] = c;//
					
					altera_buffer = true;//altera para buffer 2
					b2 = 1;
					
					vebf1 = true;
					vebf2 = false;
					
					proximo_b = true;//libera vetor b para proximo
					proximo_a = false;
					
					while(b1>0) {
					retornatoken();
					b1--;
					}
				}

			} else {//carregar buffer 2
				
				contador_a = 0 ;// zera contador 1
				
				if (b2 <10) {//carrega buffer 2
					
					buffer_2_vetor[b2] = c;
					
					b2++;
				
				} else {//chega o que tinha no buffer 2
					
					buffer_1_vetor[0] = c;
					
					altera_buffer = false;//volta a ser buffer 1
					
					b1 = 1;
					
					vebf1 = false;
					vebf2 = true;
					
					proximo_b = false;
					proximo_a = true; // libera vetor a para proximo
					
					while(b2>0) {
					retornatoken();
					b2--;
					}
			   }
			}
		}
	}

	private char Proximo() {
		
		if(proximo_a) {
			
			char c = (char) lda.leCaracter();
			
			return c;	
		}
		
		if(proximo_b) {
			
			char c = (char) lda.leCaracter();
			
			return c;	
		}
		return ' ';
	}

	public void retornatoken() {
			
		if (vebf1) {//checagem buffer 1
			
			if(contador_a<11) {
			
			if(token_a) {
				
				if(contador_a!=0) {
					
					contador_a--;
					
					token_a = false;
				}else {
					token_a = false;
				}
				
			}	
			
			if(contador_a!= 10 ) {
				
			//c = (char)buffer_1_vetor[contador_a];System.out.println("buffer 1 -"+c);
			  c = (char)buffer_1_vetor[contador_a];
			
			if (c == ' ' || c == '\n') {contador_a++;token_a =true;}

			if (c == ':') {contador_a++; to.Token(TipoToken.Delim, ":");token_a=true;}

			if (c == '*') {contador_a++; to.Token(TipoToken.OpAritMult, "*");token_a=true;}

			if (c == '/') {contador_a++; to.Token(TipoToken.OpAritDiv, "/");token_a=true;}

			if (c == '+') {contador_a++; to.Token(TipoToken.OpAritSoma, "+");token_a=true;}

			if (c == '-') {contador_a++; to.Token(TipoToken.OpAritSub, "-");token_a=true;}

			if (c == '(') {contador_a++; to.Token(TipoToken.AbrePar, "(");token_a=true;}

			if (c == ')') {contador_a++; to.Token(TipoToken.FechaPar, ")");token_a=true;}
			
			if(buffer_1_vetor[9] == '>') {
				
				 check = Proximo();
				
				 if(check == '=') {contador_a++; to.Token(TipoToken.OpRelMaiorIgual,">=");token_a=true;}
		    }
			
			if(buffer_1_vetor[9] == '<') {
				
				 check = Proximo();
				
				 if(check == '=') {contador_a++; to.Token(TipoToken.OpRelMenorIgual,"<=");token_a=true;}
			}
			
			if(buffer_1_vetor[9] == '<') {
				
				 check = Proximo();
				
				 if(check == '>') {contador_a++; to.Token(TipoToken.OpRelDif,"<>");token_a=true;}
			}
			
			if(c == '>') {
				
				 check = buffer_1_vetor[contador_a+1];
				 
					if(check == '=') {token_a=true;to.Token(TipoToken.OpRelMaiorIgual,">=");}
					
					if(check != '=') {token_a=true;to.Token(TipoToken.OpRelMaior,">");}
			}
			
			if(c =='<') {
				
				 check = buffer_1_vetor[contador_a+1];
				 boolean tipo_relacional = false;
				 contador_a++;
				
				if(check == '=') {token_a=false;to.Token(TipoToken.OpRelMaiorIgual,"<=");tipo_relacional =true;}
				
				if(check == '>') {token_a=false;to.Token(TipoToken.OpRelDif,"<>");tipo_relacional =true;}
				
				if(!tipo_relacional) {token_a=false;to.Token(TipoToken.OpRelMenor, "<");}
			}
			
			PalavraReservada(c);
			Numeral(c);
			}
		}
			contador_a++;
		} else 
			
			if (vebf2) {//checagem buffer 2
				
			    if(contador_b <11 ) {
			    
			    if(token_b) {
			    	
			    	if(contador_b !=0) {contador_b--;token_b=false;
			    	
			    	}else {token_b = false;}
			    }	
			    
			    if(contador_b !=10) {
			    	
			  //  c = (char) buffer_2_vetor[contador_b];System.out.println("buffer 2 -"+c);
			      c = (char) buffer_2_vetor[contador_b];
			
				if (c == ' ' || c == '\n') {contador_b++;token_b=true;}
					
				if (c == ':') {contador_b++; to.Token(TipoToken.Delim, ":");token_b=true;}

				if (c == '*') {contador_b++; to.Token(TipoToken.OpAritMult, "*");token_b=true;}

				if (c == '/') {contador_b++; to.Token(TipoToken.OpAritDiv, "/");token_b=true;}

				if (c == '+') {contador_b++; to.Token(TipoToken.OpAritSoma, "+");token_b=true;}

				if (c == '-') {contador_b++; to.Token(TipoToken.OpAritSub, "-");token_b=true;}

				if (c == '(') {contador_b++; to.Token(TipoToken.AbrePar, "(");token_b=true;}

				if (c == ')') {contador_b++; to.Token(TipoToken.FechaPar, ")");token_b=true;}
				
				if(buffer_2_vetor[9] == '>') {
					
					 check = Proximo();
					
					 if(check == '=') {contador_b++; to.Token(TipoToken.OpRelMaiorIgual,">=");token_b=true;}
			    }
				
				if(buffer_2_vetor[9] == '<') {
					
					 check = Proximo();
					
					 if(check == '=') {contador_b++;to.Token(TipoToken.OpRelMenorIgual,"<=");token_b=true;}
				}
				
				if(buffer_2_vetor[9] == '<') {
					
					 check = Proximo();
					
					 if(check == '>') {contador_b++;to.Token(TipoToken.OpRelDif,"<>");token_b=true;}
			  }
				
				if(c == '>') {
					
					 check = buffer_2_vetor[contador_b+1];
					 
					 contador_b++;
					
						if(check == '=') {token_b=false;to.Token(TipoToken.OpRelMaiorIgual,">=");}
						
						if(check != '=') {token_b=false;to.Token(TipoToken.OpRelMaior,">");}
				}
				
				if(c =='<') {
					
					 check = buffer_2_vetor[contador_b+1];
					 
					 boolean tipo_relacional = false;
					 
					 contador_b++;
					 
					if(check == '=') {token_b=false;to.Token(TipoToken.OpRelMaiorIgual,"<=");tipo_relacional = true;}
					
					if(check == '>') {token_b=false;to.Token(TipoToken.OpRelDif,"<>");tipo_relacional =true;}
					
					if(!tipo_relacional) {token_b=false;to.Token(TipoToken.OpRelMenor, "<");}
				}
				
				PalavraReservada(c);
				Numeral(c);
			   }
			 }
			contador_b++;
		 }
	}

	private void PalavraReservada(char ch) { // verifica palavras reservadas
		
		if (ch == 'd' || ch == 'D') {

			palavra_reservada = true;
			palavra_descoberta = false;
			contador_reservadas = 11;

		}

		if (ch == 'f' || ch == 'F') {
			palavra_reservada = true;
			palavra_descoberta = false;
			contador_reservadas = 3;
		}

		if (palavra_reservada) {

			if (contador_reservadas > 0) {

				Verificacao += ch;
				if (Verificacao.equalsIgnoreCase("Declaracoes")) {
					to.Token(TipoToken.PCDeclaracoes, "DEC");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
				}

				if (Verificacao.equalsIgnoreCase("FIM")) {
					to.Token(TipoToken.Fim, "Fim");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
				}

				contador_reservadas--;
			}

			if (!palavra_descoberta && contador_reservadas == 0) {// a palavra reservada não foi encontrada então nula
				Verificacao = "";
				palavra_reservada = false;
				contador_reservadas = 0;
			}
		}
	}

	private void Numeral(char ch) {//numeros inteiros  e reais
	
		if (!numeral) {

			VerificaNumero(ch);

			if (ch == ',' || ch == '.') {
				real = true;
				valor += ch;
				numeral = true;
			}

		} else {

			valor += ch;

			if (ch == ' ' || ch == '\n') {
				numeral = false;

				if (real) {
					to.Token(TipoToken.NumReal, valor);
					valor = "";
				} else {
					to.Token(TipoToken.PCInteiro, valor);
					valor = "";
				}

			}
		}

	}

	private void VerificaNumero(char vl_ch) {
		
		for (int i = 0; i < valores.length; i++) {

			if (vl_ch == valores[i]) {
				valor+= vl_ch;
				numeral = true;
			}
		}
		
	}

}
