package org.example.infra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class PersonAdapterImplTest {

    private PersonAdapterImpl personAdapter;

    @BeforeAll
    void beforeAll() {
        this.personAdapter = new PersonAdapterImpl();
    }

    // everything correct
    @Test
    public void everythingIsCorrect_stringConvertedToPersonObject() {
        String personString = "firstName-lastName-24-job-city"; // As I am writing my tests,
                                                                // I am thinking this is a redundant work compared to the scanner
                                                                // I did this because I want to have all the different functionalities in a seperate class

        var person = personAdapter.getPerson(personString);

        assertEquals("firstName", person.getFirstname());
        assertEquals("lastName", person.getLastName());
        assertEquals(24, person.getAge());
        assertEquals("job", person.getJob());
        assertEquals("city", person.getCity());
    }

    // params missing
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"firstName-lastName--job-city", "-lastName-24-job-city", "firstName-lastName-24-job-", "firstName-24-job-city", "firstName-lastName--24-job-city"})
    public void parametersAreMissing_errorThrownNoPersonObject(String personString) {
        assertThrows(IllegalArgumentException.class, () -> personAdapter.getPerson(personString));
    }

    //not correct type
    @ParameterizedTest
    @ValueSource(strings = {"firstName-lastName-aNumber-job-city", "firstName-lastName-24L-job-city", "firstName-lastName-24.0-job-city"})
    public void ageParameterNotCorrect_errorThrownNoPersonObject(String personString) {
        assertThrows(IllegalArgumentException.class, () -> personAdapter.getPerson(personString));
    }

}
