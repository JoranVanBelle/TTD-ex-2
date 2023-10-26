package org.example.infra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class PersonAdapterImplTest {

    private PersonAdapterImpl personAdapter;

    @BeforeEach
    void beforeEach() {
        this.personAdapter = new PersonAdapterImpl();
    }

    @Nested
    class WhenNothingIsWrong {

        @Test
        public void thenIsStringConvertedToPersonObject() {
            String personString = "firstName-lastName-24-job-city"; // As I am writing my tests,
            // I am thinking this is a redundant work compared to the scanner
            // I did this because I want to have all the different functionalities in a seperate class

            var person = personAdapter.getPerson(personString);

            assertEquals("firstName", person.getFirstName());
            assertEquals("lastName", person.getLastName());
            assertEquals(24, person.getAge());
            assertEquals("job", person.getJob());
            assertEquals("city", person.getCity());
        }
    }

    @Nested
    class WhenParametersAreMissing {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"firstName-lastName--job-city", "-lastName-24-job-city", "firstName-lastName-24-job-", "firstName-24-job-city", "firstName-lastName--24-job-city"})
        public void thenErrorIsThrownAndNoPersonObjectCreated(String personString) {
            assertThrows(IllegalArgumentException.class, () -> personAdapter.getPerson(personString));
        }
    }

    @Nested
    class WhenAgeParameterIsWrong {

        @ParameterizedTest
        @ValueSource(strings = {"firstName-lastName-aNumber-job-city", "firstName-lastName-24L-job-city", "firstName-lastName-24.0-job-city"})
        public void thenErrorIsThrownAndNoPersonObjectCreated(String personString) {
            assertThrows(IllegalArgumentException.class, () -> personAdapter.getPerson(personString));
        }
    }

}
