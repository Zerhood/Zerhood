package parser;

import model.ModelFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserXML extends DefaultHandler {
    private final String REGEX = "(\\\"([-\\dа-яА-ЯёЁ\\s,)(]+)\\\")";

    public ParserXML(String path) {
        super(path);
    }

    @Override
    public void handler(String path) {
        try {
            List<String> fileLines = Files.readAllLines(Paths.get(path));
            for (String fileLine : fileLines) {
                List<String> list = new ArrayList<>();
                Matcher matcher = Pattern.compile(REGEX).matcher(fileLine);
                while (matcher.find()) {
                    list.add(matcher.group(2));
                }
                if (list.size() == 4) {
                    modelFiles.add(new ModelFile(
                            list.get(0),
                            list.get(1),
                            Integer.parseInt(list.get(2)),
                            Integer.parseInt(list.get(3))
                    ));
                } else {
                    System.out.println("Формат файла составлен не верно");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}