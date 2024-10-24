package ejercicios;

import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio29 {

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
		ArrayList<Vehiculo> vehiculos = new ArrayList<>();
		Scanner teclado = new Scanner(System.in);

		System.out.print("Introduce el número de vehículos a introducir: ");
		int numeroVehiculos = teclado.nextInt();
		teclado.nextLine();

		
		for (int i = 0; i < numeroVehiculos; i++) {
			System.out.println("Introduce los atributos del vehículo " + (i + 1) + ":");

			System.out.print("Tipo: ");
			String tipo = teclado.nextLine();

			System.out.print("Marca: ");
			String marca = teclado.nextLine();

			System.out.print("Modelo: ");
			String modelo = teclado.nextLine();

			vehiculos.add(new Vehiculo(tipo, marca, modelo));
		}

		System.out.println("\nInformación de los vehículos creados:");
		for (Vehiculo vehiculo : vehiculos) {
			vehiculo.mostrarInfo();
		}

		teclado.close();
	}
}