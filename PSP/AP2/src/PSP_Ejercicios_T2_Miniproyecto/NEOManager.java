package PSP_Ejercicios_T2_Miniproyecto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class NEOManager {
    private final String filePath;

    public NEOManager(String filePath) {
        this.filePath = filePath;
    }

    public void processNEOs() throws IOException, InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        NEOProcessor processor = new NEOProcessor();
        List<Future<String>> futures = new ArrayList<>();
        int neoCount = 0;

        long startTime = System.currentTimeMillis();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            List<NEO> neoBatch = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0].trim();
                    double position = Double.parseDouble(parts[1].trim());
                    double speed = Double.parseDouble(parts[2].trim());
                    neoBatch.add(new NEO(name, position, speed));
                    neoCount++;
                }

                if (neoBatch.size() == Runtime.getRuntime().availableProcessors()) {
                    for (NEO neo : neoBatch) {
                        boolean add = futures.add(executor.submit(() -> {
                            return processor.processNEO(neo);
                        }));
                    }
                    neoBatch.clear();
                }
            }

            for (NEO neo : neoBatch) {
                futures.add(executor.submit(() -> {
                    return processor.processNEO(neo);
                }));
            }
        }

        for (Future<String> future : futures) {
            System.out.println(future.get());
        }

        long endTime = System.currentTimeMillis();
        executor.shutdown();

        long totalTime = endTime - startTime;
        double averageTime = (double) totalTime / neoCount;
        System.out.printf("Tiempo total de ejecución: %d ms\n", totalTime);
        System.out.printf("Tiempo medio de ejecución por NEO: %.2f ms\n", averageTime);
    }
}
