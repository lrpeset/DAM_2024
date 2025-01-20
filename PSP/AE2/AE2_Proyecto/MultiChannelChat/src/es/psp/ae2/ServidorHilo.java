package es.psp.ae2;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * This class represents the client handler thread in the server. It manages the
 * communication with a connected client, handles channel selection, user
 * registration, and message processing. The server creates an instance of this
 * class for each connected client, and it is responsible for reading and
 * writing messages, interacting with channels, and notifying all clients in the
 * channel of events or messages.
 * 
 * Key features: - Channel selection: Clients choose a channel to join. - User
 * registration: Ensures unique usernames per channel. - Message processing:
 * Handles different types of messages, including standard messages, requests
 * for user lists, channel lists, and messages directed to specific channels. -
 * Disconnection: Manages client disconnections and notifies other clients in
 * the channel.
 */
public class ServidorHilo implements Runnable {
	// The socket through which the client is connected.
	private final Socket clienteSocket;

	// The map of available channels and the list of clients connected to each
	// channel.
	private final Map<String, List<ServidorHilo>> canales;

	// BufferedReader for reading client messages.
	private BufferedReader input;

	// PrintWriter for sending messages to the client.
	private PrintWriter output;

	// The current channel the client is connected to.
	private String canalActual;

	// The username of the client.
	private String nombreUsuario;

	/**
	 * Constructor that initializes the socket and the map of channels.
	 * 
	 * @param clienteSocket The socket through which the client is connected.
	 * @param canales       The map of channels available on the server.
	 */
	public ServidorHilo(Socket clienteSocket, Map<String, List<ServidorHilo>> canales) {
		this.clienteSocket = clienteSocket;
		this.canales = canales;
	}

	/**
	 * This method is executed when the thread is started. It handles the connection
	 * lifecycle, including channel selection, user registration, and message
	 * processing.
	 */
	@Override
	public void run() {
		try {
			inicializarConexiones();
			seleccionarCanal();
			registrarUsuario();

			String mensaje;
			// Continuously reads messages from the client and processes them.
			while ((mensaje = input.readLine()) != null) {
				procesarMensaje(mensaje);
			}
		} catch (IOException e) {
			System.err.println("Error con el cliente: " + e.getMessage());
		} finally {
			desconectar();
		}
	}

	/**
	 * Initializes the input and output streams for the client communication.
	 * 
	 * @throws IOException If there is an error in initializing the streams.
	 */
	private void inicializarConexiones() throws IOException {
		input = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
		output = new PrintWriter(clienteSocket.getOutputStream(), true);
	}

	/**
	 * Allows the client to select a channel to join from the available channels. If
	 * no valid selection is made, the connection is closed.
	 * 
	 * @throws IOException If there is an error in reading the client's input.
	 */
	private void seleccionarCanal() throws IOException {
		output.println("Canales disponibles:");
		List<String> canalList = new ArrayList<>(canales.keySet());

		if (canalList.isEmpty()) {
			output.println("No hay canales disponibles. Desconectando.");
			cerrarConexion();
			return;
		}
		for (int i = 0; i < canalList.size(); i++) {
			output.println((i + 1) + "- " + canalList.get(i));
		}
		output.println("Selecciona un canal (número):");
		String canalSeleccionado = input.readLine().trim();

		try {
			int canalIndice = Integer.parseInt(canalSeleccionado) - 1;
			if (canalIndice < 0 || canalIndice >= canalList.size()) {
				throw new NumberFormatException();
			}

			canalActual = canalList.get(canalIndice);
			output.println("Te has conectado al canal: " + canalActual);
		} catch (NumberFormatException e) {
			output.println("Selección de canal no válida. Desconectando.");
			cerrarConexion();
		}
	}

	/**
	 * Registers the client by assigning a unique username within the selected
	 * channel. It checks if the username already exists in the channel and prompts
	 * the client to choose another one if necessary.
	 * 
	 * @throws IOException If there is an error in reading the client's input.
	 */
	private void registrarUsuario() throws IOException {
		output.println("Introduce tu nombre de usuario:");
		while (true) {
			nombreUsuario = input.readLine();
			synchronized (canales.get(canalActual)) {
				boolean existe = canales.get(canalActual).stream()
						.anyMatch(hilo -> hilo.nombreUsuario.equals(nombreUsuario));
				if (!existe) {
					canales.get(canalActual).add(this);
					break;
				}
			}
			output.println("Nombre de usuario ya en uso. Por favor, elige otro:");
		}
		output.println("Conexión exitosa al canal: " + canalActual);
		enviarATodos("Usuario " + nombreUsuario + " se ha unido al canal.");
	}

