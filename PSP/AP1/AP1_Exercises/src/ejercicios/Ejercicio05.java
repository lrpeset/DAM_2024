package ejercicios;

import java.util.Scanner;

public class Ejercicio05 {
	public static void main(String[] args) {

		try (Scanner teclado = new Scanner(System.in)) {
			boolean equals = false;
			while (!equals) {
				System.out.println("No acabaré hasta que ambos números sean iguales");
				System.out.print("Introduce un primer número: ");
				int numero1 = teclado.nextInt();
				System.out.print("Introduce un segundo número: ");
				int numero2 = teclado.nextInt();

				if (numero1 == numero2) {
					equals = true;
					System.out.print("Buen trabajo!");
				} else {
					equals = false;
					System.out.println("Los numero no son iguales, inténtalo de nuevo...");
				}
			}
		}
	}
}
