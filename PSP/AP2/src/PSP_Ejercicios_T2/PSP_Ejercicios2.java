package PSP_Ejercicios_T2;

public class PSP_Ejercicios2 {
    public static void execute(String className) {
        ProcessBuilder pb;
        try {
            pb = new ProcessBuilder("java", className);
            pb.start();

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
