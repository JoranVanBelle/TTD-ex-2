package org.example.infra;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvScannerImplTest {

    private CsvScannerImpl csvScanner;

    @BeforeEach
    void beforeEach() {
        this.csvScanner = new CsvScannerImpl();
    }

    @Nested
    class WhenNothingIsWrong {

        @Test
        public void thenNothingIsThrown() {
            InputStream in = new ByteArrayInputStream("this;here;is;an;inputstream\nthis;here;is;another;inputstream".getBytes());

            var result = csvScanner.readCsv(in);

            var words = result.get(0).split("-");

            assertEquals(2, result.size());
            assertEquals("this", words[0]);
            assertEquals("here", words[1]);
            assertEquals("is", words[2]);
            assertEquals("an", words[3]);
            assertEquals("inputstream", words[4]);
        }

    }

    @Nested
    class WhenFormatIsWrong {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"this;is;an;inputstream;that;is;too;long", "too;short", "this;is;weird;", "this;;is;also;weird", "this-is-even-weirder",
                "there;is;one;too;much;"})
        public void thenAnErrorIsThrown(String input) {
            InputStream in = new ByteArrayInputStream(input.getBytes());

            assertThrows(IllegalArgumentException.class, () -> csvScanner.readCsv(in));
        }
    }


    @Nested
    class WhenParametersAreWrong {
        @ParameterizedTest
        @ValueSource(strings = {"firstName;lastName;a-number;job;city", "firstName;lastName;24L;job;city", "firstName;lastName;24.0;job;city", "firstName;lastName;-24;job;city"})
        public void thenAnErrorIsThrown(String input) {
            InputStream in = new ByteArrayInputStream(input.getBytes());

            assertThrows(IllegalArgumentException.class, () -> csvScanner.readCsv(in));
        }
    }

}
