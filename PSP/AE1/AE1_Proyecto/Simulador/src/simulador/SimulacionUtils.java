package simulador;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import simulador.config.Config;

/**
 * The {@code SimulacionUtils} class provides utility methods for performing
 * simulations, generating timestamps, writing simulation results to files, and
 * ensuring that the necessary output directory exists.
 */
public class SimulacionUtils {

	/**
	 * Generates a timestamp string based on the provided time in milliseconds. The
	 * timestamp is formatted as {@code yyyyMMdd_HHmmss_SSS}.
	 * 
	 * @param time The time in milliseconds to generate the timestamp from.
	 * @return A string representing the formatted timestamp.
	 */
	public static String generateTimestamp(long time) {
		return new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date(time));
	}

	/**
	 * Runs a simulation for a specified protein type. The simulation performs a
	 * calculation based on the type and runs for a dynamically calculated amount of
	 * time.
	 * 
	 * @param type The type of simulation to run, corresponding to different protein
	 *             types.
	 * @return The result of the simulation (a double value).
	 */
	public static double simulation(int type) {
		double calc = 0.0;
		double simulationTime = Math.pow(5, type);
		double startTime = System.currentTimeMillis();
		double endTime = startTime + simulationTime;

		// Perform the simulation calculation for the specified duration
		while (System.currentTimeMillis() < endTime) {
			calc = Math.sin(Math.pow(Math.random(), 2));
		}
		return calc;
	}

	/**
	 * Writes the results of a simulation to a file. The results include the start
	 * and end timestamps, the simulation duration, and the simulation result.
	 * 
	 * @param fileName The name of the file to write the results to.
	 * @param start    The start time of the simulation in milliseconds.
	 * @param end      The end time of the simulation in milliseconds.
	 * @param result   The result of the simulation (a double value).
	 * @throws IOException If an I/O error occurs while writing to the file.
	 */
	public static void writeSimulationResults(String fileName, long start, long end, double result) throws IOException {
		// Ensure that the output directory exists
		ensureOutputDirectoryExists();

		// Construct the full file path
		String filePath = Config.OUTPUT_DIRECTORY + fileName;

		// Write the results to the file
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(generateTimestamp(start) + "\n");
			writer.write(generateTimestamp(end) + "\n");
			writer.write((end - start) / 1000.0 + "\n"); // Time in seconds
			writer.write(result + "\n");
		}
	}

	/**
	 * Ensures that the output directory exists. If the directory does not exist, it
	 * is created along with any necessary subdirectories.
	 */
	public static void ensureOutputDirectoryExists() {
		File outputDir = new File(Config.OUTPUT_DIRECTORY);
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
	}
}
