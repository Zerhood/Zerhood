package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ParserCSV extends DefaultHandler {

    public ParserCSV(String path) {
        super(path);
    }

    @Override
    public void handler(String path) {
        try {
            Files.readAllLines(Paths.get(path)).stream()
                    .skip(1)
                    .map(s -> s.split(";"))
                    .map(Arrays::asList)
                    .filter(s -> s.size() == 4)
                    .forEach(this::addCollection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}