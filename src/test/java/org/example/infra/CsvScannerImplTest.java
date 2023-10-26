package org.example.infra;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvScannerImplTest {

    private CsvScannerImpl csvScanner;

    @BeforeAll
    void beforeAll() {
        this.csvScanner = new CsvScannerImpl();
    }

    @Test
    public void csvCanBeConverted_NoErrorThrown() {
        InputStream in = new ByteArrayInputStream("this;here;is;an;inputstream\nthis;here;is;another;inputstream".getBytes());

        var result = csvScanner.readCsv(in);

        assertEquals(2, result.size());
        System.out.println(result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"this;is;an;inputstream;that;is;too;long", "too;short", "this;is;weird;", "this;;is;also;weird", "this-is-even-weirder",
    "there;is;one;too;much;"})
    public void csvCannotBeConverted_wrongFormat_ErrorIsThrown(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());

        assertThrows(IllegalArgumentException.class, () -> csvScanner.readCsv(in));
    }

    @ParameterizedTest
    @ValueSource(strings = {"firstName;lastName;a-number;job;city", "firstName;lastName;24L;job;city", "firstName;lastName;24.0;job;city"})
    public void csvCannotBeConverted_correctFormatWrongParams_ErrorIsThrown(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());

        assertThrows(IllegalArgumentException.class, () -> csvScanner.readCsv(in));
    }

}
