package ejercicios;

import java.util.Scanner;

public class Ejercicio28 {

	public static class Vehiculo {
		String tipo;
		String marca;
		String modelo;

		public Vehiculo(String tipo, String marca, String modelo) {
			this.tipo = tipo;
			this.marca = marca;
			this.modelo = modelo;
		}

		public void mostrarInfo() {
			System.out.println("Tipo: " + tipo + ", Marca: " + marca + ", Modelo: " + modelo);
		}
	}

	public static void main(String[] args) {
		Vehiculo[] vehiculos = new Vehiculo[5];
		Scanner teclado = new Scanner(System.in);

		for (int i = 0; i < vehiculos.length; i++) {
			System.out.println("Introduce los atributos del vehículo " + (i + 1) + ":");

			System.out.print("Tipo: ");
			String tipo = teclado.nextLine();

			System.out.print("Marca: ");
			String marca = teclado.nextLine();

			System.out.print("Modelo: ");
			String modelo = teclado.nextLine();

			vehiculos[i] = new Vehiculo(tipo, marca, modelo);
		}

		System.out.println("\nInformación de los vehículos creados:");
		for (Vehiculo vehiculo : vehiculos) {
			vehiculo.mostrarInfo();
		}

		teclado.close();
	}
}