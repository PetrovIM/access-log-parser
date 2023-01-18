import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 0;
        while (true) {
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists && !isDirectory) {
                System.out.println("Путь указан верно");
                count++;
                int max = 0;
                int min = 0;
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    int counte = 0;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();//длина строки
                        if (length >= max) {
                            max = length;
                        }
                        if (length <= min || length < max) {
                            min = length;
                        }
                        counte++;
                        if (max >= 1024) {
                            throw new RuntimeException("В файле встретилась строка длиннее 1024 символов");
                        }
                    }
                    System.out.println("Общее количество строк в файле: " + counte);// кол-во строк в файле
                    System.out.println("Длина самой длинной строки в файле: " + max);
                    System.out.println("Длина самой короткой строки в файле: " + min);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Указанный файл не существует или указанный путь не является путём к папке");
                continue;
            }
            System.out.println("Это файл номер: " + count);
        }
    }
}
