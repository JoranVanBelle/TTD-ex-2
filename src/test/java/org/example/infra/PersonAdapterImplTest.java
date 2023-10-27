package org.example.infra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.kafka.common.protocol.types.Field;
import org.example.entity.Row;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

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
            Row personString = new Row(List.of("firstName", "lastName", "24", "job", "city"));

            var person = personAdapter.getPerson(personString);

            assertEquals("firstName", person.getFirstName());
            assertEquals("lastName", person.getLastName());
            assertEquals(24, person.getAge());
            assertEquals("job", person.getJob());
            assertEquals("city", person.getCity());
        }
    }

    @Nested
    class WhenNumberOfParametersIsNotGood {

        @Nested
        class WhenParametersAreMissing {
            @ParameterizedTest
            @MethodSource("parametersAreMissing")
            public void thenErrorIsThrownAndNoPersonObjectCreated(Row row) {
                assertThrows(IllegalArgumentException.class, () -> personAdapter.getPerson(row));
            }

            private static Stream<Arguments> parametersAreMissing() {
                return Stream.of(
                        Arguments.of(new Row(List.of("firstName", "lastName", "job", "city"))),
                        Arguments.of(new Row(List.of("lastName", "age", "job", "city"))),
                        Arguments.of(new Row(List.of("firstName", "lastName", "age", "job"))),
                        Arguments.of(new Row(List.of("firstName", "age", "job", "city")))
                );
            }
        }

        @Nested
        class WhenTooManyParametersAreAvailable {
            @ParameterizedTest
            @MethodSource("parametersAreMissing")
            public void thenErrorIsThrownAndNoPersonObjectCreated(Row row) {
                assertThrows(IllegalArgumentException.class, () -> personAdapter.getPerson(row));
            }

            private static Stream<Arguments> parametersAreMissing() {
                return Stream.of(
                        Arguments.of(new Row(List.of("firstName", "middleName", "lastName", "age", "job", "city"))),
                        Arguments.of(new Row(List.of("marialState", "firstName", "lastName", "age", "job", "city"))),
                        Arguments.of(new Row(List.of("firstName", "lastName", "marialState", "age", "job", "city"))),
                        Arguments.of(new Row(List.of("firstName", "lastName", "age", "marialState", "job", "city"))),
                        Arguments.of(new Row(List.of("firstName", "lastName", "age", "job", "marialState", "city"))),
                        Arguments.of(new Row(List.of("firstName", "lastName", "age", "job", "city", "marialState")))
                );
            }
        }
    }

    @Nested
    class WhenAgeParameterIsWrong {

        @ParameterizedTest
        @MethodSource("wrongParameters")
        public void thenErrorIsThrownAndNoPersonObjectCreated(Row row) {
            assertThrows(IllegalArgumentException.class, () -> personAdapter.getPerson(row));
        }

        private static Stream<Arguments> wrongParameters() {
            return Stream.of(
                    Arguments.of(new Row(List.of("firstName", "lastName", "aNumber", "job", "city"))),
                    Arguments.of(new Row(List.of("firstName", "lastName", "-1", "job", "city"))),
                    Arguments.of(new Row(List.of("firstName", "lastName", "1.1", "job", "city")))
            );
        }
    }

}
