package ejercicios;

import java.util.Scanner;

public class Ejercicio14 {
	public static void main(String[] args) {
		
		System.out.print("Introduce el radio de la circunferencia: ");
		
		try(Scanner teclado = new Scanner(System.in)) {
			double radio = teclado.nextDouble();
			
			
			double diametro = 2 * radio;
			double area = Math.PI * Math.pow(radio, 2);
			
            System.out.printf("El diametro de la circunferencia es: %.3f%n", diametro);
            System.out.printf("El area de la circunferencia es: %.3f%n", area);
		}
	}
}