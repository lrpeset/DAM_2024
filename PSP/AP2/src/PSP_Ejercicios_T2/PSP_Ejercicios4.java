package PSP_Ejercicios_T2;

import java.io.*;

public class PSP_Ejercicios4 {
    public static void execute(String className) {
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java", className);
            pb.start().waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readResultFromFile(String filePath) {
        File file = new File(filePath);

        if(!file.exists()) {
            System.out.println("The file does not exist");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("Reading the file: ");
            while ((line = br.readLine()) !=null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String className = "PSP_Ejercicios_T2.PSP_Ejercicios3";
        String filePath = "sum_result.txt";

        execute(className);
        readResultFromFile(filePath);
        System.out.println("Finished");
    }
}