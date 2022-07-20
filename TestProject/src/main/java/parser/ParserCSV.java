package parser;

import model.ModelFile;

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
            List<String> fileLines = Files.readAllLines(Paths.get(path));
            fileLines.remove(0);
            for (String fileLine : fileLines) {
                String[] splitLine = fileLine.split(";");
                List<String> columnList = new ArrayList<>(Arrays.asList(splitLine));
                if (columnList.size() == 4) {
                    modelFiles.add(new ModelFile(
                            columnList.get(0),
                            columnList.get(1),
                            Integer.parseInt(columnList.get(2)),
                            Integer.parseInt(columnList.get(3))
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