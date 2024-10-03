package ejercicios;

import java.util.Scanner;

public class Ejercicio27 {
	public static void main(String[] args) {

		try (Scanner teclado = new Scanner(System.in)) {
			System.out.print("Introduce los números del DNI: ");

			if (teclado.hasNextInt()) {
				int numeroDNI = teclado.nextInt();

				if (numeroDNI >= 0 && numeroDNI <= 99999999) {
					char letra = determinarLetra.calcularLetra(numeroDNI);

					System.out.println("El DNI completo es: " + numeroDNI + letra);
				} else {
					System.out.println("El número del DNI es incorrecto");
				}
			} else {
				System.out.println("Entrada no váida, debes introducir un número entero");
				teclado.next();
			}
		}
	}

	public class determinarLetra {
		private static final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

		public static char calcularLetra(int numeroDNI) {
			int resto = numeroDNI % 23;
			return LETRAS.charAt(resto);
		}
	}
}
