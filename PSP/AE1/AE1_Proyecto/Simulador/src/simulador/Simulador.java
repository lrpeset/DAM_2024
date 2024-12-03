package simulador;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code Simulador} class represents a graphical user interface (GUI)
 * application for simulating protein structures. It provides a window for the
 * user to input data and initiate simulations using either multi-process or
 * multi-threading approaches. The results of the simulation are displayed in
 * the form of a message showing the time taken for each method.
 */
public class Simulador {

	/**
	 * The entry point of the application. It initializes the frame and makes it
	 * visible to the user.
	 * 
	 * @param args Command-line arguments (not used).
	 */
	public static void main(String[] args) {
		JFrame frame = createFrame();
		frame.setVisible(true);
	}

	/**
	 * Creates the main JFrame for the application, containing the necessary input
	 * fields and the simulate button.
	 * 
	 * @return A {@link JFrame} containing the components of the simulation
	 *         interface.
	 */
	private static JFrame createFrame() {
		JFrame frame = new JFrame("Protein Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);

		JPanel panel = new JPanel(new GridLayout(5, 2));
		JTextField[] textFields = createInputFields(panel);

		JButton simulateButton = new JButton("Start Simulation");
		panel.add(simulateButton);

		simulateButton.addActionListener(e -> handleSimulation(textFields, frame));

		frame.add(panel);
		return frame;
	}

	/**
	 * Creates the input fields for the user to enter the quantities of primary,
	 * secondary, tertiary, and quaternary proteins.
	 * 
	 * @param panel The {@link JPanel} where the input fields will be added.
	 * @return An array of {@link JTextField} where the user can input data.
	 */
	private static JTextField[] createInputFields(JPanel panel) {
		JTextField[] textFields = new JTextField[4];
		String[] types = { "Primary", "Secondary", "Tertiary", "Quaternary" };

		for (int i = 0; i < 4; i++) {
			panel.add(new JLabel("Quantity of " + types[i] + ":"));
			textFields[i] = new JTextField("0");
			panel.add(textFields[i]);
		}
		return textFields;
	}

	/**
	 * Handles the simulation process by reading user input and executing both
	 * multi-process and multi-threaded simulations. After completion, it displays a
	 * message with the total time taken for both approaches.
	 * 
	 * @param textFields The input fields containing the user's data.
	 * @param frame      The main frame of the application where results are
	 *                   displayed.
	 */
	private static void handleSimulation(JTextField[] textFields, JFrame frame) {
		try {
			int[] quantities = new int[4];
			for (int i = 0; i < 4; i++) {
				quantities[i] = Integer.parseInt(textFields[i].getText());
			}

			long startTimeMP = System.currentTimeMillis();
			SimulacionMP.runMultiproceso(quantities);
			long endTimeMP = System.currentTimeMillis();

			long startTimeMT = System.currentTimeMillis();
			SimulacionMT.runMultihilo(quantities);
			long endTimeMT = System.currentTimeMillis();

			JOptionPane.showMessageDialog(frame,
					"Simulation completed.\n" + "Total time (Multi-process): " + (endTimeMP - startTimeMP) / 1000.0
							+ " s\n" + "Total time (Multi-threading): " + (endTimeMT - startTimeMT) / 1000.0 + " s");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
		}
	}
}
