package es.psp.ae2;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a thread that listens for messages from the server. It
 * implements the Runnable interface, which allows it to run in a separate
 * thread, handling the reception of server messages asynchronously.
 */
public class ClienteHilo implements Runnable {
	private final BufferedReader input;

	/**
	 * Constructs a ClienteHilo instance with a BufferedReader to read messages from
	 * the server.
	 * 
	 * @param input the BufferedReader used to receive messages from the server
	 */
	public ClienteHilo(BufferedReader input) {
		this.input = input;
	}

	/**
	 * The run method listens for messages from the server in a loop. Each received
	 * message is printed to the console with a timestamp.
	 * 
	 * The method handles any IOExceptions that occur during message reception.
	 */
	@Override
	public void run() {
		try {
			String mensaje;
			while ((mensaje = input.readLine()) != null) {
				String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				System.out.println("[" + timestamp + "] Servidor: " + mensaje);
			}
		} catch (IOException e) {
			System.err.println("Error al recibir mensajes del servidor: " + e.getMessage());
		}
	}
}