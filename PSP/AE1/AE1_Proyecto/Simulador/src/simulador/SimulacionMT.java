package simulador;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code SimulacionMT} class is responsible for running simulations in a
 * multi-threaded environment. It generates simulation results based on the
 * given input parameters, writes the results to a file, and allows running
 * multiple simulations using separate threads.
 */
public class SimulacionMT implements Runnable {
	private final int tipo;
	private final int index;

	/**
	 * Constructs a new {@code SimulacionMT} instance.
	 * 
	 * @param tipo  The protein type to simulate (from 1 to 4).
	 * @param index The index of the simulation run.
	 */
	public SimulacionMT(int tipo, int index) {
		this.tipo = tipo;
		this.index = index;
	}

	/**
	 * Executes the simulation in a separate thread. The method starts the
	 * simulation, records the start and end time, and writes the results to a file.
	 */
	@Override
	public void run() {
		try {
			// Start the simulation
			long start = System.currentTimeMillis();
			double result = SimulacionUtils.simulation(tipo);
			long end = System.currentTimeMillis();

			// Generate the file name and write the results
			String fileName = "PROT_MT_" + tipo + "_n" + index + "_" + SimulacionUtils.generateTimestamp(start)
					+ ".sim";
			SimulacionUtils.writeSimulationResults(fileName, start, end, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Runs multiple simulations in parallel using separate threads. For each
	 * protein type (1 to 4), the method creates a new thread to run the simulation
	 * for the given number of iterations.
	 *
	 * @param cantidades An array of integers representing the number of simulations
	 *                   to run for each protein type.
	 */
	public static void runMultihilo(int[] cantidades) {
		List<Thread> threads = new ArrayList<>();
		for (int tipo = 1; tipo <= 4; tipo++) {
			// Loop through the number of simulations for each protein type
			for (int i = 1; i <= cantidades[tipo - 1]; i++) {
				// Create a new thread for each simulation
				Thread thread = new Thread(new SimulacionMT(tipo, i));
				threads.add(thread);
				thread.start();
			}
		}
		// Wait for all threads to complete
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
