package org.example.infra;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.example.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = PersonProducerImpl.class)
public class PersonProducerImplTest {

    @MockBean
    private KafkaProducer<String, Person> producer;

    private PersonProducerImpl personProducer;

    @BeforeEach
    void beforeEach() {
        this.personProducer = new PersonProducerImpl(producer);
    }

    @Nested
    class WhenNothingGoesWrong {

        @Test
        public void thenTheMessageIsSentOnce() {

            personProducer.sendRecord(new Person());

            verify(producer).send(any());
        }
    }
}
