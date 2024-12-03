package simulador.config;

/**
 * The {@code Config} class contains configuration constants used throughout the
 * simulation process. These constants define the output directory and file
 * extension for simulation result files.
 */
public class Config {

	/**
	 * The directory where the simulation results will be saved. This is a relative
	 * path from the root of the project.
	 */
	public static final String OUTPUT_DIRECTORY = "output/";

	/**
	 * The file extension used for simulation result files. Files will have this
	 * extension appended to their name when saved.
	 */
	public static final String FILE_EXTENSION = ".sim";
}
