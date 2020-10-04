package lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Principal {
	static ArrayList<String> tokens = new ArrayList<String>();

	public static void main(String[] args) {

		Verifica_lexo vl = new Verifica_lexo();

		vl.La_Lex();
		vl.Proximo_Token();
	}

}
