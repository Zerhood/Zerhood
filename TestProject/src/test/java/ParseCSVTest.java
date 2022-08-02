import model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parser.ParserCSV;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Программа обработки файлов CSV")
public class ParseCSVTest {

    private static final String sourceCorrectDateFormat = "correctDataFormatCSV.csv";
    //    private static final String sourceSwapCorrectDateFormat = "swapCorrectDataFormatXML.xml";
    private static final String wrongFile = "wrongCSVFile.csv";

    private List<Address> addresses;

    @BeforeEach
    public void setUp() {
        addresses = new ArrayList<>();
    }

    @Test
    @DisplayName("проверка работы c валидными данными")
    void parseCorrectFile() {
        List<Address> expected = new ArrayList<>();
        expected.add(new Address("\"Барнаул\"", "\"Дальняя улица\"", 56, 2));
        expected.add(new Address("\"Братск\"", "\"Большая Октябрьская улица\"", 65, 5));
        expected.add(new Address("\"Балаково\"", "\"Барыши, местечко\"", 67, 2));
        expected.add(new Address("\"Азов\"", "\"Просека, улица\"", 156, 3));
        expected.add(new Address("\"Барнаул\"", "\"Дальняя улица\"", 56, 2));

        List<Address> actual = getCollection(getCsvFileNamePath(sourceCorrectDateFormat));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("проверка работы с невалидными данными")
    void parseWrongFile() {
        List<Address> expected = new ArrayList<>();
        expected.add(new Address("\"Азов\"", "\"Просека, улица\"", 156, 3));
        expected.add(new Address("\"Барнаул\"", "\"Дальняя улица\"", 56, 2));

        List<Address> actual = getCollection(getCsvFileNamePath(wrongFile));

        assertEquals(expected, actual);
    }

    private List<Address> getCollection(String path) {
        ParserCSV parserCSV = new ParserCSV();
        parserCSV.parse(path);
        return parserCSV.getAddresses();
    }

    private String getCsvFileNamePath(String path) {
        return new File(Objects.requireNonNull(this.getClass().getResource(path)).getPath())
                .getAbsolutePath();
    }
}
