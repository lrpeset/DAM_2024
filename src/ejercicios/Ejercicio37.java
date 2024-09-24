package ejercicios;

import java.util.Scanner;

public class Ejercicio37 {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		System.out.print("Introduce tu nombre: ");
		String nombre = teclado.nextLine();

		System.out.print("Introduce el número de años de experiencia como desarrollador: ");
		int anyos = teclado.nextInt();

		System.out.println(nombre + ", tu nivel y salario base se corresponden a: ");
		mostrarSalario(anyos);
		teclado.close();
	}

	public static void mostrarSalario(int anyos) {
		if (anyos < 1) {
			System.out.println("Desarrollador Junior L1 – 15000-18000");

		} else if (anyos >= 1 && anyos <= 2) {
			System.out.println("Desarrollador Junior L2 – 18000-22000");
		} else if (anyos >= 3 && anyos < 5) {
			System.out.println("Desarrollador Senior L1 – 22000-28000");
		} else if (anyos >= 5 && anyos < 8) {
			System.out.println("Desarrollador Senior L2 – 28000-36000");
		} else if (anyos >= 8) {
			System.out.println("Analista / Arquitecto. Salario a convenir en base a rol");
		}
	}
}