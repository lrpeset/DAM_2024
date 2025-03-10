package ejercicios;

import java.util.*;

public class Ejercicio36 {

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);

		System.out.println("Introduce 5 numeros enteros");
		System.out.print("Introduce el primer numero: ");
		int numero1 = teclado.nextInt();

		System.out.print("Introduce el segundo numero: ");
		int numero2 = teclado.nextInt();

		System.out.print("Introduce el tercer numero: ");
		int numero3 = teclado.nextInt();

		System.out.print("Introduce el cuarto numero: ");
		int numero4 = teclado.nextInt();

		System.out.print("Introduce el quinto numero: ");
		int numero5 = teclado.nextInt();
		teclado.close();

		ArrayList<Integer> numeros = new ArrayList<>(Arrays.asList(numero1, numero2, numero3, numero4, numero5));

		ordenarNumeros(numeros);
	}

	public static void ordenarNumeros(ArrayList<Integer> numeros) {
		Collections.reverse(numeros);

		StringBuilder resultado = new StringBuilder();
		for (int i = 0; i < numeros.size(); i++) {
			resultado.append(numeros.get(i));
			if (i < numeros.size() - 1) {
				resultado.append(", ");
			}
		}

		System.out.println("La lista de numeros en orden inversa es: " + resultado.toString());

		int sumaNumeros = 0;

		for (int numero : numeros) {
			sumaNumeros += numero;
		}
		System.out.println("La suma de todos los números es: " + sumaNumeros);
	}
}
