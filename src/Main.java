import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int secondNumber = new Scanner((System.in)).nextInt();
        int sum = firstNumber + secondNumber;
        int diff = firstNumber - secondNumber;
        int multi = firstNumber * secondNumber;
        double div = (double) firstNumber / secondNumber;
        System.out.println("Сумма введеных чисел равна: " + sum);
        System.out.println("Разница между введенными числами равна: " + diff);
        System.out.println("Произведение введенных чисел равно: " + multi);
        System.out.println("Частное введенных чисел равно: " + div);
    }
}
