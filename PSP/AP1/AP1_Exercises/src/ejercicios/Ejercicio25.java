package ejercicios;

import java.util.Random;

public class Ejercicio25 {

	public static void main(String[] args) {

		Random randomNumber = new Random();

		int firstNumber = randomNumber.nextInt(11);
		int secondNumber = randomNumber.nextInt(11);
		int thirdNumber = randomNumber.nextInt(11);

		if (firstNumber == secondNumber && firstNumber == thirdNumber) {
			System.out.println("¡Enhorabuena! los 3 números coinciden así que has ganado un caramelo.");
		} else if (firstNumber == secondNumber || firstNumber == thirdNumber || thirdNumber == secondNumber) {
			System.out.println("¡Uy! Coinciden 2 números.");
		} else {
			System.out.println("Los números no coinciden :(");
		}
	}
}