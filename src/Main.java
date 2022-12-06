import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Введите текст и нажмите <Enter>:");
//        String text = new Scanner(System.in).nextLine();
//        System.out.println("Длина текста: " + text.length());

        int count = 0;
        while (true){
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists =file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists || isDirectory){
                System.out.println("Путь указан верно");
                count++;
            }
            else {
                System.out.println("Указанный файл не существует или указанный путь не является путём к папке");
                continue;
            }
            System.out.println("Это файл номер: " + count);
        }
    }
}
