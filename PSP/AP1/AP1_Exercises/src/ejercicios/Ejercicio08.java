package ejercicios;

import java.util.Scanner;

public class Ejercicio08 {
	public static void main(String[] args) {
		System.out.print("Escribe un número del 1 al 12 y te diré a qué mes corresponde: ");

		try (Scanner teclado = new Scanner(System.in)) {
			int numeroMes = teclado.nextInt();
			String mes = "";

			if (numeroMes == 1) {
				mes = "Enero";
			} else if (numeroMes == 2) {
				mes = "Febrero";
			} else if (numeroMes == 3) {
				mes = "Marzo";
			} else if (numeroMes == 4) {
				mes = "Abril";
			} else if (numeroMes == 5) {
				mes = "Mayo";
			} else if (numeroMes == 6) {
				mes = "Junio";
			} else if (numeroMes == 7) {
				mes = "Julio";
			} else if (numeroMes == 8) {
				mes = "Agosto";
			} else if (numeroMes == 9) {
				mes = "Septiembre";
			} else if (numeroMes == 10) {
				mes = "Octubre";
			} else if (numeroMes == 11) {
				mes = "Noviembre";
			} else if (numeroMes == 12) {
				mes = "Diciembre";
			} else {
				System.out.println("Por favor introduce un número del 1 al 12");
				return;
			}

			System.out.println("El mes correspondiente al número " + numeroMes + " es " + mes);
		}
	}
}
