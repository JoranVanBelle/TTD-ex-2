package org.example.infra;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.entity.Row;
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
    class WhenCorrectCsvIsGiven {

        @Test
        public void thenAListOfStringsIsReturned() {
            InputStream in = new ByteArrayInputStream("joran;van belle;22;developer;lichtervelde\nbenjamin;barrett;31;developer;roeselare".getBytes());

            List<Row> result = csvScanner.readCsv(in);

            assertEquals(2, result.size());
        }

        @Test
        public void thenTheResultOnlyContainsTheInputFields() {
            InputStream in = new ByteArrayInputStream("joran;van belle;22;developer;lichtervelde\nbenjamin;barrett;31;developer;roeselare".getBytes());

            List<Row> result = csvScanner.readCsv(in);

            var person = result.get(0).personString();

            assertThat(person.get(0), is(equalTo("joran")));
            assertThat(person.get(1), is(equalTo("van belle")));
            assertThat(person.get(2), is(equalTo("22")));
            assertThat(person.get(3), is(equalTo("developer")));
            assertThat(person.get(4), is(equalTo("lichtervelde")));
        }

    }

}
