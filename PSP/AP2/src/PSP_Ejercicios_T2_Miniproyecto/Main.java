package PSP_Ejercicios_T2_Miniproyecto;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        String filePath = "Ejercicios_T2_Multiproceso_NEOs.txt";

        NEOManager neoManager = new NEOManager(filePath);

        try {
            neoManager.processNEOs();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
