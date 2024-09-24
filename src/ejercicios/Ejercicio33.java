package ejercicios;

import java.util.Scanner;

public class Ejercicio33 {

	public static void main(String[] args) {
		sumaPares();
	}

	public static void sumaPares() {

		Scanner teclado = new Scanner(System.in);

		System.out.print("Escribe el número hasta el cual quieres que sume los pares: ");
		int numero = teclado.nextInt();
		int suma = 0;

		for (int i = 0; i <= numero; i++) {
			if (i % 2 == 0) {
				suma += i;
			}
		}
		System.out.print("La suma de los pares es: " + suma);
		teclado.close();
	}
}
