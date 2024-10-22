package PSP_Ejercicios_T2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PSP_Ejercicios5 {
    public static void execute(String className, String num1, String num2) {
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java","-cp", System.getProperty("java.class.path"), className, num1, num2);
            pb.start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readResultFromFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("The file does not exist");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("Reading the file " + filePath + ":");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String className = "PSP_Ejercicios_T2.PSP_Ejercicios3";

        String filePath1 = "sum_result_5_10.txt";
        execute(className, "5", "10");
        readResultFromFile(filePath1);

        String filePath2 = "sum_result_2_8.txt";
        execute(className, "2", "8");
        readResultFromFile(filePath2);

        System.out.println("Finished");
    }
}