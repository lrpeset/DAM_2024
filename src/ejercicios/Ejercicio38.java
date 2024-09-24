package ejercicios;

import java.util.Scanner;

public class Ejercicio38 {

	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		System.out.println("Escribe dos números como intervalo: ");
		System.out.print("Escribe el número más bajo de los dos: ");
		int numeroBajo = teclado.nextInt();
		System.out.print("Escribe el número más alto de los dos: ");
		int numeroAlto = teclado.nextInt();
		teclado.close();

		long inicioMetodo = System.currentTimeMillis();
		intervaloNumeros(numeroBajo, numeroAlto);

		long finalMetodo = System.currentTimeMillis();
		long tiempoConsumido = finalMetodo - inicioMetodo;

		System.out.println("El tiempo consumido en la ejecución del método es: " + tiempoConsumido + " milisegundos.");
	}

	public static void intervaloNumeros(int numeroBajo, int numeroAlto) {
		for (int i = numeroBajo; i <= numeroAlto; i++) {

			if (i % 2 == 0) {
				System.out.println(i + ". es par");
			} else {
				System.out.println(i + ". es impar");
			}
		}
	}
}