package ejercicios;

import java.util.Scanner;

public class Ejercicio11 {
	public static void main(String[] args) {
		final String LETRAS = "TRWAGMYFPDXBNJZSQVHLCKE";

		try (Scanner teclado = new Scanner(System.in)) {
			System.out.print("Introduce los números del DNI: ");

			if (teclado.hasNextInt()) {
				int numeroDNI = teclado.nextInt();

				if (numeroDNI >= 0 && numeroDNI <= 99999999) {
					int resto = numeroDNI % 23;
					char letra = LETRAS.charAt(resto);

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
}
