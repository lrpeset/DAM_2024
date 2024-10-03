package ejercicios;

import java.util.Scanner;

public class Ejercicio07 {

	public static void main(String[] args) {
		int[] numeros = new int[5];
		int suma = 0;
		int i = 0;

		try (Scanner teclado = new Scanner(System.in)) {
			System.out.println("Introduce 5 números para que pueda sumarlos");

			while (i < numeros.length) {
				System.out.print("Introduce el número " + (i + 1) + ": ");
				numeros[i] = teclado.nextInt();
				i++;
			}
		}

		i = 0;
		while (i < numeros.length) {
			suma += numeros[i];
			i++;
		}

		System.out.println("La suma de los números es: " + suma);
	}
}
