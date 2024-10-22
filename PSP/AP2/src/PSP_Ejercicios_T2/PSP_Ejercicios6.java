package PSP_Ejercicios_T2;

public class PSP_Ejercicios6 {
    public static void execute(String className) {
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), className);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String className = "PSP_Ejercicios_T2.PSP_Ejercicios1";
        execute(className);
        System.out.println("Finished");
    }
}
