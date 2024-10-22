package PSP_Ejercicios_T2;

import java.io.*;

public class PSP_Ejercicios3 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please insert two numbers as arguments.");
            return;
        }

        int number1 = Integer.parseInt(args[0]);
        int number2 = Integer.parseInt(args[1]);
        int sum = 0;

        for (int i = number1; i <= number2; i++) {
            sum += i;
        }

        try {
            File file = new File("sum_result_" + number1 + "_" + number2 + ".txt");
            FileWriter writer = new FileWriter(file);
            writer.write("Sum between " + number1 + " and " + number2 + " is: " + sum);
            writer.close();

            System.out.println("Result is saved in 'sum_result_" + number1 + "_" + number2 + ".txt'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}