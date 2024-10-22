package PSP_Ejercicios_T2;

import java.io.File;

public class PSP_Ejercicios7 {
    public static void execute(String className, String outputFilePath) {
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), className);
            pb.redirectOutput(new File(outputFilePath));
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String className = "PSP_Ejercicios_T2.PSP_Ejercicios1";
        String outputFilePath = "output_result.txt";

        execute(className, outputFilePath);
        System.out.println("Execution finished. Output file: " + outputFilePath);
    }
}
