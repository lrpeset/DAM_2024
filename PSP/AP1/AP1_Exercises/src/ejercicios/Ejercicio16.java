package ejercicios;

import java.util.Scanner;

public class Ejercicio16 {
	public static void main(String[] args) {
		try (Scanner teclado = new Scanner(System.in)) {

			System.out.print("Introduce tu día de nacimiento (DD): ");
			int dia = teclado.nextInt();

			System.out.print("Introduce tu mes de nacimiento (MM): ");
			int mes = teclado.nextInt();

			System.out.print("Introduce tu año de nacimiento (YYYY): ");
			int anyo = teclado.nextInt();
			teclado.nextLine();

			int suma = sumarDigitos(dia) + sumarDigitos(mes) + sumarDigitos(anyo);

			int numeroSuerte = reducirAUnDigito(suma);

			System.out.println("Tu número de la suerte es: " + numeroSuerte);
		}
	}

	public static int sumarDigitos(int numero) {
		int suma = 0;
		while (numero > 0) {
			suma += numero % 10;
			numero /= 10;
		}
		return suma;
	}


	public static int reducirAUnDigito(int numero) {
		while (numero >= 10) {
			numero = sumarDigitos(numero);
		}
		return numero;
	}
}
