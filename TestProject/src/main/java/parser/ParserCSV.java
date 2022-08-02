package parser;

import model.Address;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParserCSV implements ModelClass {

    private Address address;
    private List<Address> addresses;

    public ParserCSV() {
        addresses = new ArrayList<>();
    }

    public void parse(String path) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] array = line.split(";");
                if (isCorrect(array)) {
                    address = new Address(
                            array[0],
                            array[1],
                            Integer.parseInt(array[2]),
                            Integer.parseInt(array[3])
                    );
                    addresses.add(address);
                    address = null;
                } else {
                    System.out.println("Формат данных неверный" + System.lineSeparator() +
                            "city = " + array[0] + " должна быть строка" + System.lineSeparator() +
                            "street = " + array[1] + " должна быть строка" + System.lineSeparator() +
                            "house = " + array[2] + " должно быть число" + System.lineSeparator() +
                            "floor = " + array[3] + " должно быть число");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    @Override
    public boolean isCorrect(String[] array) {
        String strRegex = "^(\\\"[а-яА-ЯёЁ\\d-\\s,)(]+\\\")";
        String intRegex = "(\\d+)";

        return array[0].matches(strRegex) &&
                array[1].matches(strRegex) &&
                array[2].matches(intRegex) &&
                array[3].matches(intRegex);
    }

    @Override
    public void getCityFloorCounts() {
        Map<String, Map<Integer, Long>> map = getAddresses().stream()
                .collect(Collectors.groupingBy(Address::getCity
                        , Collectors.groupingBy(Address::getFloor, Collectors.counting())));
        map.entrySet().forEach(System.out::println);
    }

    @Override
    public void getDuplicateCounts() {
        Map<Address, Long> map = getAddresses().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        map.entrySet().stream()
                .filter(s -> s.getValue() > 1)
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }
}