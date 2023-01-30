import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int count = 0;
        while (true){
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists =file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists && !isDirectory){
                System.out.println("Путь указан верно");
                count++;
                int max = 0;
                int min = 0;
                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader =
                            new BufferedReader(fileReader);
                    String line;
                    int counte = 0;
                    int bot1 = 0;
                    int bot2 = 0;
                    Statistics st = new Statistics();
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if (line.contains("Googlebot")){
                            bot1++;
                        }
                        if (line.contains("YandexBot")){
                            bot2++;
                        }
                        LogEntry logEntry = new LogEntry(line);
                        st.addEntry(logEntry);

                        counte++;
                        if (max >= 1024) {
                            throw new RuntimeException("В файле встретилась строка длиннее 1024 символов");
                        }


                    }
                    System.out.println("Общее количество строк в файле: " + counte);
                    double b1 = (double) bot1/counte * 100;
                    double b2 = (double) bot2/counte * 100;
                    System.out.println("Доля запросов от Google: " + b1);
                    System.out.println("Доля запросов от Yandex: " + b2);
                    System.out.println("Min time: " + st.getMinTime());
                    System.out.println("Max time: " + st.getMaxTime());
                    System.out.println("Total trafic: " + st.getTotalTraffic());
                    System.out.println("Trafic rate: " + st.getTrafficRate());





                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                System.out.println("Указанный файл не существует или указанный путь не является путём к папке");
                continue;
            }
            System.out.println("Это файл номер: " + count);
        }
    }
}
