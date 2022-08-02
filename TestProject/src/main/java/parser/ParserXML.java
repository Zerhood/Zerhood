package parser;

import model.Address;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ParserXML extends DefaultHandler implements ModelClass {

    private Address address;
    private List<Address> addresses;

    public ParserXML() {
        addresses = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("item") && address == null) {
            String[] array = {
                    attributes.getValue("city"),
                    attributes.getValue("street"),
                    attributes.getValue("house"),
                    attributes.getValue("floor")
            };
            if (isCorrect(array)) {
                address = new Address(
                        attributes.getValue("city"),
                        attributes.getValue("street"),
                        Integer.parseInt(attributes.getValue("house")),
                        Integer.parseInt(attributes.getValue("floor"))
                );
                addresses.add(address);
            } else {
                System.out.println("Формат данных неверный" + System.lineSeparator() +
                        "city = " + array[0] + " должна быть строка" + System.lineSeparator() +
                        "street = " + array[1] + " должна быть строка" + System.lineSeparator() +
                        "house = " + array[2] + " должно быть число" + System.lineSeparator() +
                        "floor = " + array[3] + " должно быть число");
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("item")) {
            address = null;
        }
    }

    public List<Address> getAddresses() {
        return addresses;
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

    @Override
    public boolean isCorrect(String[] array) {
        String strRegex = "^([а-яА-ЯёЁ\\d-\\s,)(]+)";
        String intRegex = "(\\d+)";

        return array[0].matches(strRegex) &&
                array[1].matches(strRegex) &&
                array[2].matches(intRegex) &&
                array[3].matches(intRegex);
    }
}