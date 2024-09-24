package ejercicios;

import java.util.Scanner;

public class Ejercicio06 {

	public static void main(String[] args) {
		int[] numeros = new int[5];
		int suma = 0;

		try (Scanner teclado = new Scanner(System.in)) {
			System.out.println("Introduce 5 número para que pueda sumarlos");

			for (int i = 0; i < numeros.length; i++) {
				System.out.print("Introduce el número " + (i + 1) + ": ");
				numeros[i] = teclado.nextInt();
			}
		}

		for (int numero : numeros) {
			suma += numero;
		}

		System.out.println("La suma de los números es: " + suma);
	}
}