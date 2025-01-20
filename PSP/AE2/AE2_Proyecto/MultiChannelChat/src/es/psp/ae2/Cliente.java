package es.psp.ae2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

/**
 * Represents a client in a client-server architecture. Handles the connection
 * to the server, user interaction, and message sending through the console and
 * pop-up dialogs.
 */
public class Cliente {
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	/**
	 * Constructs a new Cliente instance and establishes a connection to the server.
	 * Initiates the reception thread and manages user interaction.
	 * 
	 * @param serverIp   the IP address of the server
	 * @param serverPort the port number of the server
	 */
	public Cliente(String serverIp, int serverPort) {
		try {
			conectarAlServidor(serverIp, serverPort);
			iniciarHiloDeRecepcion();
			gestionarInteraccion();
		} catch (IOException e) {
			System.err.println("Error al conectar al servidor: " + e.getMessage());
		}
	}

	/**
	 * Establishes a connection to the server using the specified IP and port.
	 * 
	 * @param serverIp   the IP address of the server
	 * @param serverPort the port number of the server
	 * @throws IOException if an I/O error occurs while creating the socket or
	 *                     streams
	 */
	private void conectarAlServidor(String serverIp, int serverPort) throws IOException {
		socket = new Socket(serverIp, serverPort);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
		System.out.println("Conectado al servidor en " + serverIp + ":" + serverPort);
	}

	/**
	 * Starts a new thread to handle the reception of messages from the server.
	 */
	private void iniciarHiloDeRecepcion() {
		new Thread(new ClienteHilo(input)).start();
	}

	/**
	 * Manages the user interaction: selecting a channel, setting the username, and
	 * starting the main interaction loop for sending messages.
	 * 
	 * @throws IOException if an I/O error occurs during interaction
	 */
	private void gestionarInteraccion() {
		try (BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
			seleccionarCanal(consoleInput);
			establecerNombreUsuario(consoleInput);
			iniciarBuclePrincipal(consoleInput);
		} catch (IOException e) {
			System.err.println("Error durante la interacción con el cliente: " + e.getMessage());
		}
	}

	/**
	 * Prompts the user to select a communication channel.
	 * 
	 * @param consoleInput the BufferedReader used for console input
	 * @throws IOException if an I/O error occurs while reading input
	 */
	private void seleccionarCanal(BufferedReader consoleInput) throws IOException {
		System.out.print("Selecciona un canal (número): ");
		String canalSeleccionado = leerEntradaDeConsola(consoleInput);
		output.println(canalSeleccionado);
	}

	/**
	 * Prompts the user to enter a username.
	 * 
	 * @param consoleInput the BufferedReader used for console input
	 * @throws IOException if an I/O error occurs while reading input
	 */
	private void establecerNombreUsuario(BufferedReader consoleInput) throws IOException {
		System.out.print("Introduce tu nombre de usuario: ");
		String nombreUsuario = leerEntradaDeConsola(consoleInput);
		output.println(nombreUsuario);
	}

	/**
	 * The main loop that handles user input from the console and either sends it to
	 * the server or opens a pop-up dialog for messaging.
	 * 
	 * @param consoleInput the BufferedReader used for console input
	 * @throws IOException if an I/O error occurs while reading input
	 */
	private void iniciarBuclePrincipal(BufferedReader consoleInput) throws IOException {
		System.out
				.println("Conectado. Pulsa 'Intro' para abrir el popup de mensajería o escribe comandos directamente.");

		while (true) {
			String inputLine = leerEntradaDeConsola(consoleInput);

			if (inputLine == null || inputLine.trim().isEmpty()) {
				manejarPopupDeMensajeria();
			} else {
				output.println(inputLine);
				if ("exit".equalsIgnoreCase(inputLine)) {
					System.out.println("Cerrando cliente...");
					break;
				}
			}
		}
	}

	/**
	 * Reads a line of input from the console.
	 * 
	 * @param consoleInput the BufferedReader used for console input
	 * @return the input line entered by the user
	 * @throws IOException if an I/O error occurs while reading input
	 */
	private String leerEntradaDeConsola(BufferedReader consoleInput) throws IOException {
		return consoleInput.readLine();
	}

	/**
	 * Displays a pop-up dialog for the user to enter a message to be sent to the
	 * server. If no message is entered, a notification is shown in the console.
	 */
	private void manejarPopupDeMensajeria() {
		String mensaje = JOptionPane.showInputDialog(null, "Escribe tu mensaje:", "Enviar mensaje",
				JOptionPane.PLAIN_MESSAGE);

		if (mensaje == null || mensaje.trim().isEmpty()) {
			System.out.println("No se envió ningún mensaje.");
		} else {
			output.println(mensaje);
		}
	}

	/**
	 * The main method that initializes the client by reading the server's IP and
	 * port from the console, and then creates a `Cliente` instance to connect to
	 * the server.
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		try (BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
			System.out.print("Introduce la IP del servidor (localhost): ");
			String serverIp = consoleInput.readLine().trim();
			System.out.print("Introduce el puerto del servidor (5000): ");
			int serverPort = Integer.parseInt(consoleInput.readLine().trim());

			new Cliente(serverIp, serverPort);
		} catch (IOException | NumberFormatException e) {
			System.err.println("Error en la entrada del cliente: " + e.getMessage());
		}
	}
}
