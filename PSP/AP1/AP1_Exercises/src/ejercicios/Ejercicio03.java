package ejercicios;

import java.util.Scanner;

public class Ejercicio03 {
	public static void main(String[] args) {

		try (Scanner teclado = new Scanner(System.in)) {
			System.out.print("Introduce un primer número a sumar: ");
			int numero1 = teclado.nextInt();
			System.out.print("Introduce un segundo número a sumar: ");
			int numero2 = teclado.nextInt();

			System.out.print("La suma de ambos dos es: " + (numero1 + numero2));
		}
	}
}
