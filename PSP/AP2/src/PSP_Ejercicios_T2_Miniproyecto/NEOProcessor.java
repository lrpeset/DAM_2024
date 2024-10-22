package PSP_Ejercicios_T2_Miniproyecto;

import java.io.FileWriter;
import java.io.IOException;

public class NEOProcessor {
    private static final double EARTH_POSITION = 1;
    private static final double EARTH_SPEED = 100;

    public NEOProcessor() {
    }

    public String processNEO(NEO neo) {
        double probability = neo.calculateCollisionProbability(EARTH_POSITION, EARTH_SPEED);

        String message;
        if (probability > 10.0) {
            message = String.format("ALERTA MUNDIAL: El NEO %s tiene una alta probabilidad de colisión: %.2f%%", neo.getName(), probability);
        } else {
            message = String.format("Tranquilidad: El NEO %s tiene una probabilidad de colisión de %.2f%% - No representa un peligro inmediato.", neo.getName(), probability);
        }

        saveCollisionProbabilityToFile(neo.getName(), probability);

        return message;
    }


    private void saveCollisionProbabilityToFile(String neoName, double probability) {
        try (FileWriter writer = new FileWriter(neoName + ".txt")) {
            writer.write(String.format("NEO: %s - Probabilidad de colisión: %.2f%%", neoName, probability));
        } catch (IOException e) {
            System.err.println("Error guardando el archivo para " + neoName);
            e.printStackTrace();
        }
    }
}
