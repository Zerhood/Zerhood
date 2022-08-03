import org.xml.sax.SAXException;
import parser.ParserCSV;
import parser.ParserXML;
import result.PrintResult;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    if (match(CSV_REGEX, input)) {
                        csvParse(input);
                    } else if (match(XML_REGEX, input)) {
                        xmlParse(input);
                    }
                } else {
                    System.out.println("Ошибка, путь или файл не найдеты.");
                }
            }
        }
    }

    public static void csvParse(String input) {
        ParserCSV parserCSV = new ParserCSV();
        parserCSV.parse(input);
        PrintResult printResult = new PrintResult(parserCSV.getAddresses());
        printResult.getDuplicateCounts();
        printResult.getCityFloorCounts();
    }

    public static void xmlParse(String input) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ParserXML parserXML = new ParserXML();
            parser.parse(new File(input), parserXML);
            PrintResult printResult = new PrintResult(parserXML.getAddresses());
            printResult.getDuplicateCounts();
            printResult.getCityFloorCounts();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public static boolean existFile(String path) {
        return Files.exists(Paths.get(path));
    }

    public static boolean match(String regex, String input) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}