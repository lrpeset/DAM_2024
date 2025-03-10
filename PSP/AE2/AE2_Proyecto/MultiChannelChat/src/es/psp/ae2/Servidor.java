package es.psp.ae2;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * This class represents the server application in a client-server architecture.
 * It listens for incoming client connections, manages channels, and assigns
 * clients to different channels to allow communication among them.
 * 
 * The server loads available channels from a file, listens on a specific port
 * for client connections, and creates a new thread (ServidorHilo) to handle
 * communication for each connected client. The server is designed to handle
 * multiple clients concurrently using multithreading.
 * 
 * Key components: - A map of channels, where each channel corresponds to a list
 * of connected client handler threads (ServidorHilo). - A constant `PORT`
 * representing the port number on which the server listens for client
 * connections. - A constant `CANALES_FILE` which refers to the file containing
 * the available channels.
 */
public class Servidor {
	// The port on which the server listens for client connections.
	private static final int PORT = 5000;

	// The file containing the list of available channels.
	private static final String CANALES_FILE = "canales.txt";

	// A map that holds channels and the corresponding list of client handler
	// threads.
	private Map<String, List<ServidorHilo>> canales = new HashMap<>();

	/**
	 * Constructs the Servidor object, which initializes the channels and starts the
	 * server to listen for client connections.
	 */
	public Servidor() {
		cargarCanales();
		iniciarServidor();
	}

	/**
	 * Loads the available channels from a file (CANALES_FILE) and stores them in
	 * the `canales` map. Each channel is represented by a number and an empty list
	 * for the connected clients.
	 */
	private void cargarCanales() {
		try (BufferedReader reader = new BufferedReader(new FileReader(CANALES_FILE))) {
			String linea;
			// Reads each line in the file and adds the channel number to the map.
			while ((linea = reader.readLine()) != null) {
				String[] partes = linea.split("-", 2);
				if (partes.length == 2) {
					String numeroCanal = partes[0].trim();
					canales.put(numeroCanal, new ArrayList<>());
					System.out.println("Canal cargado: " + linea.trim());
				}
			}
		} catch (IOException e) {
			System.err.println("Error al cargar canales: " + e.getMessage());
		}
	}

	/**
	 * Starts the server, listening for incoming client connections on the specified
	 * port (PORT). For each client connection, a new thread (ServidorHilo) is
	 * created to handle communication with the client.
	 */
	private void iniciarServidor() {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Servidor iniciado en el puerto " + PORT);

			while (true) {
				Socket clienteSocket = serverSocket.accept();
				System.out.println("Cliente conectado: " + clienteSocket.getInetAddress());

				// Start a new thread for handling the connected client.
				new Thread(new ServidorHilo(clienteSocket, canales)).start();
			}
		} catch (IOException e) {
			System.err.println("Error en el servidor: " + e.getMessage());
		}
	}

	/**
	 * The main method to start the server by creating an instance of the Servidor
	 * class.
	 * 
	 * @param args Command-line arguments (not used in this implementation).
	 */
	public static void main(String[] args) {
		new Servidor();
	}
}
