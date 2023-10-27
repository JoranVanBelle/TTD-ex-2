package org.example.infra;

import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.example.infra.SerdeFactory.personSerde;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = PersonProducerImpl.class)
public class PersonProducerImplTest {

    MockProducer<String, Person> mockProducer = new MockProducer<>(true, new StringSerializer(), personSerde("mock://sr").serializer());

    private PersonProducerImpl personProducer;

    @BeforeEach
    void beforeEach() {
        this.personProducer = new PersonProducerImpl(mockProducer);
    }

    @Nested
    class WhenNothingGoesWrong {

        @Test
        public void thenTheMessageIsSentOnce() {

            var expectedPerson = Person.newBuilder()
                    .setFirstName("Joran")
                    .setLastName("Van Belle")
                    .setAge(22)
                    .setCity("Lichtervelde")
                    .setJob("developer")
                    .build();

            personProducer.sendRecord(expectedPerson);


            verify(mockProducer, times(1)).send(any());

            var person = mockProducer.history().get(0).value();

            assertThat(person, is(equalTo(expectedPerson)));
        }
    }
}
