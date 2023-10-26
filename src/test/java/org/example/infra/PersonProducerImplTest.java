package org.example.infra;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.example.infra.PersonProducerImpl.personSerde;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
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

            personProducer.sendRecord(
                    Person.newBuilder()
                            .setFirstName("Joran")
                            .setLastName("Van Belle")
                            .setAge(22)
                            .setCity("Lichtervelde")
                            .setJob("developer")
                            .build());


            verify(mockProducer).send(any());

            var person = mockProducer.history().get(0).value();

            assertTrue(person.getFirstName().equals("Joran"));
            assertTrue(person.getLastName().equals("Van Belle"));
            assertTrue(person.getAge() == 22);
            assertTrue(person.getJob().equals("developer"));
            assertTrue(person.getCity().equals("Lichtervelde"));
        }
    }
}
