import parser.ParserCSV;
import parser.ParserXML;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static final String CSV_REGEX = "(.+.csv)";
    private static final String XML_REGEX = "(.+.xml)";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Пожайлуста, введите путь до файла " +
                        "или завершите выполнение программы командой - exit");
                String input = scanner.nextLine();
                if (input.equals("exit")) break;
                if (existFile(input)) {
                    if (input.matches(CSV_REGEX)) {
                        new ParserCSV(input);
                    } else if (input.matches(XML_REGEX)) {
                        new ParserXML(input);
                    }
                } else {
                    System.out.println("Ошибка, путь или файл не найдеты.");
                }
            }
        }
    }

    public static boolean existFile(String path) {
        return Files.exists(Paths.get(path));
    }
}