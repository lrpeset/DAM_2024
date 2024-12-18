package ejercicios;

import java.util.Scanner;

public class Ejercicio12 {
	public static void main(String[] args) {
		int[] notas = new int[10];

		int cantidadSuspensos = 0;

		int cantidadAprobados = 0;

		int cantidadNotables = 0;

		int cantidadSobresalientes = 0;

		int cantidadMatricula = 0;

		try (Scanner teclado = new Scanner(System.in)) {
			System.out.println("Introduce las 10 notas correspondientes.");

			for (int i = 0; i < notas.length; i++) {
				System.out.print("Introduce la nota " + (i + 1) + ": ");
				int nota = teclado.nextInt();

				if (nota < 0 || nota > 10) {
					System.out.println("Nota inválida. Debe estar entre 0 y 10.");
					i--;
				} else {
					notas[i] = nota;
				}
			}
		}

		for (int nota : notas) {
			if (nota < 5) {
				cantidadSuspensos++;
			} else if (nota >= 5 && nota < 7) {
				cantidadAprobados++;
			} else if (nota >= 7 && nota < 8) {
				cantidadNotables++;
			} else if (nota >= 8 && nota < 9) {
				cantidadSobresalientes++;
			} else if (nota == 10) {
				cantidadMatricula++;
			}
		}

		System.out.println("Hay " + cantidadSuspensos + " suspensos");
		System.out.println("Hay " + cantidadAprobados + " aprobados");
		System.out.println("Hay " + cantidadNotables + " notables");
		System.out.println("Hay " + cantidadSobresalientes + " sobresalientes");
		System.out.println("Hay " + cantidadMatricula + " matrículas");
	}
}
