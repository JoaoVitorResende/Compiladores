package lexico;

import java.util.ArrayList;

public class Verifica_lexo {

	private String tipo, lexema, comando;

	// -----------------------------------------
	public char c, check;

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
	// palavra reservada
	String Verificacao = "";
	int contador_reservadas = 0;
	boolean palavra_descoberta = false;
	boolean andamento = false;
	// ----

	// numeral
	String valor = "";//o que vai imprimir o valor
	char[] valores = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };//numeros validos
	boolean numeral = false;// variavel de controle
	boolean real = false;// double
	// --

	// boolean
	String boleana = "";
	boolean inicia_boolena = false;
	int contador_boleana = 0;
	// --

	// Comentario
	boolean comentario = false;
	char[] cadeiavet;
	// --

	// cadeia
	String cadeia;
	// ---
	
	// variavel
	String variavel ="";
	boolean ativa_variavel = false;
	// ---
	
	// ---------------------------------

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

			if (!altera_buffer) {// carrega buffer 1

				contador_b = 0;// zera contador 2

				if (b1 < 10) {

					buffer_1_vetor[b1] = c;

					b1++;

				} else {// inicia checagem

					buffer_2_vetor[0] = c;//

					altera_buffer = true;// altera para buffer 2
					b2 = 1;

					vebf1 = true;
					vebf2 = false;

					proximo_b = true;// libera vetor b para proximo
					proximo_a = false;

					while (b1 > 0) {
						retornatoken();
						b1--;
					}
				}

			} else {// carregar buffer 2

				contador_a = 0;// zera contador 1

				if (b2 < 10) {// carrega buffer 2

					buffer_2_vetor[b2] = c;

					b2++;

				} else {// chega o que tinha no buffer 2

					buffer_1_vetor[0] = c;

					altera_buffer = false;// volta a ser buffer 1

					b1 = 1;

					vebf1 = false;
					vebf2 = true;

					proximo_b = false;
					proximo_a = true; // libera vetor a para proximo

					while (b2 > 0) {
						retornatoken();
						b2--;
					}
				}
			}
		}
	}

	private char Proximo() {

		if (proximo_a) {

			char c = (char) lda.leCaracter();

			return c;
		}

		if (proximo_b) {

			char c = (char) lda.leCaracter();

			return c;
		}
		return ' ';
	}

	public void retornatoken() {

		if (vebf1) {// checagem buffer 1

			if (contador_a < 11) {

				if (token_a) {

					if (contador_a != 0) {
						contador_a--;
						token_a = false;

					} else {
						token_a = false;
					}
				}
				if (contador_a != 10) {

					// c = (char)buffer_1_vetor[contador_a];System.out.println("buffer 1 -"+c);
					c = (char) buffer_1_vetor[contador_a];

					if (c == ' ' || c == '\n') {
						contador_a++;
						token_a = true;
					}

					if (c == '(') {
						contador_a++;
						to.Token(TipoToken.AbrePar, "(");
						token_a = true;
					}

					if (c == ')') {
						contador_a++;
						to.Token(TipoToken.FechaPar, ")");
						token_a = true;
					}

					if (c == '>') {

						check = buffer_1_vetor[contador_a + 1];


						if (check == '=') {
							token_a = true;
							to.Token(TipoToken.OpRelMaiorIgual, ">=");
						}

						if (check != '=') {
							token_a = true;
							to.Token(TipoToken.OpRelMaior, ">");
						}
					}

					if (c == '<') {

						check = buffer_1_vetor[contador_a + 1];
						boolean tipo_relacional = false;
						contador_a++;

						if (check == '=') {
							token_a = false;
							to.Token(TipoToken.OpRelMaiorIgual, "<=");
							tipo_relacional = true;
						}

						if (check == '>') {
							token_a = false;
							to.Token(TipoToken.OpRelDif, "<>");
							tipo_relacional = true;
						}

						if (!tipo_relacional) {
							token_a = false;
							to.Token(TipoToken.OpRelMenor, "<");
						}
					}
					
					Comentario(c);
					
					if(!comentario) {
						Delimitador(c);
						PalavraReservada(c);
						Numeral(c);
						ChecaBoolean(c);
						Aritimetico(c);
						
						if(ativa_variavel) {
							Variavel(c);
						}
					}
				}
			}
			contador_a++;
		} else

		if (vebf2) {// checagem buffer 2

			if (contador_b < 11) {

				if (token_b) {

					if (contador_b != 0) {
						contador_b--;
						token_b = false;

					} else {
						token_b = false;
					}
				}

				if (contador_b != 10) {

					// c = (char) buffer_2_vetor[contador_b];System.out.println("buffer 2 -"+c);
					c = (char) buffer_2_vetor[contador_b];

					if (c == ' ' || c == '\n') {
						contador_b++;
						token_b = true;
					}


					if (c == '(') {
						contador_b++;
						to.Token(TipoToken.AbrePar, "(");
						token_b = true;
					}

					if (c == ')') {
						contador_b++;
						to.Token(TipoToken.FechaPar, ")");
						token_b = true;
					}

					if (c == '>') {

						check = buffer_2_vetor[contador_b + 1];

						contador_b++;

						if (check == '=') {
							token_b = false;
							to.Token(TipoToken.OpRelMaiorIgual, ">=");
						}

						if (check != '=') {
							token_b = false;
							to.Token(TipoToken.OpRelMaior, ">");
						}
					}

					if (c == '<') {

						check = buffer_2_vetor[contador_b + 1];

						boolean tipo_relacional = false;

						contador_b++; // aumenta para caso for = ou > já pule

						if (check == '=') {
							token_b = false;
							to.Token(TipoToken.OpRelMaiorIgual, "<=");
							tipo_relacional = true;
						}

						if (check == '>') {
							token_b = false;
							to.Token(TipoToken.OpRelDif, "<>");
							tipo_relacional = true;
						}

						if (!tipo_relacional) {
							token_b = false;
							to.Token(TipoToken.OpRelMenor, "<");
							contador_b--;
						}
					}
					
					Comentario(c);
					
					if(!comentario) {
						Delimitador(c);
						PalavraReservada(c);
						Numeral(c);
						ChecaBoolean(c);
						Aritimetico(c);
						
						if(ativa_variavel) {
							Variavel(c);
						}
					}
				}
			}
			contador_b++;
		}
	}

	private void PalavraReservada(char ch) { // verifica palavras reservadas

		if (ch == 'D') {
			
			if(!andamento) {
				contador_reservadas = 11;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}

		if (ch == 'F') {
			
			if(!andamento) {
				contador_reservadas = 3;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}
		
		if(ch == 'V') {
			if(!andamento) {
				contador_reservadas = 3;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}
		
		if(ch == 'A') {
			
			if(!andamento) {
				contador_reservadas = 9;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}
		
		if(ch == 'I') {
			if(!andamento) {
				contador_reservadas = 6;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}
		
		if(ch == 'R') {
			if(!andamento) {
				contador_reservadas = 4;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}
		
		if(ch == 'L') {
			if(!andamento) {
				contador_reservadas = 3;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}
		
		if(ch == 'S') {
			
			if(!andamento) {
				contador_reservadas = 2;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
		}
		
		if(ch == 'E') {
			
			if(!andamento) {
				contador_reservadas = 8;
				palavra_reservada = true;
				palavra_descoberta = false;
				andamento = true;
			}
			
		}
		
		
		if (palavra_reservada) {

			if (contador_reservadas > 0) {

				Verificacao += ch;
				// System.out.println(Verificacao);
				if (Verificacao.equals("DECLARACOES")) {
					to.Token(TipoToken.PCDeclaracoes, "DEC");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}

				if (Verificacao.equals("FIM")) {
					to.Token(TipoToken.Fim, "Fim");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("ALGORITMO")) {
					to.Token(TipoToken.PCAlgoritmo, "ALG");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("VAR")) {
					to.Token(TipoToken.Var, "Var");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					ativa_variavel = true;
					contador_reservadas = 0;
					andamento = false;
					
				}
				
				if (Verificacao.equals("INT")) {
					to.Token(TipoToken.PCInteiro, "INT");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("REAL")) {
					to.Token(TipoToken.PCReal, "REA");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("ATRIBUIR")) {
					to.Token(TipoToken.PCAtribuir, "ATR");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("LER")) {
					to.Token(TipoToken.PCLer, "LER");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("SE")) {
					to.Token(TipoToken.PCSe, "SE");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("ENTAO")) {
					to.Token(TipoToken.PCEntao, "ENT");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("A ")) {
					to.Token(TipoToken.PCA, "A");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
				}
				
				if (Verificacao.equals("ENQUANTO")) {
					to.Token(TipoToken.PCEnquanto, "ENQ");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
					
				}
				
				if (Verificacao.equals("INICIO")) {
					to.Token(TipoToken.PCInicio, "INI");
					Verificacao = "";
					palavra_reservada = false;
					palavra_descoberta = true;
					contador_reservadas = 0;
					andamento = false;
					
				}
				contador_reservadas--;
			}

			if (!palavra_descoberta && contador_reservadas == 0) {// a palavra reservada não foi encontrada então nula
				Verificacao = "";
				palavra_reservada = false;
				contador_reservadas = 0;
				andamento = false;
			}
		}
	}

	private void Numeral(char ch) {// numeros inteiros e reais

		if (!numeral) {

			VerificaNumero(ch);

			if (ch == ',' || ch == '.') {
				real = true;
				valor += ch;
				numeral = true;
				
				/*
				if(vebf1) {
					token_a = true;
					contador_a++;
				}else {
					token_b = true;
					contador_b++;
				}
				*/
			}

		} else {
			valor += ch;
			if (ch == ' ' && valor != "" || ch == '\n' || ch == '\r') {
				numeral = false;
				// real 
				if (real) {
					to.Token(TipoToken.NumReal, valor);
					valor = "";
				} else {
					to.Token(TipoToken.PCInteiro, valor);
					valor = "";
				}
				//---
				/*
				// retorna ajeita fila
				if(vebf1) {
					token_a = true;
					contador_a++;
				}else {
					token_b = true;
					contador_b++;
				}
				//-----*/
			}
		}

	}

	private void VerificaNumero(char vl_ch) {

		for (int i = 0; i < valores.length; i++) {

			if (vl_ch == valores[i]) {
				valor += vl_ch;
				numeral = true;
			}
		}

	}

	private void ChecaBoolean(char bo_ch) {// verifica se correspodem as boleanas

		if (bo_ch == 'E') {
			inicia_boolena = true;
			boleana += bo_ch;
			contador_boleana = 1;
			/*
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}*/

		}

		if (bo_ch == 'O') {
			inicia_boolena = true;
			boleana += bo_ch;
			contador_boleana = 2;
			/*
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
			*/
		}

		if (inicia_boolena) {

			if (bo_ch == 'U') {
				boleana += bo_ch;
				/*
				if(vebf1) {
					token_a = true;
					contador_a++;
				}else {
					token_b = true;
					contador_b++;
				}
				*/
			}

			if (boleana.equals("OU")) {
				inicia_boolena = false;
				to.Token(TipoToken.OpBoolOU, boleana);
				boleana = "";
				/*
				if(vebf1) {
					token_a = true;
					contador_a++;
				}else {
					token_b = true;
					contador_b++;
				}
				*/
			}

			if (bo_ch == '\r' && boleana.equals("E") || bo_ch == '\n' && boleana.equals("E")) {
				to.Token(TipoToken.OpBoolE, "E");
				inicia_boolena = false;
				boleana = "";
				
				Verificacao = "";
				palavra_reservada = false;
				palavra_descoberta = true;
				contador_reservadas = 0;
				andamento = false;
				/*
				if(vebf1) {
					token_a = true;
					contador_a++;
				}else {
					token_b = true;
					contador_b++;
				}
				*/
			}

			if (inicia_boolena && contador_boleana == 0) {
				inicia_boolena = false;
				boleana = "";
				/*
				if(vebf1) {
					token_a = true;
					contador_a++;
				}else {
					token_b = true;
					contador_b++;
				}
				*/
			}

			contador_boleana--;
		}
	}

	private void Comentario(char co_ch) {
		
		if (co_ch == '%') {
			comentario = true;
			/*
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}*/
		}
		
		if (co_ch == '\n' || co_ch == '\r' || co_ch == ' ') {
			comentario = false;
			/*
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}*/
		}
	}

	private void Variavel(char va_ch) {// problema de pegar um antes 
		
		if(va_ch != '\n') {
			variavel += va_ch;
			
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
			
		}else {
			System.out.print("nome da variavel "+variavel);// printa na mesma linha do var com um espaço e o nome da variavel
			ativa_variavel = false;
			
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
		}
		
	}
	
	private void Delimitador(char dl_ch) {
		if (dl_ch == ':') {
			to.Token(TipoToken.Delim, ":");
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
		}
	}

	private void Aritimetico(char ar_ch) {
		
		if (ar_ch == '*') {
			to.Token(TipoToken.OpAritMult, "*");
			
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
		}

		if (ar_ch == '/') {
			to.Token(TipoToken.OpAritDiv, "/");
			
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
		}

		if (ar_ch == '+') {
			to.Token(TipoToken.OpAritSoma, "+");
			
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
		}

		if (ar_ch == '-') {
			to.Token(TipoToken.OpAritSub, "-");
			
			if(vebf1) {
				token_a = true;
				contador_a++;
			}else {
				token_b = true;
				contador_b++;
			}
		}
		
	}

}