	/**
	 * Processes incoming messages from the client. It handles various commands such
	 * as listing connected users, available channels, exiting the channel, and
	 * sending messages to specific channels or users.
	 * 
	 * @param mensaje The message received from the client.
	 */
	private void procesarMensaje(String mensaje) {
		if (mensaje.equalsIgnoreCase("whois")) {
			enviarATodos("Usuarios conectados al canal " + canalActual + ": " + obtenerUsuarios());
		} else if (mensaje.equalsIgnoreCase("channels")) {
			output.println("Canales disponibles: " + canales.keySet());
		} else if (mensaje.equalsIgnoreCase("exit")) {
			enviarATodos("Usuario " + nombreUsuario + " ha abandonado el canal.");
			desconectar();
		} else if (mensaje.startsWith("@canal")) {
			reenviarMensajeACanal(mensaje);
		} else {
			enviarATodos(formatMensaje(mensaje));
		}
	}

	/**
	 * Retrieves the list of usernames connected to the current channel.
	 * 
	 * @return A string with the usernames of the connected users.
	 */
	private String obtenerUsuarios() {
		return canales.get(canalActual).stream().map(h -> h.nombreUsuario).toList().toString();
	}

	/**
	 * Forwards a message to another channel.
	 * 
	 * @param mensaje The message to be forwarded.
	 */
	private void reenviarMensajeACanal(String mensaje) {
		String[] partes = mensaje.substring(6).trim().split(" ", 2);
		if (partes.length == 2) {
			String canalDestino = partes[0];
			String mensajeCanal = partes[1];

			if (canales.containsKey(canalDestino)) {
				enviarATodosCanal(canalDestino, "[" + nombreUsuario + "] " + mensajeCanal);
			} else {
				output.println("Canal " + canalDestino + " no encontrado.");
			}
		} else {
			output.println("Formato incorrecto. Uso correcto: @canalX mensaje.");
		}
	}

	/**
	 * Formats the message with a timestamp and the username of the sender.
	 * 
	 * @param mensaje The original message from the client.
	 * @return The formatted message.
	 */
	private String formatMensaje(String mensaje) {
		return "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "] "
				+ nombreUsuario + ": " + mensaje;
	}

	/**
	 * Sends a message to all clients in the current channel.
	 * 
	 * @param mensaje The message to be sent to all clients.
	 */
	private void enviarATodos(String mensaje) {
		synchronized (canales.get(canalActual)) {
			canales.get(canalActual).forEach(h -> h.output.println(mensaje));
		}
	}

	/**
	 * Sends a message to all clients in a specific channel.
	 * 
	 * @param canalDestino The channel where the message will be sent.
	 * @param mensaje      The message to be sent.
	 */
	private void enviarATodosCanal(String canalDestino, String mensaje) {
		synchronized (canales.get(canalDestino)) {
			canales.get(canalDestino).forEach(h -> h.output.println("[Canal " + canalDestino + "] " + mensaje));
		}
	}

	/**
	 * Disconnects the client from the server and notifies other clients in the
	 * channel.
	 */
	private void desconectar() {
		try {
			synchronized (canales.get(canalActual)) {
				canales.get(canalActual).remove(this);
				enviarATodos("Usuario " + nombreUsuario + " ha abandonado el canal " + canalActual + ".");
			}
			cerrarConexion();
		} catch (Exception e) {
			System.err.println("Error al desconectar cliente: " + e.getMessage());
		}
	}

	/**
	 * Closes the connection with the client, including closing the socket and
	 * streams.
	 * 
	 * @throws IOException If there is an error while closing the connection.
	 */
	private void cerrarConexion() throws IOException {
		if (clienteSocket != null)
			clienteSocket.close();
		if (input != null)
			input.close();
		if (output != null)
			output.close();
	}
}
