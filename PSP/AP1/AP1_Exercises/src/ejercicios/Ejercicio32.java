package ejercicios;

import java.util.ArrayList;
import java.util.Arrays;

public class Ejercicio32 {

	public static void main(String[] args) {
		String[] companyeros = { "Isaac", "Jairo", "Ariadna", "Rebeca", "Julio", "Claudia" };

		for (int i = 0; i < companyeros.length; i++) {
			System.out.println(companyeros[i]);
		}

		ArrayList<String> companyerosLista = new ArrayList<>(
				Arrays.asList("Isaac", "Jairo", "Ariadna", "Rebeca", "Julio", "Claudia"));

		for (String companyero : companyerosLista) {
			System.out.println(companyero);
		}
	}
}