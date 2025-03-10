package ejercicios;

import java.util.Scanner;

public class Ejercicio10 {
	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		int numeroMes;
		String mes = null;

		while (true) {
			System.out.print("Introduce un número del 1 al 12 y te diré el mes correspondiente: ");

			if (teclado.hasNextInt()) {
				numeroMes = teclado.nextInt();

				switch (numeroMes) {
				case 1:
					mes = "Enero";
					break;
				case 2:
					mes = "Febrero";
					break;
				case 3:
					mes = "Marzo";
					break;
				case 4:
					mes = "Abril";
					break;
				case 5:
					mes = "Mayo";
					break;
				case 6:
					mes = "Junio";
					break;
				case 7:
					mes = "Julio";
					break;
				case 8:
					mes = "Agosto";
					break;
				case 9:
					mes = "Septiembre";
					break;
				case 10:
					mes = "Octubre";
					break;
				case 11:
					mes = "Noviembre";
					break;
				case 12:
					mes = "Diciembre";
					break;
				default:
					System.out.println("Número no válido, por favor, introduce un número del 1 al 12");
					continue;
				}

				System.out.println("El mes correspondiente al número " + numeroMes + " es " + mes);
				break;
			} else {
				System.out.println("Entrada no válida, introduce un número entero.");
				teclado.next();
			}
		}
		teclado.close();
	}
}
