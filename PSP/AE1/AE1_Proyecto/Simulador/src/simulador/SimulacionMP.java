package simulador;

import java.io.IOException;

/**
 * The {@code SimulacionMP} class is responsible for running a simulation in a
 * multi-process environment. It generates simulation results based on the given
 * input parameters, writes the results to a file, and allows running multiple
 * simulations using separate processes.
 */
public class SimulacionMP {

	/**
	 * The entry point of the multi-process simulation application. It takes two
	 * arguments: the type of protein and an index value. The simulation is executed
	 * and the results are written to a file.
	 *
	 * @param args Command-line arguments, where the first argument is the protein
	 *             type and the second is the index.
	 * @throws IOException If an I/O error occurs during simulation result writing.
	 */
	public static void main(String[] args) throws IOException {
		int tipo = Integer.parseInt(args[0]); // Protein type
		int index = Integer.parseInt(args[1]); // Simulation index

		// Start the simulation
		long start = System.currentTimeMillis();
		double result = SimulacionUtils.simulation(tipo);
		long end = System.currentTimeMillis();

		// Generate the file name and write the results
		String fileName = "PROT_MP_" + tipo + "_n" + index + "_" + SimulacionUtils.generateTimestamp(start) + ".sim";
		SimulacionUtils.writeSimulationResults(fileName, start, end, result);
	}

	/**
	 * Runs multiple simulations in parallel using separate processes. For each
	 * protein type (1 to 4), the method creates a new process to run the simulation
	 * for the given number of iterations.
	 *
	 * @param cantidades An array of integers representing the number of simulations
	 *                   to run for each protein type.
	 * @throws IOException          If an I/O error occurs during the creation or
	 *                              execution of the processes.
	 * @throws InterruptedException If the current thread is interrupted while
	 *                              waiting for any process to finish.
	 */
	public static void runMultiproceso(int[] cantidades) throws IOException, InterruptedException {
		for (int tipo = 1; tipo <= 4; tipo++) {
			// Loop through the number of simulations for each protein type
			for (int i = 1; i <= cantidades[tipo - 1]; i++) {
				// Create a new process for each simulation
				ProcessBuilder builder = new ProcessBuilder("java", "-cp", "bin", "simulador.SimulacionMP",
						String.valueOf(tipo), String.valueOf(i));
				builder.inheritIO();
				Process process = builder.start();
				process.waitFor(); // Wait for the process to finish
			}
		}
	}
}
