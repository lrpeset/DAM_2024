package ejercicios;

public class Ejercicio35 {
	public static void main(String[] args) {

		int[] arrayNumeros = { 1, 8, 3, 4, 5 };

		int max = mayorNumero(arrayNumeros);

		System.out.println("El mayor valor del array es: " + max);
	}

	public static int mayorNumero(int[] array) {
		int max = array[0];
		for (int num : array) {
			if (num > max) {
				max = num;
			}
		}
		return max;
	}
}
