package ejercicios;

import java.util.Scanner;

public class Ejercicio18 {
	public static void main(String[] args) {
		Scanner teclado = new Scanner(System.in);

		System.out.print("Introduce tu contraseña: ");
		String contrasena = teclado.nextLine();

		if (esContrasenaValida(contrasena)) {
			System.out.print("Repite la contraseña: ");
			String contrasenaRepetida = teclado.nextLine();

			if (contrasena.equals(contrasenaRepetida)) {
				System.out.println("La contraseña es válida y ha sido confirmada.");
			} else {
				System.out.println("Las contraseñas no coinciden. Inténtalo de nuevo.");
			}
		} else {
			System.out.println(
					"La contraseña no es válida, debe tener al menos 5 carácteres, 1 número y 1 letra mayúscula.");
		}

		teclado.close();
	}

	public static boolean esContrasenaValida(String contrasena) {
		if (contrasena.length() < 5) {
			return false;
		}

		boolean tieneNumero = false;
		boolean tieneMayuscula = false;

		for (char c : contrasena.toCharArray()) {
			if (Character.isDigit(c)) {
				tieneNumero = true;
			}
			if (Character.isUpperCase(c)) {
				tieneMayuscula = true;
			}
		}

		return tieneNumero && tieneMayuscula;
	}
}