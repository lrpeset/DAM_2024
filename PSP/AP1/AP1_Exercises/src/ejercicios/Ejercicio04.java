package ejercicios;

import java.util.Scanner;

public class Ejercicio04 {
	public static void main(String[] args) {

		try (Scanner teclado = new Scanner(System.in)) {
			System.out.print("Introduce un primer número a comparar: ");
			int numero1 = teclado.nextInt();
			System.out.print("Introduce un segundo número a comparar: ");
			int numero2 = teclado.nextInt();

			if (numero1 > numero2) {
				System.out.println("El número " + numero1 + " es mayor que " + numero2);
			} else if (numero2 > numero1) {
				System.out.println("El número " + numero2 + " es mayor que " + numero1);
			} else {
				System.out.print("Ambos números son iguales");
			}
		}
	}
}
