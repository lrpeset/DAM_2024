package ejercicios;

import java.util.Random;

public class Ejercicio24 {

	public static void main(String[] args) {

		Random randomNumber = new Random();

		int firstNumber = randomNumber.nextInt(11);
		int secondNumber = randomNumber.nextInt(11);

		if (firstNumber == secondNumber) {
			System.out.println("¡Enhorabuena! ambos números coinciden así que has ganado un caramelo.");
		} else {
			System.out.println("Los números no coinciden :(");
		}
	}
}