package org.example.infra;

import org.example.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PersonProducerImplTest {

    @Mock
    private KafkaTemplate<String, Person> kafkaTemplate;

    private PersonProducerImpl personProducer;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        this.personProducer = new PersonProducerImpl(kafkaTemplate);
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


            verify(kafkaTemplate, times(1)).send(eq("person"), eq(expectedPerson));
        }
    }
}
