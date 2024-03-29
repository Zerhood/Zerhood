package result;

import model.Address;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PrintResult {

    public static void getCityFloorCounts(List<Address> list) {
        Map<String, Map<Integer, Long>> map = list.stream()
                .collect(Collectors.groupingBy(Address::getCity
                        , Collectors.groupingBy(Address::getFloor, Collectors.counting())));
        map.entrySet().forEach(System.out::println);
    }

    public static void getDuplicateCounts(List<Address> list) {
        Map<Address, Long> map = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        map.entrySet().stream()
                .filter(s -> s.getValue() > 1)
                .sorted(Map.Entry.comparingByValue())
                .forEach(System.out::println);
    }
}