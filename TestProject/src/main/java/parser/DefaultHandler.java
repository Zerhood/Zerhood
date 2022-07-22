package parser;

import model.ModelFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class DefaultHandler {
    protected List<ModelFile> modelFiles;

    public DefaultHandler(String path) {
        modelFiles = new ArrayList<>();
        handler(path);
        getDuplicateCounts();
        getCityFloorCounts();
    }

    protected abstract void handler(String path);

    private void getCityFloorCounts() {
        Map<String, Map<Integer, Long>> map = modelFiles.stream()
                .collect(Collectors.groupingBy(ModelFile::getCity
                        , Collectors.groupingBy(ModelFile::getFloor, Collectors.counting())));
        map.entrySet().forEach(System.out::println);
    }

    private void getDuplicateCounts() {
        Map<ModelFile, Long> map = modelFiles.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        map.entrySet().stream()
                .filter(s -> s.getValue() > 1)
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }

    public void addCollection(List<String> list) {
        modelFiles.add(new ModelFile(
                list.get(0),
                list.get(1),
                Integer.parseInt(list.get(2)),
                Integer.parseInt(list.get(3))
        ));
    }
}