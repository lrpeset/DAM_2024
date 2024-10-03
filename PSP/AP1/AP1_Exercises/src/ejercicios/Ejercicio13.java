package ejercicios;

import java.util.Scanner;

public class Ejercicio13 {
	public static void main(String[] args) {

		try (Scanner teclado = new Scanner(System.in)) {

			System.out.print("Introduce los grados centígrados: ");
			double grados = teclado.nextDouble();

			double gradosFahrenheit = (grados * 9 / 5) + 32;
            System.out.printf("La conversión a grados Fahrenheit es: %.1f°F%n", gradosFahrenheit);
		}
	}
}
