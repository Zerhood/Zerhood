package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserXML extends DefaultHandler {

    public ParserXML(String path) {
        super(path);
    }

    @Override
    public void handler(String path) {
        try {
            Files.readAllLines(Paths.get(path)).stream()
                    .map(this::addInnerCollection)
                    .filter(s -> s.size() == 4)
                    .filter(this::isCorrect)
                    .forEach(this::addCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> addInnerCollection(String s) {
        List<String> list = new ArrayList<>();
        String REGEX = "(\\\"([-\\dа-яА-ЯёЁ\\s,)(]+)\\\")";
        Matcher matcher = Pattern.compile(REGEX).matcher(s);
        while (matcher.find()) {
            list.add(matcher.group(2));
        }
        return list;
    }
}