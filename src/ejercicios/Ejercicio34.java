package ejercicios;

import java.math.*;

public class Ejercicio34 {
	public static void main(String[] args) {

		int numero = 15;
		BigInteger factorial = BigInteger.ONE;

		for (int i = 1; i <= numero; i++) {
			factorial = factorial.multiply(BigInteger.valueOf(i));
		}

		System.out.println("El factorial de 15 es: " + factorial);
	}
}