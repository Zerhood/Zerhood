import model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import parser.ParserXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Программа обработки файлов XML")
public class ParseXMLTest {
    private static final String sourceCorrectDateFormat = "correctDataFormatXML.xml";
    private static final String sourceSwapCorrectDateFormat = "swapCorrectDataFormatXML.xml";
    private static final String wrongFile = "wrongXMLFile.xml";

    private List<Address> addresses;

    @BeforeEach
    public void setUp() {
        addresses = new ArrayList<>();
    }

    @Test
    @DisplayName("проверяем файл с данными в одном порядке")
    void testValidateFormat() {
        List<Address> expected = new ArrayList<>();
        expected.add(new Address("Барнаул", "Дальняя улица", 56, 2));
        expected.add(new Address("Братск", "Большая Октябрьская улица", 65, 5));
        expected.add(new Address("Балаково", "Барыши, местечко", 67, 2));
        expected.add(new Address("Братск", "7-я Вишнёвая улица", 49, 5));
        expected.add(new Address("Батайск", "Мостотреста, улица", 133, 4));
        expected.add(new Address("Барнаул", "Дальняя улица", 56, 2));

        List<Address> actual = getCollection(getXmlFileNamePath(sourceCorrectDateFormat));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("проверяем файл с данными в разном порядке")
    void testNoValidFormat() {
        List<Address> expected = new ArrayList<>();
        expected.add(new Address("Барнаул", "Дальняя улица", 56, 2));
        expected.add(new Address("Братск", "Большая Октябрьская улица", 65, 5));
        expected.add(new Address("Балаково", "Барыши, местечко", 67, 2));
        expected.add(new Address("Братск", "7-я Вишнёвая улица", 49, 5));
        expected.add(new Address("Батайск", "Мостотреста, улица", 133, 4));
        expected.add(new Address("Барнаул", "Дальняя улица", 56, 2));

        List<Address> actual = getCollection(getXmlFileNamePath(sourceSwapCorrectDateFormat));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("проверяем файл с некоректными данными")
    void testWrongFile() {
        List<Address> expected = new ArrayList<>();
        expected.add(new Address("Барнаул", "Дальняя улица", 56, 2));
        expected.add(new Address("Братск", "Большая Октябрьская улица", 65, 5));
        expected.add(new Address("Барнаул", "Дальняя улица", 56, 2));

        List<Address> actual = getCollection(getXmlFileNamePath(wrongFile));

        assertEquals(expected, actual);
    }

    private List<Address> getCollection(String path) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            ParserXML parserXML = new ParserXML();
            parser.parse(new File(path), parserXML);
            return parserXML.getAddresses();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    private String getXmlFileNamePath(String path) {
        return new File(Objects.requireNonNull(this.getClass().getResource(path)).getPath())
                .getAbsolutePath();
    }
}
