package ejercicios;

import java.util.Scanner;

public class Ejercicio26 {
	public static void main(String[] args) {
		int[] notas = new int[10];

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

		int[] cantidades = determinarCalificacion(notas);

		System.out.println("Hay " + cantidades[0] + " suspensos");
		System.out.println("Hay " + cantidades[1] + " aprobados");
		System.out.println("Hay " + cantidades[2] + " notables");
		System.out.println("Hay " + cantidades[3] + " sobresalientes");
		System.out.println("Hay " + cantidades[4] + " matrículas");
	}
	
	public static int[] determinarCalificacion(int[] notas) {
		int cantidadSuspensos = 0;
		int cantidadAprobados = 0;
		int cantidadNotables = 0;
		int cantidadSobresalientes = 0;
		int cantidadMatricula = 0;
		
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
		
		return new int[]{cantidadSuspensos, cantidadAprobados, cantidadNotables, cantidadSobresalientes, cantidadMatricula};
	}
}