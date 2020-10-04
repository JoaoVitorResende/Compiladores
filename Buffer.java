package lexico;

import java.util.ArrayList;

public class Buffer {

	char[] buffer_1_vetor = new char[9999];
	char[] buffer_2_vetor = new char[9999];

	boolean altera_buffer = false;

	int b1 = 0, b2 = 0;
	
	Verifica_lexo vfl;
	/*
	public void Buffer(char c) {
		
		if (!altera_buffer) {
			buffer_1_vetor[b1] = c;
			if (buffer_1_vetor[b1] == '>' || buffer_1_vetor[b1] == '<') {
				if (c == '\n' || c == ' ' && buffer_1_vetor[b1] == '>' || buffer_1_vetor[b1] == '<') {

					altera_buffer = true;
					buffer_2_vetor[b2] = c;
					b2++;
				}

			} else {

				b1++;
			}
		}

		if (altera_buffer) {

			buffer_2_vetor[b2] = c;
			b2++;
		}
		
	}

	public void LinhaCorrigida() {
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(buffer_1_vetor[0]);
		
		for (int i = 0; i <= b1; i++) {
			RetornaChar(buffer_1_vetor[i]);
			if (i == b1) {
				for (int j = 0; j < b2; j++) {
					RetornaChar(buffer_2_vetor[j]);
				}
			}
		}

	}

	public void RetornaChar(char c) {
		vfl.c = c;
		vfl.Verifica_Caracter();
	}*/
}
